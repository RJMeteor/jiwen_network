package com.renjia.blog.util;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{id}")
@Component
public class WebSocketUtil {


    public static final ConcurrentHashMap<Long, Session> webSocketClient = new ConcurrentHashMap<Long, Session>();


    @OnClose
    public void close(Session session) {
        System.out.println("关闭");
        for (Map.Entry<Long, Session> longSessionEntry : webSocketClient.entrySet()) {
            if (longSessionEntry.getValue() == session) {
                webSocketClient.remove(longSessionEntry.getKey());
                break;
            }
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") String userId) throws Exception {
        System.out.println("连接" + userId);
        if (webSocketClient.contains(Long.valueOf(userId))) {
            webSocketClient.remove(Long.valueOf(userId));
        }
        webSocketClient.put(Long.valueOf(userId), session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        for (Map.Entry<Long, Session> longSessionEntry : webSocketClient.entrySet()) {
            if (longSessionEntry.getValue() == session) {
                webSocketClient.remove(longSessionEntry.getKey());
                break;
            }
        }
    }

    public static void sendSingleUserMessage(WebsocketMessage websocketMessage, Long sendUserId, Long acceptorUserId) {
//        Session senderSession = WebSocketUtil.webSocketClient.get(sendUserId);
        Session acceptorSession = WebSocketUtil.webSocketClient.get(acceptorUserId);

        String messageJson = JSONObject.toJSONString(websocketMessage);
//        if (!ObjectUtils.isEmpty(senderSession)) {
//            senderSession.getAsyncRemote().sendText(messageJson);
//        }
        if (!ObjectUtils.isEmpty(acceptorSession)) {
            acceptorSession.getAsyncRemote().sendText(messageJson);
        }
    }

}
