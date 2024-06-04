<script setup lang="ts">
    import "@/assets/markdown.scss"
    import u from "@/components/create/gasp 高级动画.md?url"
    import {renderMarkdownToHtml} from "@/util/marked";
    import {ref, onMounted} from "vue";
    import DpTextEllipsis from "@/components/create/DpTextEllipsis.vue"
    import {processCallback} from "@/util/observer";
    import {ChatbubbleEllipsesSharp, Heart, Reload, Search} from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import {Menu, TimeOutline, FolderOpenOutline, BookmarkOutline} from "@vicons/ionicons5"
    import {defaultArticle, defaultBlogComment, defaultBlogCommentContent} from "@/d/defautls";
    import {Comment} from "@/d/vm/Comment";
    import {
        addCommentRequst,
        addLikeByArticleIdAndUserIdAndpersonIdRequest,
        deleteLikeByArticleIdAndUserIdRequest, getCommentByArticleIdRequest
    } from "@/config/request";
    import {useUserStore} from "@/config/store/user";
    import {useRoute, useRouter} from "vue-router";
    import {log} from "util";

    const router = useRouter()
    const route = useRoute()
    const articles = ref<BlogArticle>({...defaultArticle})
    const userStore = useUserStore()
    const topicContentHeight = ref<number>(0)
    const commentShow = ref<boolean>(false)
    const topicContentRef = ref<Element>()
    const preViewImages = ref<string[]>([])
    const html = ref<string>("")

    const props = withDefaults(defineProps<{ articles: BlogArticle }>(), {
        articles: {
            ...defaultArticle
        } as any
    })

    // 等待所有图片加载完成后滚动到底部
   function getImgLoadEd(dom:Element,callback:()=>void) {
        // 查询容器下所有图片
        let images = dom?.querySelectorAll('img');
       console.log(images)

        // Array.prototype.slice.call将images类数组转换成合法数组
        const promises = Array.prototype.slice.call(images).map(node => {
            return new Promise((resolve, reject) => {
                let loadImg = new Image();
                loadImg.src = node.src;
                loadImg.onload = () => {
                    console.log(loadImg.width);
                    resolve(node);
                }
            });
        });

        // 利用Promise.all监听所有图片加载完成
        Promise.all(promises).then(results => {
            callback && callback();
        }).catch(e => {
            console.error('网络异常或其他程序异常', e);
        })
    }
    const dp=ref<Element>()
    onMounted(function () {
        // console.log(props.articles)
        articles.value = props.articles
        renderMarkdownToHtml(props.articles?.articleContent?.articleMd as string).then(res => {
            if (res) {
                const dr = document.createElement('div')
                dr.style.cssText = "padding: 20px;width: 100%;background-color: var(--blog_background_color);"
                dr.className = "markdown-body"
                dr.innerHTML = res
                // console.log(res)
                topicContentRef.value?.append(dr)
                function loadingComplete():void{
                    console.log(dr.offsetHeight);
                    (dp.value as any).topicContentHeight = dr.offsetHeight
                    topicContentHeight.value = dr.offsetHeight
                    dr.remove()
                }
                getImgLoadEd(dr,loadingComplete)

            }
            html.value = res
        })
        const observer = new MutationObserver(function (mutations, observe) {
            codeBlockLineNumber(observe)
            observe.disconnect()
        });
        observer.observe((topicContentRef.value && topicContentRef.value.getElementsByClassName("markdown-body")[0]) as Node, {
            childList: true
        })
    })

    function codeBlockLineNumber(observe: MutationObserver) {
        if (topicContentRef.value) {
            let container = topicContentRef.value.getElementsByClassName("markdown-body")[0]
            new Promise((resolve, reject) => {
                resolve("")
            }).then(function () {
                let images = container.querySelectorAll("img")
                images.forEach(value => {
                    preViewImages.value.push(value.src)
                })
            })
        }
    }


    function addBrowseAction() {
        if (userStore.getUser.id) {
            const userId = userStore.getUser.id
            const personId = articles.value.user?.id as any
            const articleId = articles.value.articleContent?.articleId as any
            if (userId != personId) {
                addLikeByArticleIdAndUserIdAndpersonIdRequest(userId, personId, articleId, 1)
            }
        }
    }

    /*
    * 文章点赞
    * */
    function addLike() {
        const userId = userStore.getUser.id
        const personId = articles.value.user?.id as any
        const articleId = articles.value.articleContent?.articleId as any
        if (articles.value.likeUserId?.includes(userStore.getUser.id)) {
            deleteLikeByArticleIdAndUserIdRequest(userId, articleId).then(res => {
                if (res) {
                    articles.value?.likeUserId?.splice(articles.value.likeUserId?.indexOf(userStore.getUser.id), 1)
                    // (articles.value.likeCount as any) -= 1
                    // articles.value.isLike = 0
                }
            })
        } else {
            addLikeByArticleIdAndUserIdAndpersonIdRequest(userId, personId == userId ? 0 : personId, articleId, 0).then(res => {
                if (res) {

                    articles.value.likeUserId?.push(userStore.getUser.id)
                    // (articles.value.likeCount as any) += 1
                    // articles.value.isLike = 1
                }
            })
        }
    }

    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize) >= total) {
            pageInfo.loading = false
            pageInfo.pageInfo = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            }
        } else {
            pageInfo.pageInfo.pageNum++
        }
    }


    const recoverField = ref<any>() //需要回复消息的评论
    const rootFielld = ref<any>()  //回复的消息的更评论
    const inputValueField = ref<string>("") //回复评论的内容

    /*
    * 打开评论遮罩层
    * */
    const commentField = ref<{
        comments: BlogComment[]
        pageInfo: PageInfoType<BlogComment>,
        loading: boolean,
    }>({
        comments: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true
    })

    /*
     * 打开评论遮罩层，并请求文章评论
      * */
    function commentShowFun() {
        if (!commentShow.value) {
            commentField.value = {
                comments: [],
                pageInfo: {
                    total: 0,
                    pageNum: 1,
                    pageSize: 10,
                },
                loading: true
            }
        }
        const {pageNum, pageSize} = commentField.value.pageInfo
        getCommentByArticleIdRequest(articles.value.articleContent?.articleId as number, 0, pageNum, pageSize).then(res => {
            if (res) {
                commentField.value.comments = [...res.list as any]
                commentField.value.pageInfo = {...commentField.value.pageInfo, ...res}
                // commentField.value.pageInfo.pageNum++
                loadingAction(commentField.value)
                commentShow.value = !commentShow.value
            }
        })
    }

    //点击回复按钮的回调函数
    function recoverComment(rootItem, comment) {
        console.log(rootItem, comment)
        recoverField.value = comment
        rootFielld.value = rootItem
        inputValueField.value = ""
    }

    /*
    * 执行回复评论
    * */
    function recoverAction() {
        if (!inputValueField.value.trim().length) {
            window.$message.error("评论内容为空，没发评论哟")
            return;
        }
        const blogComment = {
            ...defaultBlogComment
        }
        if (recoverField.value) {

            blogComment.articleId = articles.value.articleContent?.articleId as any
            blogComment.userId = userStore.getUser.id
            blogComment.commentContent = {
                ...defaultBlogCommentContent
            }
            if (recoverField.value?.user?.id == userStore.getUser.id) {
                blogComment.personId = 0;
            } else {
                blogComment.personId = recoverField.value?.user?.id;
            }
            console.log(recoverField.value)
            blogComment.commentKeyId = rootFielld.value.commentContent?.commentId as number
            blogComment.commentContent.commentContent = inputValueField.value
        } else {
            blogComment.articleId = articles.value.articleContent?.articleId as any
            blogComment.userId = userStore.getUser.id
            blogComment.personId = 0;
            blogComment.commentContent = {
                ...defaultBlogCommentContent
            }
            blogComment.commentContent.commentContent = inputValueField.value
            blogComment.commentKeyId = 0 as number
        }
        console.log(blogComment)
        addCommentRequst(blogComment).then(res => {
            if (res) {
                console.log(res)
                res.isChildren = 0
                res.comments = []
                if (rootFielld.value) {
                    rootFielld.value.comments.push(ref<BlogComment>(res))
                } else {
                    commentField.value.comments.push(res)
                }
            }
        })
    }

    /*
    * 评论输入框事情焦点时回调的方法
    * */
    function commentInputBlurAction() {
        recoverField.value = null
        rootFielld.value = null
        inputValueField.value = ""
    }

    /**
     * 跳转到编辑页面
     * @param articleId
     */
    function editArticleAction(articleId: number) {
        router.push({
            name: "create",
            params: {
                type: "request",
                id: articleId
            }
        })
    }

    function deleteComment(comment) {
        commentField.value.comments.forEach((value, index) => {
            if (Object.is(value.commentContent?.commentId,comment.commentContent?.commentId)){
                commentField.value.comments.splice(index,1)
            }
        })
    }
</script>

<template>
    <n-drawer v-model:show="commentShow"
              :width="502" :placement="'right'">
        <n-drawer-content title="评论区" :native-scrollbar="false">
            <div v-for="(ele,index) in commentField.comments">
                <Comment @recoverComment="recoverComment" :key="index" @deleteComment="deleteComment"
                         :comment="ele"/>
            </div>
            <template #footer>
                <div style="width: 100%">
                    <n-input
                            v-model:value="inputValueField"
                            @blur="commentInputBlurAction" style="background-color: inherit;" type="textarea"
                            :autosize="{
                                minRows: 5,
                                maxRows: 5
                              }"
                            :placeholder="!recoverField?'输入评论内容...':'回复@'+recoverField?.user.userName"
                            round clearable>
                        <template #suffix>
                            <n-button @click="recoverAction" strong secondary type="success">
                                发送
                            </n-button>
                        </template>
                    </n-input>
                </div>
            </template>
        </n-drawer-content>
    </n-drawer>
    <div ref="topicContentRef" class="topicContent">
        <n-list>
            <template #footer>
                <n-divider/>
                <n-space vertical>
                    <n-flex justify="space-between">
                        <div @click="commentShowFun" style="display: flex;justify-content: center;align-items: center;">
                            <n-icon size="20">
                                <ChatbubbleEllipsesSharp/>
                            </n-icon>
                            <span style="line-height: 12px;text-align: center;margin-left: 7px;">
                                      {{articles?.commentCount?articles?.commentCount:""}}
                                </span>
                        </div>
                        <div @click="addLike" style="display: flex;justify-content: center;align-items: center;">
                            <n-icon :color="articles.isLike?'#DE576DFF':''" size="20">
                                <Heart/>
                            </n-icon>
                            <span style="line-height: 12px;text-align: center;">
                                      {{articles?.likeUserId?.length?articles?.likeUserId?.length:""}}
                                </span>
                        </div>
                        <div style="display: flex;justify-content: center;align-items: center;">
                            <n-icon size="20">
                                <EyeRegular/>
                            </n-icon>
                            <span style="line-height: 12px;text-align: center;">
                                       {{articles?.browseCount?articles?.browseCount:""}}
                                </span>
                        </div>
                    </n-flex>
                </n-space>
            </template>
            <viewer :images="preViewImages">
                <DpTextEllipsis @addBrowseAction="addBrowseAction" ref="dp" :topicContentHeight="topicContentHeight">
                    <n-list style="background-color: var(--blog_foreground_color);">
                        <n-list-item>
                            <n-space vertical>
                                <div style="font-size: 25px;font-weight: bold;">
                                    {{articles.articleTitle}}
                                </div>
                                <div>
                                    <n-space vertical>
                                        <div style="display: flex;;align-items: center;">
                                            <div style="display: flex;;align-items: center;">
                                                <n-icon size="20">
                                                    <TimeOutline/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                   {{articles.updateTime}}
                                                  </span>
                                            </div>
<!--                                            <n-tooltip v-if="route.params.userId == userStore.getUser.id"-->
<!--                                                       placement="top-start" trigger="hover">-->
<!--                                                <template #trigger>-->
<!--                                                    <n-icon @click.stop="editArticleAction(articles?.articleContent?.articleId)"-->
<!--                                                            size="14">-->
<!--                                                        <Create/>-->
<!--                                                    </n-icon>-->
<!--                                                </template>-->
<!--                                                编辑-->
<!--                                            </n-tooltip>-->
                                        </div>
                                        <div>
                                            <div style="display: flex;align-items: center;">
                                                <n-space>
                                                    <n-button v-for="la in articles?.articleLableClasss" size="tiny"
                                                              tertiary>
                                                        {{la.articleLables.artName}}
                                                    </n-button>
                                                </n-space>
                                            </div>
                                        </div>
                                    </n-space>
                                </div>
                            </n-space>
                        </n-list-item>
                    </n-list>
                    <div class="markdown-body" style="padding: 20px;width: 100%;" data-theme="light" v-html="html"/>
                </DpTextEllipsis>
            </viewer>
        </n-list>

    </div>
</template>

<style lang="scss" scoped>
    .topicContent {
        width: 100%;
    }

    ::v-deep(blockquote) {
        color: var(--blog_font_color_3);
    }

    ::v-deep(code) {
        color: var(--markdown_color-fg-muted)
    }

    ::v-deep(pre) {
        padding-bottom: 0px;

        &::-webkit-scrollbar {
            width: 3px !important;
            height: 8px;
        }

        code {
            color: inherit;
        }

        li {
            list-style: decimal !important;
        }
    }

    ::v-deep(.n-input__suffix) {
        .n-button {
            align-self: flex-end !important;
            margin-bottom: 12px;
        }
    }

    ::v-deep(.markdown-body) {
        box-sizing: border-box;
        padding: 0px !important;

        img:hover {
            cursor: pointer !important;
        }
    }

</style>
