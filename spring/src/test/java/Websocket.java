import com.renjia.blog.util.other.WebsocketManagerUtil;
import com.renjia.blog.util.other.WebsocketClient;

/**
 * http://localhost:8088/api/ws/send?auth=chris&tags=chris,bill&msg=hello,chris and bill
 */
public class Websocket {
    private static WebsocketManagerUtil demoManager = WebsocketManagerUtil.getInstance();

    /**
     * 向指定客户端发送消息
     *
     * @param auth
     * @param tags
     * @param msg
     * @return
     */
    public String send(String auth, String tags, String msg) {
        //模拟auth发送者，这里可根据http请求携带的token来分析
        WebsocketClient authCli = demoManager.getClientByTag(auth);
        //获取目标客户端
        WebsocketClient[] clients = demoManager.getClientsByTags(tags.split(","));
        //发送消息
        demoManager.sendText(msg, clients);
        return "send success.";
    }

    /**
     * 向指定客户端发送消息
     *
     * @param auth
     * @param tags
     * @param msg
     * @return
     */
    public String sendToOther(String auth, String tags, String msg) {
        WebsocketClient authCli = demoManager.getClientByTag(auth);
        WebsocketClient[] clients = demoManager.getClientsByTags(tags.split(","));
        demoManager.sendTextToOther( msg, clients);
        return "send success.";
    }
}
