package com.renjia.blog.netty;

import com.alibaba.fastjson2.JSONObject;
import com.renjia.blog.mq.producer.ProductionStrategy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<Long, ChannelId> userMap = new ConcurrentHashMap<>();

    public static Channel isUserOnline(Long userId) {
        return WebSocketHandler.channelGroup.find(WebSocketHandler.userMap.get(userId));
    }


    public static void sendSingleUserMessage(MessageConsume message, Long sendUserId, Long acceptorUserId) {
        ChannelId channelId = userMap.get(acceptorUserId);
        if (channelId != null) {
            Channel ct = channelGroup.find(channelId);
            if (ct != null) {
                System.out.println(11111);
                //发送的消息必须是TextWebSocketFrame实例，否则客户端接受不到消息
                ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(message)));
            }
        }
    }


    public static Long getUrlParams(String url) {
        if (!url.contains("we")) {
            return null;
        }
        String userId = url.substring(url.lastIndexOf("/") + 1);
        return Long.parseLong(userId);
    }
}
