package com.renjia.blog.util.other;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class WebsocketManagerUtil implements Websocket.Manager<WebsocketClient> {
    private static CopyOnWriteArraySet<WebsocketClient> linkSet = new CopyOnWriteArraySet<>();

    //单例
    private static WebsocketManagerUtil instance = new WebsocketManagerUtil();

    private WebsocketManagerUtil() {
    }

    public static WebsocketManagerUtil getInstance() {
        return instance;
    }

    @Override
    public void sendText(String text, WebsocketClient... clients) {
        for (WebsocketClient client : clients) {
            client.sendText(text);
        }
    }

    @Override
    public void sendTextYoAll(String text) {
        for (WebsocketClient demoClient : linkSet) {
            demoClient.sendText(text);
        }
    }

    @Override
    public void send(Object object, WebsocketClient... clients) {
        for (WebsocketClient client : clients) {
            client.send(object);
        }
    }

    @Override
    public void sendToAll(Object object) {
        for (WebsocketClient demoClient : linkSet) {
            demoClient.send(object);
        }
    }

    @Override
    public void sendTextToOther(String text, WebsocketClient... clients) {
        Set<String> tagSet = Arrays.stream(clients).map(WebsocketClient::getTag).collect(Collectors.toSet());
        for (WebsocketClient demoClient : linkSet) {
            if (tagSet.contains(demoClient.getTag())) {
                continue;
            }
            demoClient.sendText(text);
        }
    }

    @Override
    public void sendToOther(Object object, WebsocketClient... clients) {
        Set<String> tagSet = Arrays.stream(clients).map(WebsocketClient::getTag).collect(Collectors.toSet());
        for (WebsocketClient demoClient : linkSet) {
            if (tagSet.contains(demoClient.getTag())) {
                continue;
            }
            demoClient.send(object);
        }
    }

    @Override
    public void addClients(WebsocketClient... clients) {
        linkSet.addAll(Arrays.asList(clients));
    }

    @Override
    public CopyOnWriteArraySet<WebsocketClient> all() {
        return linkSet;
    }

    @Override
    public void removeClients(WebsocketClient... clients) {
        for (WebsocketClient client : clients) {
            linkSet.remove(client);
        }
    }

    @Override
    public WebsocketClient getClientByTag(String tag) {
        for (WebsocketClient demoClient : linkSet) {
            if (demoClient.getTag().equals(tag)) {
                return demoClient;
            }
        }
        return null;
    }

    @Override
    public WebsocketClient[] getClientsByTags(String... tags) {
        if (null == tags || tags.length == 0) {
            return null;
        }
        Set<String> tagSet = Arrays.stream(tags).collect(Collectors.toSet());
        List<WebsocketClient> clientList = linkSet.stream().filter(c -> tagSet.contains(c.getTag())).collect(Collectors.toList());
        WebsocketClient[] clients = new WebsocketClient[clientList.size()];
        clientList.toArray(clients);
        return clients;
    }
}
