package com.renjia.blog.mq.consumer;

import com.alibaba.fastjson2.JSON;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
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
import com.renjia.blog.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BadFaithService {

    public static final String dxlNo = "dxl:_";  //死信队列无法处理，放入redis的hash键（key）的前缀

    @Autowired
    private RedisUtil redisUtil;

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

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    private Integer retryCountMax;

    private static Handler handler;


    @PostConstruct
    public void init() {
        handler = new HandlerImpl();
    }

    private <T> Consumer<T> retryCallback(Message message, Channel channel) {
        return (T object) -> {
            handler.retry(message, channel, retryCountMax, handler.consumer(message, channel), (o1 -> {
                MessageProcessingChain.Node node = MessageProcessingChain.chain.handle(o1);
                if (node.key != null) {
                    String messages = JSON.toJSONString(o1);
                    String register = BadFaithService.dxlNo + node.personId;
                    redisUtil.hset(register, node.key, messages);
                }
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        };
    }


    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.comment + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.comment + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.comment), concurrency = "10")
    public void comment(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            Long deserialization = JSON.parseObject(parse.getMessage(), Long.class);
            BlogComment commentById = blogCommentMapper.getCommentById(deserialization);
            if (ObjectUtils.isEmpty(commentById)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(commentById.getPerson().getId()));
            if (isOnlie) {
                WebSocketHandler.sendSingleUserMessage(parse, commentById.getUser().getId(), commentById.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.collect + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.collect + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.collect), concurrency = "10")
    public void collect(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogFavorite favoritesById = blogFavoriteMapper.getFavoritesById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(favoritesById)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(favoritesById.getPerson().getId()));
            if (isOnlie) {
                WebSocketHandler.sendSingleUserMessage(parse, favoritesById.getUser().getId(), favoritesById.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.followWithInterest + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.followWithInterest + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.followWithInterest), concurrency = "10")
    public void followWithInterest(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogAttention attention = blogAttentionMapper.getAttentionsById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(attention)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(attention.getAttentionUser().getId()));
            if (isOnlie) {
                WebSocketHandler.sendSingleUserMessage(parse, attention.getUser().getId(), attention.getAttentionUser().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.privateLetter + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.privateLetter + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.privateLetter), concurrency = "10")
    public void privateLetter(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogChattingRecords chattingRecords = blogChattingRecordsMapper.getChat(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(chattingRecords)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(chattingRecords.getAcceptorUser().getId()));
            System.out.println("MQDirectService" + isOnlie);
            if (isOnlie) {
                WebSocketHandler.sendSingleUserMessage(parse, chattingRecords.getSenderUser().getId(), chattingRecords.getAcceptorUser().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.like + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.like + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.like), concurrency = "10")
    public void like(Message message, Channel channel) throws IOException {
        MessageConsume parse = handler.deserializationByte(message.getBody());
        handler.handle(message, channel, retryCountMax, (bl) -> {
            BlogLikeBrowse likeBrowse = blogLikeBrowseMapper.getLikeById(JSON.parseObject(parse.getMessage(), Long.class));
            if (ObjectUtils.isEmpty(likeBrowse)) return true;
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(likeBrowse.getPerson().getId()));
            if (isOnlie) {
                WebSocketHandler.sendSingleUserMessage(parse, likeBrowse.getUser().getId(), likeBrowse.getPerson().getId());
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQType.other + MQType.DLXsufix + MQType.queuesufix, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = MQType.other + MQType.DLXsufix + MQType.exchangesufix),
            key = MQType.other), concurrency = "10")
    public void other(Message message, Channel channel) {
        handler.handle(message, channel, retryCountMax, (o1) -> {
            MessageProcessingChain.Node node = MessageProcessingChain.chain.handle(o1);
            Boolean isOnlie = !ObjectUtils.isEmpty(WebSocketHandler.isUserOnline(node.personId));
            if (isOnlie) {
                MessageConsume messageConsume = handler.deserializationByte(message.getBody());
                messageConsume.setMessage(JSON.toJSONString(node.value));
                WebSocketHandler.sendSingleUserMessage(messageConsume, node.userId, node.personId);
            }
            return isOnlie;
        }, retryCallback(message, channel));
    }
}
