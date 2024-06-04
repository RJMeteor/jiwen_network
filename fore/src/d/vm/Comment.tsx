import {defineComponent, ref, Ref, watch, defineProps, computed} from "vue"
import {NThing, NAvatar, NButton, NSpace, NFlex, NTag, NIcon, NDivider, NText, NSpin} from "naive-ui";
import "./comment.scss"
import {Heart} from "@vicons/fa";
import {CaretForwardSharp, ChevronDownOutline} from "@vicons/ionicons5";
import {useUserStore} from "@/config/store/user";
import {defaultBlogComment} from "@/d/defautls";
import {deleteCommentRequst, getCommentByArticleIdRequest} from "@/config/request";
import {router} from "@/config/router";
import {menuOptions} from '@/config/data/personalHomepage'
// interface CommentType {
//     isChildren: boolean,
//     commentChildren: CommentType[]
//     sendUser: any,
//     acceptUser: any,
//     commentContent: string,
//     createTime: string,
//     commentId: number
// }

const Comment = defineComponent({
    props: {
        comment: {
            type: Object,
            default: null,
        }
    },
    emits: ["recoverComment", "deleteComment"],
    setup(props, context) {
        // const commentWatch: Object = props.comment
        const map = new Map()
        const p = ref<BlogComment>({...defaultBlogComment})
        const show = ref<boolean>(false)
        const userStore = useUserStore()
        const cloneRR = ref<BlogComment>({...defaultBlogComment})
        let pageInfo: PageInfoType<BlogComment> = {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        }

        //加载状态验证
        function loadingAction(pageInfo: any) {
            // emptyField.value = articles.value.length > 0 ? false : true
            const {total, pageNum, pageSize} = pageInfo
            if ((pageNum * pageSize + pageSize) >= total) {
                show.value = false
                pageInfo = {
                    total: 0,
                    pageNum: 1,
                    pageSize: 10,
                }
            }
        }

        //回复评论
        function recoverCommentAction(commentItem: BlogComment) {
            !p.value.comments && (p.value.comments = [])
            // const r = ref<BlogComment>({
            //     active: 0,
            //     article: undefined,
            //     articleId: 12,
            //     commentContent: {id: 2, commentContent: "ssssssssssssssssssss", commentId: 35},
            //     commentContentId: 2,
            //     commentKeyId: 0,
            //     id: 1,
            //     isChildren: 4,
            //     person: undefined,
            //     personId: 1,
            //     stated: 0,
            //     updateTime: "2024-03-08 17:54:02",
            //     user: {
            //         id: 5,
            //         userName: "任家",
            //         userImage: "https://blog-renjia.oss-cn-chengdu.aliyuncs.com/static/images/5/a307a2f4a5e14b079775ee7518ecc5c3.webp"
            //     } as any,
            //     userId: 2
            // })
            //  p.value.comments?.push(r)
            // console.log(p.value,1111111111111111111111111111111111111111)
            context.emit("recoverComment", p.value, commentItem)
        }

        //删除评论
        function deleteCommentAction(commentItem: BlogComment) {
            deleteCommentRequst(commentItem.commentContent?.commentId as number).then(res => {
                if (res) {
                    if (Object.is(((p as any).value as any)?.commentContent?.commentId, commentItem.commentContent?.commentId)) {
                        context.emit("deleteComment", commentItem)
                        return;
                    }
                    p.value.comments?.forEach((value, index) => {
                        if (Object.is(((value as any).value as any)?.commentContent?.commentId, commentItem.commentContent?.commentId)) {
                            console.log(Object.is(((value as any).value as any)?.commentContent?.commentId, commentItem.commentContent?.commentId))
                            p.value.comments?.splice(index, 1)
                        }
                    })
                }
            })
        }

        //跳转个人用户主页
        function toHomeAction(commentItem: BlogComment) {
            userStore.$patch({
                mobileMenu: menuOptions(commentItem?.user?.id as number) as any
            })
            router.push({
                name: "personalHomepage",
                params: {
                    userId: commentItem?.user?.id
                }
            })
        }

        function loadMore() {
            show.value = true
            const rootId = p.value.commentContent?.commentId as number
            const articleId = p.value.articleId;
            if (+rootId > 0) {
                // 发送加载数据请求
                getCommentByArticleIdRequest(articleId, +rootId, pageInfo.pageNum, pageInfo.pageSize).then(res => {
                    if (res.list) {
                        pageInfo = {...res}
                        for (let blogComment of res.list) {
                            const requestId = blogComment.commentContent?.commentId
                            if (!map.get(requestId)) {
                                p.value?.comments?.push(ref<BlogComment>(blogComment) as any)
                            }
                        }
                        pageInfo.pageNum++
                    }
                    show.value = false
                }).catch(error => {
                    show.value = false
                })
            }
        }

        function isolate(item) {
            // console.log(item,34343)
            return (
                <>
                    <NThing content-indented v-slots={
                        {
                            avatar: () => (
                                <>
                                    <NAvatar
                                        class="blog_cursor"
                                        onClick={toHomeAction}
                                        round
                                        size="medium"
                                        src={item.user.userImage}
                                    />
                                    {/*{item.sendUser}*/}
                                </>
                            ),
                            description: () => (
                                <div style="display:flex;align-items: center;">
                                            <span style="font-size:15px;line-height:20px;margin-right:5px;">
                                                {item.user.userName}
                                            </span>
                                    <NTag
                                        style={item.user.id == userStore.getUser.id ? "display:flex;align-items: center;" : "display:none;"}
                                        size="small" type="error">
                                        作者
                                    </NTag>
                                    <div
                                        style={item?.person ? "display:none;" : "display:flex;align-items: center;"}>
                                        <NIcon depth="3"
                                               style={item.user.id != userStore.getUser.id ? "display:flex;align-items: center;" : "display:none;"}
                                               size={14}>
                                            <CaretForwardSharp/>
                                        </NIcon>
                                        <NText depth="3">
                                            {/*<span>renjia</span>*/}
                                            {item?.person?.userName}
                                        </NText>
                                    </div>

                                </div>
                            ),
                            default: () => (
                                <>
                                    {item.commentContent.commentContent}
                                </>
                            ),
                            footer: () => (
                                <>
                                    <NSpace vertical>
                                        <NFlex justify="space-between">
                                            <NSpace>
                                                <NText depth="3">
                                                    {/*2023-1-18*/}
                                                    {item.updateTime}
                                                </NText>
                                                <NButton onClick={() => recoverCommentAction(item)} text>
                                                    回复
                                                </NButton>
                                                {userStore.getUser.id == item.user.id ?
                                                    (<NButton onClick={() => deleteCommentAction(item)} type="error"
                                                              text>
                                                        删除
                                                    </NButton>) : ""}
                                            </NSpace>
                                            <NSpace>
                                                {/*<NIcon class="blog_cursor" size="18" color="#D03050FF">*/}
                                                {/*    <Heart/>*/}
                                                {/*</NIcon>*/}
                                                {/*12*/}
                                            </NSpace>
                                        </NFlex>
                                        {item?.comments && childrenComment(item?.comments)}
                                    </NSpace>
                                </>
                            )
                        }}/>


                </>
            )
        }

        function childrenComment(item) {
            return Array.isArray(item) ? (item != undefined && item.length != 0 &&
                item.map(itemd => itemd.value && isolate(itemd.value))) : (
                <>
                    <NSpin show={show.value}>
                        {item && isolate(item)}
                    </NSpin>
                    <div style={item?.isChildren != 0 ? "display:block;margin-left:45px;" : "display:none;"}>
                        <NButton onClick={() => loadMore()} disabled={show.value} type="info" text>
                            展开更多
                            <NIcon>
                                <ChevronDownOutline/>
                            </NIcon>
                        </NButton>
                        <NDivider/>
                    </div>
                    <div style={item?.isChildren != 0 ? "display:none;" : "display:block;margin-left:45px;"}>
                        <NDivider/>
                    </div>
                </>
            )
        }

        const compute = computed(() => {
            p.value = {...props.comment} as BlogComment
            p.value.comments = []
            return null;
        })

        const render = ref<any>()
        watch(p, value => {
            p.value && p.value.comments?.forEach(value => {
                const localId = value.commentContent?.commentId
                !map.get(localId) && map.set(localId, "jjjj")
            })
            // console.log("wathck")
            render.value = childrenComment(p.value);
        }, {deep: true})

        return () => (
            <>
                <div style="display:none">
                    {compute.value}
                </div>
                {
                    render.value
                }
            </>
        )

    }
})

export {
    Comment
}
export type {}