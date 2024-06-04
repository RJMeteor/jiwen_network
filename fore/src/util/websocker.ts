import {useUserStore} from "@/config/store/user";
import {h} from "vue";
import {threadId} from "worker_threads";
import {NAvatar, NTag} from "naive-ui";
import {useRoute} from "vue-router";
import { useNotificationsSettingsStore } from "@/config/store/notificationsSettings";

const alterMessage = {
    collect: "文章收藏推送",
    followWithInterest: "关注消息推送",
    comment: "评论消息推送",
    like: "点赞消息推送",
    "privateLetter;chat": "私信消息推送"
}

interface NofiType<T> {
    type: string,
    message: T
}

// BlogAttention | BlogFavorite | BlogComment | BlogLikeBrowse | BlogChattingRecords
interface PassByValueType {
    type: string,
    sendUser: BlogUser,
    content: string,
    time: string
}

class WebsocketUtil {
    static webSocket: WebSocket;
    static proccseCallback = new Map<string, (msg: any) => void>()
    private openActive: boolean = false;
    private RetryCount: number = 0;
    private timer: any = null
    private heartTimer: any = null;
    // private heartTimeOut = 1000 * 60 * 5;
    private heartTimeOut = 1000 * 30;
    private timeout = 2000;

    constructor() {
        const userStore = useUserStore()
        const user: BlogUser = userStore.getUser
        if (!WebsocketUtil.webSocket) {
            WebsocketUtil.webSocket = new WebSocket(`${import.meta.env.BLOG_WEBSOCKET_URL}/${user.id}`)
            WebsocketUtil.webSocket.onclose = this.close.bind(this)
            WebsocketUtil.webSocket.onopen = this.open.bind(this)
            WebsocketUtil.webSocket.onerror = this.error.bind(this)
            WebsocketUtil.webSocket.onmessage = this.message.bind(this)
        }
    }

    private nofit(message: PassByValueType) {
        console.log(message)
        const userStore = useUserStore()
        const user: BlogUser = userStore.getUser
        if (user.id != message.sendUser.id && !message.type.includes(userStore.getRouteName)) {
            window.$notification.create({
                title: message.sendUser.userName,
                content: message.content.length > 10 ? message.content.substring(0, 10) + "..." : message.content,
                description: () =>
                    h(NTag, {
                            bordered: false,
                            type: "info",
                            size: "small"
                        },
                        {
                            default: () => `${alterMessage[message.type]}`
                        }),
                duration: 3000,
                meta: message.time,
                avatar: () =>
                    h(NAvatar, {
                        size: 'small',
                        round: true,
                        src: message.sendUser?.userImage
                    }),
            })
        }
    }

    /*
    * 连接成功
    * */
    private open(event: Event) {
        this.openActive = true;
        console.log("连接成功")
        this.heartCheck()

    }

    private message(e) {
        const userStore = useUserStore()
        let parse: any = JSON.parse(e.data);
        parse.message = JSON.parse(parse.message)
        console.log(parse)
        switch (parse.type) {
            case "collect": {
                if (!userStore.getnotiSet.collectActive) return
                let nofti: NofiType<BlogFavorite> = parse;
                this.nofit({
                    type: "collect",
                    sendUser: parse.message.user,
                    time: parse.message.updateTime,
                    content: `收藏了你的${parse.message.article.articleLimiter ? "话题:" : "文章:"}${parse.message.article.articleTitle}`
                })
                break
            }
            case "followWithInterest": {
                if (!userStore.getnotiSet.followActive) return
                let nofti: NofiType<BlogAttention> = parse;
                this.nofit({
                    type: "followWithInterest",
                    sendUser: parse.message.user,
                    time: parse.message.updateTime,
                    content: "关注了你"
                })
                break
            }
            case "comment": {
                if (!userStore.getnotiSet.commentActive) return
                let nofti: NofiType<BlogComment> = parse;
                this.nofit({
                    type: "comment",
                    sendUser: parse.message.sendUser,
                    time: parse.message.updateTime,
                    content: `收藏了你的${parse.message.article.articleLimiter ? "话题:" : "文章:"}${parse.message.commentContent.commentCount}`
                })
                break
            }
            case "like": {
                if (!userStore.getnotiSet.likeActive) return
                let nofti: NofiType<BlogLikeBrowse> = parse;
                this.nofit({
                    type: "like",
                    sendUser: parse.message.user,
                    time: parse.message.updateTime,
                    content: `点赞了你的${parse.message.article.articleLimiter ? "话题:" : "文章:"}${parse.message.article.articleTitle}`
                })
                break
            }
            case "privateLetter;chat": {
                if (!userStore.getnotiSet.privateLetterActive) return
                let nofti: NofiType<BlogChattingRecords> = parse;
                WebsocketUtil.proccseCallback.get("privateLetter;chat") && WebsocketUtil.proccseCallback.get("privateLetter;chat")!(parse.message)
                this.nofit({
                    type: "privateLetter;chat",
                    sendUser: parse.message.senderUser,
                    time: parse.message.updateTime,
                    content: parse.message.chattingRecordsContent.chattingRecordsContent
                })
                break
            }
        }
        // console.log(e.data)
    }

    /*
    * 心跳检测
    * */
    private heartCheck() {
        if (this.RetryCount < 10) {
            this.heartTimer && clearInterval(this.heartTimer);
            this.heartTimer = setInterval(() => {
                if (this.openActive) {
                    console.log(111)
                    WebsocketUtil.webSocket.send("hard")
                }
                ;
            }, this.heartTimeOut);
        }
    }

    /*
    * 错误
    * */
    private error(event: Event) {
        this.reConnect()
        this.RetryCount++
    }

    /*
    * 重连
    * */
    private reConnect() {
        if (this.RetryCount < 10) {
            if (this.openActive) return; //如果已经连上就不在重连了
            this.timer && clearTimeout(this.timer);
            this.heartTimer && clearInterval(this.heartTimer);
            this.timer = setTimeout(function () { // 延迟5秒重连  避免过多次过频繁请求重连
                new WebsocketUtil()
            }, this.timeout);
        }
    }

    /*
    * 关闭连接
    * */
    private close(event: CloseEvent) {
        this.reConnect()
        this.RetryCount++
    }
}

export {
    WebsocketUtil
}

