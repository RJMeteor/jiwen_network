package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.mapper.BlogFavoriteMapper;
import com.renjia.blog.mq.producer.ProductionStrategy;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogFavorite;
import com.renjia.blog.service.IBlogArticleService;
import com.renjia.blog.service.IBlogFavoriteService;
import com.renjia.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 收藏夹 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogFavoriteServiceImpl extends ServiceImpl<BlogFavoriteMapper, BlogFavorite> implements IBlogFavoriteService {

    @Resource
    private BlogFavoriteMapper blogFavoriteMapper;
    @Autowired
    private ProductionStrategy productionStrategy;

    @Resource
    private IBlogArticleService blogArticleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<BlogFavorite> getFavoriteArticleByUserId(Long userId, String articleName, Long favoriteClassId, Integer isPrivate, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogFavorite> list = blogFavoriteMapper.getFavoriteArticleByUserId(userId, articleName, favoriteClassId, isPrivate);
        PageInfo<BlogFavorite> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer deleteFavoriteByUserIdAndArticleId(Long userId, Long articleId) {
        LambdaQueryWrapper<BlogFavorite> deleteLambda = new LambdaQueryWrapper<>();
        deleteLambda.eq(BlogFavorite::getArticleId, articleId).eq(BlogFavorite::getUserId, userId);
        int delete = this.baseMapper.delete(deleteLambda);
        if (delete>0){
            BlogArticle blogArticle = blogArticleService.getById(articleId);
            if (blogArticle.getArticleLimiter()==0) {
                redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                        .incrementScore(blogArticle.getId(), -1);
            }else {
                redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                        .incrementScore(blogArticle.getId(), -1);

            }
        }
        return delete;
    }

    @Override
    public Integer addFavoriteByUserIdAndArticleIdAndFavoriteId(Long userId, Long personId, Long favoriteClassId, Long articleId) {
        LambdaQueryWrapper<BlogFavorite> isContain = new LambdaQueryWrapper<>();
        isContain.
                eq(BlogFavorite::getArticleId, articleId).
                eq(BlogFavorite::getUserId, userId).
                eq(BlogFavorite::getPersonId, personId).
                eq(BlogFavorite::getFavoriteClassId, favoriteClassId);
        if (this.getOne(isContain) != null) {
            return 0;
        }
        BlogFavorite blogFavorite = new BlogFavorite();
        blogFavorite.setArticleId(articleId);
        blogFavorite.setUserId(userId);
        blogFavorite.setFavoriteClassId(favoriteClassId);
        blogFavorite.setPersonId(personId);
//        int insert = this.getBaseMapper().insert(blogFavorite);
        int insert = blogFavoriteMapper.addFavorites(blogFavorite);
        if (insert > 0 && personId != 0) {
//            BlogFavorite favoritesById = blogFavoriteMapper.getFavoritesById(blogFavorite.getId());
            BlogArticle blogArticle = blogArticleService.getById(articleId);
            if (blogArticle.getArticleLimiter()==0) {
                redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                        .incrementScore(blogArticle.getId(), 1);
            }else {
                redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                        .incrementScore(blogArticle.getId(), 1);

            }
            MessageConsume message = new MessageConsume(MessageConsume.MessageType.FAVORITE.getType(), JSON.toJSONString(blogFavorite.getId()));
            productionStrategy.sendMessage(message.getType(), message);
        }
        return insert;
    }

    @Override
    public Integer deleteFavoriteArticleById(List<Long> ids) {
        int i = this.getBaseMapper().deleteBatchIds(ids);
        if (i>0) {
            List<Long> collect = this.listByIds(ids).stream().map(ele -> ele.getArticleId()).collect(Collectors.toList());
            for (BlogArticle blogArticle : blogArticleService.listByIds(collect)) {
                if (blogArticle.getArticleLimiter()==0) {
                    redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE)
                            .incrementScore(blogArticle.getId(), -1);
                }else {
                    redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE)
                            .incrementScore(blogArticle.getId(), -1);

                }
            }
        }
        return i;
    }

    @Override
    public PageInfo<BlogFavorite> getFavoritesByPersonId(Long personId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogFavorite> list = blogFavoriteMapper.getFavoritesByPersonId(personId);
        PageInfo<BlogFavorite> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public Integer updateFavoritesActive(List<Long> ids) {
        Integer integer = blogFavoriteMapper.updateFavoritesActive(ids);
        return integer;
    }
}
