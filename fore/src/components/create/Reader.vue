<script setup lang="tsx">
    import {Component, defineComponent, Ref, h, onMounted, onUnmounted, ref, nextTick} from "vue"
    import FavoritesClass from "@/components/personal/FavoritesClass.vue"
    import {
        ChatbubbleEllipsesSharp,
        TimeOutline,
        Menu,
        Heart,
        StarSharp
    } from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import {renderMarkdownToHtml} from "@/util/marked";
    import "@/assets/markdown.scss"
    import {listener, tocbotInit, tocObserver} from "@/util/observer";
    import {Comment} from "@/d/vm/Comment";
    import 'tocbot/src/scss/tocbot.scss'
    import {commentShow, favoriteShow} from "@/config/data/reader";
    import {useRoute, useRouter} from "vue-router";
    import {
        addAttentionRequest,
        addCommentRequst,
        addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest,
        addLikeByArticleIdAndUserIdAndpersonIdRequest,
        deleteAttentionRequest,
        deleteFavoriteByUserIdAndArticleIdRequest,
        deleteLikeByArticleIdAndUserIdRequest,
        getArticlesByUserIdAndArticlesIdRequest,
        getCommentByArticleIdRequest,
    } from "@/config/request";
    import {defaultArticle, defaultBlogComment, defaultBlogCommentContent} from "@/d/defautls";
    import {useUserStore} from "@/config/store/user";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {changeTheme, themeState} from "@/config/data/app";
    import {menuOptions} from "@/config/data/personalHomepage";
    import {menuOptions as menuOptionsHome} from '@/config/data/personalHomepage'

    const router = useRouter()
    const route = useRoute()
    const userStore = useUserStore()

    const {userId, articleId} = route.params
    const html = ref<string>("")
    const articles = ref<BlogArticle>({...defaultArticle})
    const favoritesClassRef = ref<{
        favoritesRequest: () => void;
    }>()

    function favoritesClassAction() {
        favoritesClassRef.value && favoritesClassRef.value.favoritesRequest()
    }

    const tocObserverFun = tocObserver()
    onMounted(function () {
        /*
        * 请求文章数据
        * */
        nextTick(function () {
            let elementById = document.getElementById(`toc23`)
            elementById!.parentElement!.parentElement!.id = "renjia_01"

            getArticlesByUserIdAndArticlesIdRequest(+userId, +articleId).then(res => {
                if (res) {
                    articles.value = res
                    const userId = userStore.getUser.id
                    const personId = articles.value.user?.id as any
                    const articleId = articles.value.articleContent?.articleId as any
                    if (userId != personId) {
                        addLikeByArticleIdAndUserIdAndpersonIdRequest(userId, personId, articleId, 1)
                    }
                    renderMarkdownToHtml(res.articleContent?.articleMd as any).then(res => {
                        html.value = res
                        tocbotInit()
                    })
                }
            })
            listener()
            const dom: any = document.getElementById("toc23")
            tocObserverFun.observe(dom, {
                childList: true,
                subtree: true,
                attributes: true
            })
        })
    })
    onUnmounted(function () {
        tocObserverFun.disconnect()
        favoriteShow.value = false
    })

    /*
   * 收藏文章，收藏自己的文章personId 为0
   * */
    function addFavorite(favoriteClass: BlogFavoriteClass) {
        const userid = userStore.getUser.id
        const personId = articles.value.user?.id as number
        // console.log(articles.value)
        const articleId: any = articles.value.articleContent?.articleId
        addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest(userid, personId == userid ? 0 : personId, favoriteClass.id, articleId).then(res => {
            if (res) {
                articles.value.favoriteUserId.push(userStore.getUser.id)
                favoriteShow.value = false
            }
        })
    }

    /**
     * 显示收藏夹遮罩层
     */

    function favoriteShowFun() {
        if (articles.value.favoriteUserId.includes(userStore.getUser.id)) {
            deleteFavoriteByUserIdAndArticleIdRequest(userStore.getUser.id, articles.value.articleContent?.articleId as number).then(res => {
                if (res) {
                    articles.value.favoriteUserId.splice(articles.value.favoriteUserId.indexOf(userStore.getUser.id), 1)
                }
            })
        } else {
            favoritesClassAction()
            favoriteShow.value = !favoriteShow.value
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
                console.log(res)
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
        if ((pageNum * pageSize + pageSize) >= total) {
            pageInfo.loading = false
            pageInfo.pageInfo = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            }
        }
    }

    function addAttentionAction(userId: number) {
        const localUserId = userStore.getUser.id
        const user = articles.value?.user
        if (user?.fensUserId && user.fensUserId.includes(localUserId)) {
            deleteAttentionRequest(userId, localUserId).then(res => {
                if (res) {
                    user?.fensUserId.splice(user?.fensUserId?.indexOf(localUserId), 1)
                }
            })
        } else {
            addAttentionRequest(userId, localUserId).then(res => {
                if (res) {
                    const fensUserIds = user?.fensUserId
                    if (fensUserIds) {
                        fensUserIds.push(userStore.getUser.id);
                    } else {
                        (articles.value?.user as any).fensUserId.pash(userStore.getUser.id) as any
                    }
                }
            })
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
                commentField.value.pageInfo.pageNum++
                loadingAction(commentField.value)
                commentShow.value = !commentShow.value
            }
        })
    }

    //点击回复按钮的回调函数
    function recoverComment(rootItem, comment) {
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
            blogComment.personId = (userStore.getUser.id != articles.value?.user?.id ? articles.value?.user?.id : 0) as number;
            blogComment.commentContent = {
                ...defaultBlogCommentContent
            }
            blogComment.commentContent.commentContent = inputValueField.value
            blogComment.commentKeyId = 0 as number
        }
        // console.log(blogComment)
        addCommentRequst(blogComment).then(res => {
            if (res) {
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


    function toChatViewAction(userId: number) {
        let routeUrl = router.resolve({
            name: "chat",
            params: {
                userId
            }
        });
        window.open(routeUrl.href, '_blank');
    }

    function deleteComment(comment) {
        commentField.value.comments.forEach((value, index) => {
            if (Object.is(value.commentContent?.commentId, comment.commentContent?.commentId)) {
                commentField.value.comments.splice(index, 1)
            }
        })
    }

    function toHomeAction(userId: number) {
        userStore.$patch({
            mobileMenu: menuOptionsHome(userId) as any
        })
        let routeUrl = router.resolve({
            name: 'personalHomepage',
            params: {
                userId
            }
        });
        window.open(routeUrl.href, '_blank');
    }
</script>

<template>
    <FavoritesClass v-if="favoriteShow" @addFavorite="addFavorite" ref="favoritesClassRef"/>
    <n-drawer v-model:show="commentShow"
              :width="502" :placement="'right'">
        <n-drawer-content title="评论区" :native-scrollbar="false">
            <!--                        :rootLoading="commentField.loading"-->
            <div v-for="(ele,index) in commentField.comments" :key="index">
                <Comment @recoverComment="recoverComment" @deleteComment="deleteComment" :key="index"
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
                            :placeholder="!recoverField?'输入评论内容...':'回复@'+recoverField.user.userName"
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
    <div id="mobile">
        <n-list bordered>
            <n-list-item>
                <div style="display: flex;">
                    <div>
                        <n-avatar
                                round
                                :size="100"
                                @click="toHomeAction(articles?.user?.id)"
                                :src="articles.user?.userImage"
                        />
                    </div>
                    <div style="margin-left: 20px;width: 100%">
                        <n-thing>
                            <template #description>
                                <div style="display: flex;justify-content: space-between;flex: 1">
                                    <n-space justify="space-between" style="flex: 1">
                                        <div>
                                            <n-gradient-text :size="24" type="success">
                                                {{articles.user?.userName}}
                                            </n-gradient-text>
                                        </div>
                                        <div>
                                            <n-flex justify="space-between" size="large">
                                                <n-button
                                                        size="small"
                                                        @click="addAttentionAction(articles?.user?.id)"
                                                        v-if="userStore.getUser.id != articles?.user?.id"
                                                        strong secondary type="tertiary">
                                                    {{articles.user?.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                                </n-button>
                                                <!--                                <n-button strong secondary type="primary">-->
                                                <!--                                    Primary-->
                                                <!--                                </n-button>-->
                                                <n-button size="small" @click="toChatViewAction(articles?.user?.id)"
                                                          v-if="userStore.getUser.id != articles?.user?.id" strong
                                                          secondary
                                                          type="info">
                                                    私信
                                                </n-button>
                                            </n-flex>
                                        </div>
                                    </n-space>

                                </div>
                            </template>
                            <n-space>
                                <n-tag type="info">
                                    {{articles.user?.fensUserId?.length}}
                                    <template #avatar>
                                        粉丝
                                    </template>
                                </n-tag>
                                <n-tag type="success">
                                    {{articles.user?.attentionUserId?.length}}
                                    <template #avatar>
                                        关注
                                    </template>
                                </n-tag>
                            </n-space>
                            <br/>
                            <p v-show="articles.user?.userIntro" style="word-break: break-all">
                                <n-ellipsis :line-clamp="1">
                                    {{articles.user?.userIntro}}
                                </n-ellipsis>
                            </p>
                        </n-thing>
                    </div>
                </div>
            </n-list-item>
        </n-list>
        <div class="reader" id="blog_reader">
            <n-space vertical>
                <viewer :images="preViewImages">
                    <n-list style="padding:0px  20px;background-color: var(--blog_foreground_color);">
                        <n-list-item>
                            <n-space vertical>
                                <div style="font-size: 25px;font-weight: bold;">
                                    {{articles.articleTitle}}
                                </div>
                                <div>
                                    <n-space>
                                        <div style="display: flex;justify-content: center;align-items: center;">
                                            <n-icon size="20">
                                                <TimeOutline/>
                                            </n-icon>
                                            <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{articles.updateTime}}
                                                        </span>
                                        </div>
                                        <div style="display: flex;justify-content: center;align-items: center;">
                                            <n-icon size="20">
                                                <EyeRegular/>
                                            </n-icon>
                                            <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{articles.browseCount}}
                                                        </span>
                                        </div>
                                        <div>
                                            <div style="display: flex;justify-content: center;align-items: center;">
                                                <n-icon size="20">
                                                    <BookmarkOutline/>
                                                </n-icon>
                                                <span v-for:="ele in articleLableClasss"
                                                      style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{ele.articleLables?.artName}}
                                                    </span>
                                            </div>
                                        </div>
                                    </n-space>
                                </div>
                            </n-space>
                            <!--                            <n-divider />-->
                        </n-list-item>
                    </n-list>
                    <div id="markdown-body" class="markdown-body" style="padding: 20px;" data-theme="light"
                         v-html="html"/>
                </viewer>
                <n-list bordered v-csdnScrollFooter>
                    <n-list-item>
                        <n-flex justify="space-around">
                            <div @click="commentShowFun" class="blog_cursor"
                                 style="display: flex;justify-content: center;align-items: center;">
                                <n-icon size="20">
                                    <ChatbubbleEllipsesSharp/>
                                </n-icon>
                                <span style="line-height: 12px;text-align: center;">
                                        {{articles.commentCount}}
                                </span>
                            </div>
                            <div @click="addLike" class="blog_cursor"
                                 style="display: flex;justify-content: center;align-items: center;">
                                <n-icon :color="articles.likeUserId?.includes(userStore?.getUser?.id)?'#DE576DFF':''"
                                        size="20">
                                    <Heart/>
                                </n-icon>
                                <span style="line-height: 12px;text-align: center;">
                                         {{articles.likeUserId?.length}}
                                </span>
                            </div>
                            <div @click="favoriteShowFun" class="blog_cursor"
                                 style="display: flex;justify-content: center;align-items: center;">
                                <n-icon :color="articles.favoriteUserId?.includes(userStore?.getUser?.id)?'#FCB040FF':''"
                                        size="20">
                                    <StarSharp/>
                                </n-icon>
                                <span style="line-height: 12px;text-align: center;">
                                         {{articles.favoriteUserId?.length}}
                                </span>
                            </div>
                        </n-flex>
                    </n-list-item>
                </n-list>
            </n-space>
        </div>
    </div>
    <div id="pc">
        <div class="reader" id="blog_reader">
            <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                <n-gi :span="6">
                    <n-space vertical>
                        <viewer :images="preViewImages">
                            <n-list style="padding:0px  20px;background-color: var(--blog_foreground_color);">
                                <n-list-item>
                                    <n-space vertical>
                                        <div style="font-size: 25px;font-weight: bold;">
                                            {{articles.articleTitle}}
                                        </div>
                                        <div>
                                            <n-space>
                                                <div style="display: flex;justify-content: center;align-items: center;">
                                                    <n-icon size="20">
                                                        <TimeOutline/>
                                                    </n-icon>
                                                    <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{articles.updateTime}}
                                                        </span>
                                                </div>
                                                <div style="display: flex;justify-content: center;align-items: center;">
                                                    <n-icon size="20">
                                                        <EyeRegular/>
                                                    </n-icon>
                                                    <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{articles.browseCount}}
                                                        </span>
                                                </div>
                                                <div>
                                                    <div style="display: flex;justify-content: center;align-items: center;">
                                                        <n-icon size="20">
                                                            <BookmarkOutline/>
                                                        </n-icon>
                                                        <span v-for:="ele in articleLableClasss"
                                                              style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                                {{ele.articleLables?.artName}}
                                                    </span>
                                                    </div>
                                                </div>
                                            </n-space>
                                        </div>
                                    </n-space>
                                    <!--                            <n-divider />-->
                                </n-list-item>
                            </n-list>
                            <div id="markdown-body" class="markdown-body" style="padding: 20px;"
                                 data-theme="light"
                                 v-html="html"/>
                        </viewer>
                        <n-list bordered v-csdnScrollFooter>
                            <n-list-item>
                                <n-flex justify="space-around">
                                    <div @click="commentShowFun" class="blog_cursor"
                                         style="display: flex;justify-content: center;align-items: center;">
                                        <n-icon size="20">
                                            <ChatbubbleEllipsesSharp/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                        {{articles.commentCount}}
                                </span>
                                    </div>
                                    <div @click="addLike" class="blog_cursor"
                                         style="display: flex;justify-content: center;align-items: center;">
                                        <n-icon :color="articles.likeUserId?.includes(userStore?.getUser?.id)?'#DE576DFF':''"
                                                size="20">
                                            <Heart/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                         {{articles.likeUserId?.length}}
                                </span>
                                    </div>
                                    <div @click="favoriteShowFun" class="blog_cursor"
                                         style="display: flex;justify-content: center;align-items: center;">
                                        <n-icon :color="articles.favoriteUserId?.includes(userStore?.getUser?.id)?'#FCB040FF':''"
                                                size="20">
                                            <StarSharp/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                         {{articles.favoriteUserId?.length}}
                                </span>
                                    </div>
                                </n-flex>
                            </n-list-item>
                        </n-list>
                    </n-space>
                </n-gi>
                <n-gi :span="2">
                    <div v-csdnScroll>
                        <n-space vertical>
                            <n-list bordered>
                                <n-list-item>
                                    <n-thing>
                                        <template #header>
                                            <n-space vertical>
                                                <div style="display: flex;justify-content: center">
                                                    <n-avatar
                                                            round
                                                            :size="100"
                                                            @click="toHomeAction(articles?.user?.id)"
                                                            :src="articles.user?.userImage"
                                                    />
                                                </div>
                                                <div style="display: flex;justify-content: center">
                                                    <n-gradient-text :size="24" type="success">
                                                        {{articles.user?.userName}}
                                                    </n-gradient-text>
                                                </div>
                                            </n-space>
                                        </template>
                                        <template #footer>
                                            <n-flex justify="space-between" size="large">

                                                <n-button
                                                        @click="addAttentionAction(articles?.user?.id)"
                                                        v-if="userStore.getUser.id != articles?.user?.id"
                                                        strong secondary type="tertiary">
                                                    {{articles.user?.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                                </n-button>
                                                <!--                                <n-button strong secondary type="primary">-->
                                                <!--                                    Primary-->
                                                <!--                                </n-button>-->
                                                <n-button @click="toChatViewAction(articles?.user?.id)"
                                                          v-if="userStore.getUser.id != articles?.user?.id"
                                                          strong
                                                          secondary
                                                          type="info">
                                                    私信
                                                </n-button>
                                            </n-flex>
                                        </template>
                                        <template #description>
                                            <n-row>
                                                <n-col :span="12">
                                                    <n-statistic label="粉丝">
                                                        {{articles.user?.fensUserId?.length}}
                                                    </n-statistic>
                                                </n-col>
                                                <n-col :span="12">
                                                    <n-statistic label="关注">
                                                        {{articles.user?.attentionUserId?.length}}
                                                    </n-statistic>
                                                </n-col>
                                            </n-row>
                                        </template>
                                        <p style="text-indent: 2em;">
                                            {{articles.user?.userIntro}}
                                        </p>
                                    </n-thing>
                                </n-list-item>
                            </n-list>

                            <n-card
                                    size="small"
                                    :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                                <template #header>
                                    <div style="font-size: 15px;font-weight: bold;">
                                        目录
                                    </div>
                                </template>
                                <template #header-extra>
                                    <n-icon style="cursor: pointer;" @click="tocShow" size="20">
                                        <Menu/>
                                    </n-icon>
                                </template>
                                <n-collapse-transition>
                                    <n-scrollbar style="max-height: 320px;">
                                        <div id="toc23" style="padding: 0px 20px 0px 0px;"></div>
                                    </n-scrollbar>
                                </n-collapse-transition>
                            </n-card>
                        </n-space>
                    </div>
                </n-gi>
            </n-grid>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    .reader {
        width: 100%;
    }

    #favorite {
        ::v-deep(.n-card__footer) {
            padding: 12px;
        }
    }

    ::v-deep(.n-card.n-card--content-segmented) {
        > .n-card__content {
            padding-top: 0;
        }
    }

    ::v-deep(.n-input__suffix) {
        .n-button {
            align-self: flex-end !important;
            margin-bottom: 12px;
        }
    }

    ::v-deep(.n-card) {
        > .n-card__content {
            padding: 0px !important;
        }
    }

    ::v-deep(.n-drawer-footer) {
        display: block !important;
    }

    ::v-deep(.n-statistic) {
        display: flex;
        flex-direction: column;;
        justify-content: center;
        align-items: center;
    }

    ::v-deep(.n-thing-header) {
        justify-content: center !important;
    }

    ::v-deep(blockquote) {
        color: var(--blog_font_color_3);
    }


    ::v-deep(code) {
        color: var(--markdown_color-fg-muted);
    }

    ::v-deep(pre) {
        code {
            color: inherit;

            &.hljs {
                &::-webkit-scrollbar {
                    width: 3px !important;
                    height: 8px;
                }
            }
        }

        li {
            list-style: decimal !important;
        }
    }

    ::v-deep(.markdown-body) {
        img:hover {
            cursor: pointer !important;
        }
    }

    ::v-deep(a.toc-link::before) {
        height: 0px !important;
    }

    ::v-deep(.is-active-link) {
        display: block;
        color: var(--markdown_color-accent-fg);
    }

    ::v-deep(.toc-list ) {
        padding: 0px;
    }

    ::v-deep(.toc-link) {
        display: block;
        padding: 3px 0px;
    }

    #toc23 {
        &::-webkit-scrollbar {
            width: 3px !important;
            height: 8px;
        }

        ::v-deep(li) {
            a {
                &:hover {
                    color: var(--markdown_color-accent-fg);
                }
            }

            list-style: none;
            padding-left: 20px;
        }
    }

</style>
