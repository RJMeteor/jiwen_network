package com.renjia.blog.mq;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.rabbitmq.client.Channel;
import com.renjia.blog.netty.MessageConsume;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandlerImpl implements Handler {

    @Override
    public <T> void handle(Message message, Channel channel, Integer retryCountMax, Function<MessageConsume, Boolean> attemptAck, Consumer<Object> retryCallback) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            Boolean apply = attemptAck.apply(deserializationByte(message.getBody())); //用户是否在线，在线发送消息
            if (apply) channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 在线确认消息
            else retryCallback.accept(null); //用户不在线尝试重试消息确认
        } catch (Exception e) {
            retryCallback.accept(null);  //尝试重试消息确认
        }
    }

    @Override
    @SneakyThrows
    public void retry(Message message, Channel channel, Integer retryCountMax, Consumer<Map> consumer, Consumer<MessageConsume> unableToSucceed) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 头部信息
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        // 处理消息失败，获取重试次数
        int retryCount = (int) headers.getOrDefault("x-retry-count", 1);
        System.out.println("====me重试" + retryCount);
        // 判断是否达到最大重试次数
        if (retryCount >= retryCountMax) {
            unableToSucceed.accept(deserializationByte(message.getBody()));  // 发送到死信队列，或存入redis中 （自定义处理失败）
//            channel.basicReject(deliveryTag, false);
        } else {
            headers.put("x-retry-count", retryCount + 1);
            consumer.accept(headers);  //执行消息重试
//            throw new RuntimeException("抛异常重试消息");
        }
    }

}
