package com.renjia.blog.config.cache;

import com.alibaba.fastjson2.JSON;
import com.renjia.blog.mapper.BlogArticleMapper;
import com.renjia.blog.mapper.BlogAttentionMapper;
import com.renjia.blog.mapper.BlogLikeBrowseMapper;
import com.renjia.blog.mapper.BlogUserMapper;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogAttention;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogAttentionService;
import com.renjia.blog.service.IBlogLikeBrowseService;
import com.renjia.blog.service.IBlogUserService;
import com.renjia.blog.util.RedisUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotData {
    @Resource
    private BlogAttentionMapper blogAttentionMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    /**
     * 热点文章
     * 定期缓存热点数据到redis中
     */
    @XxlJob("hotArticle")
    public void hotArticle(){
        System.out.println("hotArticle");
        Set<Integer> set = redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE).reverseRange(0, 7);
        if (set.isEmpty()) return;
        List<Integer> collect = set.stream().collect(Collectors.toList());
        List<BlogArticle> ohtArticle = blogArticleMapper.getOhtArticle(collect);
        redisTemplate.opsForValue().set(RedisUtil.HOTARTICLE+":hot", JSON.toJSONString(ohtArticle));
    }

    /**
     * 热点话题
     * 定期缓存热点数据到redis中
     */
    @XxlJob("hotTopic")
    public void hotTopic(){
        System.out.println("hotTopic");
        Set<Integer> set = redisTemplate.boundZSetOps(RedisUtil.HOTTOPICE).reverseRange(0, 7);
        if (set.isEmpty()) return;
        List<Integer> collect = set.stream().collect(Collectors.toList());
        List<BlogArticle> ohtTopic = blogArticleMapper.getOhtTopic(collect);
        redisTemplate.opsForValue().set(RedisUtil.HOTTOPICE+":hot", JSON.toJSONString(ohtTopic));

    }

    /**
     * 热点用户
     * 定期缓存热点数据到redis中
     */
    @XxlJob("hotUser")
    public void hotUser(){
        System.out.println("hotUser");
        Set<Integer> set = redisTemplate.boundZSetOps(RedisUtil.HOTUSER).reverseRange(0, 7);
        if (set.isEmpty()) return;
        List<Integer> collect = set.stream().collect(Collectors.toList());
        List<BlogAttention> blogAttentions = blogAttentionMapper.getOhtUser(collect);
        redisTemplate.opsForValue().set(RedisUtil.HOTUSER+":hot", JSON.toJSONString(blogAttentions));
    }
}
