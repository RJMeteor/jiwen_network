package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.mq.producer.ProductionStrategy;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.pojo.BlogAttention;
import com.renjia.blog.mapper.BlogAttentionMapper;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogAttentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjia.blog.util.RedisUtil;
import com.renjia.blog.util.WebSocketUtil;
import com.renjia.blog.util.WebsocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 关注 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogAttentionServiceImpl extends ServiceImpl<BlogAttentionMapper, BlogAttention> implements IBlogAttentionService {

    @Resource
    private BlogAttentionMapper blogAttentionMapper;
    @Autowired
    private ProductionStrategy productionStrategy;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Integer addAttention(Long personId, Long userId) {
        BlogAttention blogAttention = new BlogAttention();
        blogAttention.setAttentionUserId(personId);
        blogAttention.setUserId(userId);
        Integer integer = blogAttentionMapper.addAttentions(blogAttention);
        if (integer > 0) {
            redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(blogAttention.getAttentionUserId(), 1);
            BlogAttention attentionsById = blogAttentionMapper.getAttentionsById(blogAttention.getId());
            MessageConsume message = new MessageConsume(MessageConsume.MessageType.ATTENTION.getType(), JSON.toJSONString(attentionsById));
            productionStrategy.sendMessage(message.getType(), message);
        }
        return integer;
    }

    @Override
    public Integer deleteAttention(Long personId, Long userId) {
        LambdaQueryWrapper<BlogAttention> blogAttentionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        blogAttentionLambdaQueryWrapper.eq(BlogAttention::getUserId, userId)
                .eq(BlogAttention::getAttentionUserId, personId);
        int delete = this.getBaseMapper().delete(blogAttentionLambdaQueryWrapper);
        if (delete > 0) {
            redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(personId, -1);
        }
        return delete;
    }

    @Override
    public PageInfo<BlogAttention> getAttentionByPersonId(Long personId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogAttention> list = blogAttentionMapper.getAttentionByPersonId(personId);
        PageInfo<BlogAttention> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 获取自己关注
     *
     * @param childrenId
     */
    @Override
    public PageInfo<BlogAttention> getAttentions(Long childrenId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogAttention> list = blogAttentionMapper.getAttentions(childrenId);
        PageInfo<BlogAttention> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 获取自己粉丝
     *
     * @param personId
     */
    @Override
    public PageInfo<BlogAttention> getFens(Long personId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogAttention> list = blogAttentionMapper.getFens(personId);
        PageInfo<BlogAttention> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public Integer updateAttentionActive(List<Long> ids) {
        Integer integer = blogAttentionMapper.updateAttentionActive(ids);
        return integer;
    }

    @Override
    public Integer deleteAttentions(List<Long> ids) {
        int i = this.getBaseMapper().deleteBatchIds(ids);
        if (i > 0) {
            for (Long id : ids) {
                redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(id, -1);
            }
        }
        return i;
    }

    @Override
    public List<BlogAttention> getOhtUser() {
//        List<BlogAttention> ohtUser = blogAttentionMapper.getOhtUser();
        Object o = redisTemplate.opsForValue().get(RedisUtil.HOTUSER+":hot");
        ArrayList<BlogAttention> arrayList = JSON.parseObject((String) o, ArrayList.class);
        return arrayList;
    }
}
