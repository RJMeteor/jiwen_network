package com.renjia.blog.mq;

import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.renjia.blog.netty.MessageConsume;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Handler {
    /**
     * 处理消息
     *
     * @param message
     * @param channel
     * @param retryCountMax
     * @param attemptAck
     * @param retryCallback
     * @param <T>
     */
    <T> void handle(Message message, Channel channel, Integer retryCountMax, Function<MessageConsume, Boolean> attemptAck, Consumer<Object> retryCallback);

    /**
     * 重试逻辑
     *
     * @param message
     * @param channel
     * @param retryCountMax
     * @param consumer
     */
    void retry(Message message, Channel channel, Integer retryCountMax, Consumer<Map> consumer, Consumer<MessageConsume> UnableToSucceed);

    /**
     * 默认重试逻辑
     *
     * @param message
     * @param channel
     * @return
     */
    default Consumer<Map> consumer(Message message, Channel channel) {
        return (headers) -> {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            try {
                // 绑定死信队列，拒绝消息丢到死信队列
                //channel.basicReject(deliveryTag, false);
                channel.basicAck(deliveryTag, false);
                //2.重新发送到MQ中
                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                        .contentType("application/json")
                        .expiration("100000") //消息过期时间
                        .headers(headers).build();
                //防止循环重复消费堵塞其他任务，添加处理消息到队列末尾
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                        message.getMessageProperties().getReceivedRoutingKey(), basicProperties,
                        message.getBody());
            } catch (IOException e) {
                try {
                    channel.basicReject(deliveryTag, false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    default MessageConsume deserialization(String content) {
        return JSON.parseObject(content, MessageConsume.class);
    }

    default MessageConsume deserializationByte(byte[] content) {
        return JSON.parseObject(content, MessageConsume.class);
    }
}
