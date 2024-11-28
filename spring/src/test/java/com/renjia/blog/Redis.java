package com.renjia.blog;

import com.renjia.blog.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Redis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void zset(){
//        redisTemplate.boundZSetOps("yyyy").incrementScore("zyh",-1);
//        redisTemplate.boundZSetOps("yyyy").incrementScore(1,3);
//        Set set = redisTemplate.boundZSetOps("yyyy").reverseRange(0, 5);

        Set<Integer> set1 = redisTemplate.boundZSetOps(RedisUtil.HOTARTICLE).reverseRange(0, 7);

        System.out.println(set1);

    }
}
