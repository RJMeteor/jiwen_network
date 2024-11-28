package com.renjia.blog.util.other;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Use for: 实现一个客户端
 * Explain:
 */
//@Component
//@ServerEndpoint("/chat/{auth}")
public class WebsocketClient implements Websocket.LinkPoint, Websocket.Client {
    private static WebsocketManagerUtil demoManager = WebsocketManagerUtil.getInstance();
    private String tag;
    private Session session;

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("auth") String auth) {
        this.session = session;
        this.tag = auth;

        demoManager.addClients(this);
        String msg = tag + " 上线了" + "目前在线 " + demoManager.all().size() + " 人";
        System.out.println(msg);
        demoManager.sendTextToOther(msg, this);
    }

    @OnMessage
    @Override
    public void onMessage(String message) {
        String msg = tag + ": " + message;
        System.out.println(msg);
        demoManager.sendTextToOther(msg, this);
    }

    @OnClose
    @Override
    public void onClose() {
        demoManager.removeClients(this);
        String msg = tag + " 下线了,目前在线 " + demoManager.all().size() + " 人";
        System.out.println(msg);
        demoManager.sendTextToOther(msg);
    }

    @OnError
    @Override
    public void onError(Session session, Throwable throwable) {
        System.out.println("出错");
        try {
            session.close();
            demoManager.removeClients(this);
            String msg = tag + " 离线了,目前在线 " + demoManager.all().size() + " 人";
            System.out.println(msg);
            demoManager.sendTextToOther(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public void sendText(String text) {
        try {
            session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Object object) {
        try {
            session.getBasicRemote().sendObject(object);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
