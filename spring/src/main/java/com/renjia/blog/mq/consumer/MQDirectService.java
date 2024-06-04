package com.renjia.blog.mq.consumer;


import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.Channel;
import com.renjia.blog.mapper.*;
import com.renjia.blog.mq.Handler;
import com.renjia.blog.mq.HandlerImpl;
import com.renjia.blog.mq.MQType;
import com.renjia.blog.mq.producer.MessageProcessingChain;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.netty.WebSocketHandler;
import com.renjia.blog.pojo.*;
import com.renjia.blog.util.BeanUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;


@Component
@ConditionalOnBean(BeanUtil.class)
public class MQDirectService {

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    private Integer retryCountMax;

    private static Handler handler;

    @Resource
    private BlogChattingRecordsMapper blogChattingRecordsMapper;
    @Resource
    private BlogCommentMapper blogCommentMapper;
    @Resource
    private BlogFavoriteMapper blogFavoriteMapper;
    @Resource
    private BlogLikeBrowseMapper blogLikeBrowseMapper;
    @Resource
    private BlogAttentionMapper blogAttentionMapper;


    @PostConstruct
    public void init() {
        handler = new HandlerImpl();
    }

    private Consumer<Object> retryCallback(Message message, Channel channel) {
        return (object) -> {
            handler.retry(message, channel, retryCountMax, handler.consumer(message, channel), (messageConsume -> {
                try {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        };
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = MQType.comment + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.comment + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.comment)
                    }),
            exchange = @Exchange(value = MQType.comment + MQType.exchangesufix),
            key = MQType.comment), concurrency = "10")
    public void comment(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            Long deserialization = JSON.parseObject(parse.getMessage(), Long.class);
            BlogComment commentById = blogCommentMapper.getCommentById(deserialization);
            if (ObjectUtils.isEmpty(commentById)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(commentById.getPerson().getId()));
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(commentById));
                WebSocketHandler.sendSingleUserMessage(parse, commentById.getUser().getId(), commentById.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.collect + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.collect + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.collect)
                    }),
            exchange = @Exchange(value = MQType.collect + MQType.exchangesufix),
            key = MQType.collect), concurrency = "10")
    public void collect(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogFavorite favoritesById = blogFavoriteMapper.getFavoritesById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(favoritesById)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(favoritesById.getPerson().getId()));
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(favoritesById));
                WebSocketHandler.sendSingleUserMessage(parse, favoritesById.getUser().getId(), favoritesById.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.followWithInterest + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.followWithInterest + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.followWithInterest)
                    }),
            exchange = @Exchange(value = MQType.followWithInterest + MQType.exchangesufix),
            key = MQType.followWithInterest), concurrency = "10")
    public void followWithInterest(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogAttention attentionsById = blogAttentionMapper.getAttentionsById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(attentionsById)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(attentionsById.getAttentionUser().getId()));
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(attentionsById));
                WebSocketHandler.sendSingleUserMessage(parse, attentionsById.getUser().getId(), attentionsById.getAttentionUser().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.privateLetter + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.privateLetter + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.privateLetter)
                    }),
            exchange = @Exchange(value = MQType.privateLetter + MQType.exchangesufix),
            key = MQType.privateLetter), concurrency = "10")
    public void privateLetter(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogChattingRecords blogChattingRecords = blogChattingRecordsMapper.getChat(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(blogChattingRecords)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(blogChattingRecords.getAcceptorUser().getId()));
            System.out.println("MQDirectService" + isOnlie);
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(blogChattingRecords));
                WebSocketHandler.sendSingleUserMessage(parse, blogChattingRecords.getSenderUser().getId(), blogChattingRecords.getAcceptorUser().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.like + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.like + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.like)
                    }),
            exchange = @Exchange(value = MQType.like + MQType.exchangesufix),
            key = MQType.like), concurrency = "10")
    public void like(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogLikeBrowse blogLikeBrowse = blogLikeBrowseMapper.getLikeById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(blogLikeBrowse)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(blogLikeBrowse.getPerson().getId()));
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(blogLikeBrowse));
                WebSocketHandler.sendSingleUserMessage(parse, blogLikeBrowse.getUser().getId(), blogLikeBrowse.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.other + MQType.queuesufix, durable = "true", autoDelete = "false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = MQType.other + MQType.DLXsufix + MQType.exchangesufix),
                            @Argument(name = "x-dead-letter-routing-key", value = MQType.other)
                    }),
            exchange = @Exchange(value = MQType.other + MQType.exchangesufix),
            key = MQType.other), concurrency = "10")
    public void other(Message message, Channel channel) {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (o1) -> {
            MessageProcessingChain.Node node = MessageProcessingChain.chain.handle(o1);
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(node.personId));
            if (isOnlie) {
                parse.setMessage(JSON.toJSONString(node.value));
                WebSocketHandler.sendSingleUserMessage(parse, node.userId, node.personId);
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }
}