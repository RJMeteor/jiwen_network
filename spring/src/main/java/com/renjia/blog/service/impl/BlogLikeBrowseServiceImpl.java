package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.mapper.BlogLikeBrowseMapper;
import com.renjia.blog.mq.producer.ProductionStrategy;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.service.IBlogArticleService;
import com.renjia.blog.service.IBlogLikeBrowseService;
import com.renjia.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 点赞/浏览 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogLikeBrowseServiceImpl extends ServiceImpl<BlogLikeBrowseMapper, BlogLikeBrowse> implements IBlogLikeBrowseService {
    @Resource
    private BlogLikeBrowseMapper blogLikeBrowseMapper;
    @Autowired
    private ProductionStrategy productionStrategy;

    @Resource
    private IBlogArticleService blogArticleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer addLikeByArticleId(Long articlesId, Long userId, Long personId, Integer isLike) {
        BlogLikeBrowse blogLikeBrowse = new BlogLikeBrowse();
        blogLikeBrowse.setPersonId(personId);
        blogLikeBrowse.setArticleId(articlesId);
        blogLikeBrowse.setUserId(userId);
        blogLikeBrowse.setLikeBrowseLimiter(isLike);
        int insert = this.getBaseMapper().insert(blogLikeBrowse);
        if (insert <= 0) return 0;
        BlogArticle blogArticle = blogArticleService.getById(articlesId);
        if (isLike == 0 && personId != 0) {
            Integer integer = blogLikeBrowseMapper.addLike(blogLikeBrowse);
            if (integer > 0) {
                if (blogArticle.getArticleLimiter() == 0) {
                    redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                            .incrementScore(articlesId, 1);
                } else {
                    redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                            .incrementScore(articlesId, 1);

                }
//                BlogLikeBrowse likeById = blogLikeBrowseMapper.getLikeById(blogLikeBrowse.getId());
                MessageConsume message = new MessageConsume(MessageConsume.MessageType.LIKE.getType(), JSON.toJSONString(blogLikeBrowse.getId()));
                productionStrategy.sendMessage(message.getType(), message);
            }
            return integer;
        } else {
            if (blogArticle.getArticleLimiter() == 0) {
                redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                        .incrementScore(articlesId, 1);
            } else {
                redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                        .incrementScore(articlesId, 1);

            }
            return insert;
        }
    }

    @Override
    public Integer deleteLikeByArticleIdAndUserIdAndpersonId(Long articlesId, Long userId) {
        LambdaQueryWrapper<BlogLikeBrowse> deletetLike = new LambdaQueryWrapper<>();
        deletetLike.eq(BlogLikeBrowse::getUserId, userId)
                .eq(BlogLikeBrowse::getLikeBrowseLimiter, 0)
                .eq(BlogLikeBrowse::getArticleId, articlesId);
        int delete = this.getBaseMapper().delete(deletetLike);
        if (delete > 0) {
            BlogArticle blogArticle = blogArticleService.getById(articlesId);
            if (blogArticle.getArticleLimiter() == 0) {
                redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                        .incrementScore(blogArticle.getId(), -1);
            } else {
                redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                        .incrementScore(blogArticle.getId(), -1);

            }
            redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE).incrementScore(articlesId, -1);
        }
        return delete;
    }


    @Override
    public PageInfo<BlogLikeBrowse> getLikeByPersonId(Long personId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogLikeBrowse> list = blogLikeBrowseMapper.getLikeByPersonId(personId);
        PageInfo<BlogLikeBrowse> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public Integer updateLikeActive(List<Long> ids) {
        Integer integer = blogLikeBrowseMapper.updateLikeActive(ids);
        return integer;
    }

    @Override
    public List<BlogLikeBrowse> getOhtArticle() {
//        List<BlogLikeBrowse> ohtArticle = blogLikeBrowseMapper.getOhtArticle();
        Object o = redisTemplate.opsForValue().get(RedisUtil.HOTARTICLE + ":hot");
        ArrayList<BlogLikeBrowse> arrayList = JSON.parseObject((String) o, ArrayList.class);
        return arrayList;
    }

    @Override
    public List<BlogLikeBrowse> getOhtTopic() {
//        List<BlogLikeBrowse> ohtTopic = blogLikeBrowseMapper.getOhtTopic();
        Object o = redisTemplate.opsForValue().get(RedisUtil.HOTTOPICE + ":hot");
        ArrayList<BlogLikeBrowse> arrayList = JSON.parseObject((String) o, ArrayList.class);
        return arrayList;
    }
}
