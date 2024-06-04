package com.renjia.blog.mq;

import com.rabbitmq.client.AMQP;
import com.renjia.blog.mq.producer.ProductionStrategy;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        //防止循环重复消费堵塞其他任务，添加处理消息到队列末尾
        ProductionStrategy.sendDoCallback(message.getMessageProperties().getReceivedExchange(),
                message.getMessageProperties().getReceivedRoutingKey(),
                message.getBody());
    }
}