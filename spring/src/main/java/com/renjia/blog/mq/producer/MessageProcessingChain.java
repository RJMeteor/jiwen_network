package com.renjia.blog.mq.producer;

import com.alibaba.fastjson2.JSON;
import com.renjia.blog.mapper.*;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.pojo.*;
import com.renjia.blog.util.BeanUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Function;


public class MessageProcessingChain {
    public static MessageProcessingChain chain;
    private MessageConsume doHandlerTarget;
    private Node lastValue;
    private Node fristValue;
    private static BlogCommentMapper blogCommentMapper;
    private static BlogAttentionMapper blogAttentionMapper;
    private static BlogLikeBrowseMapper blogLikeBrowseMapper;
    private static BlogChattingRecordsMapper blogChattingRecordsMapper;
    private static BlogFavoriteMapper blogFavoriteMapper;
    private static volatile Boolean handler = false;

    public MessageProcessingChain() {
        if (!handler) {
            synchronized (handler) {
                if (!handler) {
                    blogCommentMapper = BeanUtil.getBean(BlogCommentMapper.class);
                    blogAttentionMapper = BeanUtil.getBean(BlogAttentionMapper.class);
                    blogLikeBrowseMapper = BeanUtil.getBean(BlogLikeBrowseMapper.class);
                    blogChattingRecordsMapper = BeanUtil.getBean(BlogChattingRecordsMapper.class);
                    blogFavoriteMapper = BeanUtil.getBean(BlogFavoriteMapper.class);
                    handler = true;
                }
            }
        }
    }

    private Function<Node, Node> COMMENT = node -> {
        Long id = JSON.parseObject(this.doHandlerTarget.getMessage(), Long.class);
        BlogComment o = blogCommentMapper.getCommentById(id);
        node.value = o;
        node.personId = Optional.ofNullable(o.getPerson()).map(BlogUser::getId).orElse(null);
        node.userId = Optional.ofNullable(o.getUser()).map(BlogUser::getId).orElse(null);
        node.key = MessageConsume.MessageType.COMMENT.getType() + "_" + node.personId + "_" + node.userId;
        return node;
    };

    private Function<Node, Node> FAVORITE = node -> {
        Long id = JSON.parseObject(this.doHandlerTarget.getMessage(), Long.class);
        BlogFavorite o = blogFavoriteMapper.getFavoritesById(id);
        node.value = o;
        node.personId = Optional.ofNullable(o.getPerson()).map(BlogUser::getId).orElse(null);
        node.userId = Optional.ofNullable(o.getUser()).map(BlogUser::getId).orElse(null);
        node.key = MessageConsume.MessageType.FAVORITE.getType() + "_" + node.personId + "_" + node.userId;
        return node;
    };

    private Function<Node, Node> CHATTINGRECORDS = node -> {
        Long id = JSON.parseObject(this.doHandlerTarget.getMessage(), Long.class);
        BlogChattingRecords o = blogChattingRecordsMapper.getChat(id);
        node.value = o;
        node.personId = Optional.ofNullable(o.getAcceptorUser()).map(BlogUser::getId).orElse(null);
        node.userId = Optional.ofNullable(o.getSenderUser()).map(BlogUser::getId).orElse(null);
        node.key = MessageConsume.MessageType.CHATTINGRECORDS.getType() + "_" + node.personId + "_" + node.userId;
        return node;
    };

    private Function<Node, Node> ATTENTION = node -> {
        Long id = JSON.parseObject(this.doHandlerTarget.getMessage(), Long.class);
        BlogAttention o = blogAttentionMapper.getAttentionsById(id);
        node.value = o;
        node.personId = Optional.ofNullable(o.getAttentionUser()).map(BlogUser::getId).orElse(null);
        node.userId = Optional.ofNullable(o.getUser()).map(BlogUser::getId).orElse(null);
        node.key = MessageConsume.MessageType.ATTENTION.getType() + "_" + node.personId + "_" + node.userId;
        return node;
    };

    private Function<Node, Node> LIKE = node -> {
        Long id = JSON.parseObject(this.doHandlerTarget.getMessage(), Long.class);
        BlogLikeBrowse o = blogLikeBrowseMapper.getLikeById(id);
        node.value = o;
        node.personId = Optional.ofNullable(o.getPerson()).map(BlogUser::getId).orElse(null);
        node.userId = Optional.ofNullable(o.getUser()).map(BlogUser::getId).orElse(null);
        node.key = MessageConsume.MessageType.LIKE.getType() + "_" + node.personId + "_" + node.userId;
        return node;
    };


    public MessageProcessingChain pushChai(Node node) {
        if (this.fristValue == null) {
            this.lastValue = node;
            this.fristValue = node;
        }
        this.lastValue.next = node;
        this.lastValue = node;
        return this;
    }

    public MessageProcessingChain initChai() {
        chain = this.pushChai(new Node(MessageConsume.MessageType.FAVORITE, this.FAVORITE))
                .pushChai(new Node(MessageConsume.MessageType.COMMENT, this.COMMENT))
                .pushChai(new Node(MessageConsume.MessageType.CHATTINGRECORDS, this.CHATTINGRECORDS))
                .pushChai(new Node(MessageConsume.MessageType.ATTENTION, this.ATTENTION))
                .pushChai(new Node(MessageConsume.MessageType.LIKE, this.LIKE));
        return chain;
    }

    public Node handle(MessageConsume doHandlerTarget) {
        return this.fristValue.doHandle(doHandlerTarget);
    }

    public static class Node {
        public Long personId; //上级ID
        public Long userId; //下级ID
        public String key; // redis key
        public Object value; // 消息数据
        private Node next; //下一个节点
        private Function<Node, Node> result; //处理回调
        public MessageConsume.MessageType type; //消息类型

        public Node(MessageConsume.MessageType type, Function<Node, Node> result) {
            this.type = type;
            this.result = result;
        }

        public Node doHandle(MessageConsume message) {
            if (!this.type.getType().equals(message.getType())) return next.doHandle(message);
            return result.apply(this);
        }
    }
}
