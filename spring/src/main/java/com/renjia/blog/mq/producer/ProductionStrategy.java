package com.renjia.blog.mq.producer;

import com.alibaba.fastjson2.JSON;
import com.renjia.blog.mq.ConfirmCallback;
import com.renjia.blog.mq.MQType;
import com.renjia.blog.mq.ReturnCallback;
import com.renjia.blog.mq.consumer.BadFaithService;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ProductionStrategy {

    private static RabbitTemplate rabbitTemplate;
    private static RedisUtil redisUtil;

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,
            10,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(4),
            new ThreadPoolExecutor.DiscardPolicy()
    );


    public ProductionStrategy(RabbitTemplate rabbitTemplate, RedisUtil redisUtil) {
        ProductionStrategy.rabbitTemplate = rabbitTemplate;
        ProductionStrategy.redisUtil = redisUtil;
    }

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(new ConfirmCallback());
        rabbitTemplate.setReturnCallback(new ReturnCallback());
    }

    public void sendMessage(String type, MessageConsume message) {
        rabbitTemplate.convertAndSend(type + MQType.exchangesufix, type, JSON.toJSONString(message), me -> {
            me.getMessageProperties().setExpiration("10000");
            return me;
        });
    }

    public static void sendDoCallback(String ex, String key, byte[] body) {
        rabbitTemplate.convertAndSend(ex, key, JSON.parseObject(body), message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
    }

    public static void compensate(Long accptorId) {
        Map<Object, Object> hmget = redisUtil.hmget(BadFaithService.dxlNo + accptorId);
        redisUtil.del(BadFaithService.dxlNo + accptorId);
        if (!ObjectUtils.isEmpty(hmget)) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.putAll(hmget);
            Collection<Object> values = hashMap.values();
            poolExecutor.execute(() -> {
                for (Object value : values) {
                    poolExecutor.execute(() -> {
                        rabbitTemplate.convertAndSend(MessageConsume.MessageType.OTHER.getType() + MQType.exchangesufix, MessageConsume.MessageType.OTHER.getType(), value, me -> {
                            me.getMessageProperties().setExpiration("10000");
                            return me;
                        });
                    });
                }
            });
        }
    }
}
