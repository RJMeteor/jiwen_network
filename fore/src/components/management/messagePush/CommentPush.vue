<script setup lang="ts">

    import {
        Search,
        Create,
        EllipsisHorizontalCircleOutline,
        ChatbubbleEllipsesSharp,
        ChevronForward,
        Heart
    } from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import Loading from "@/d/Loading.vue"
    import {useRoute, useRouter} from "vue-router";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {useUserStore} from "@/config/store/user";
    import {
        addCommentRequst, deleteCommentRequst,
        deleteCommentsRequest,
        getCommentByOtherPersonIdAndUserIdRequest,
        getCommentByOtherPersonIdRequest, getCommentNofitRequest, updataCommentAvtiveByPersonIdAndUserIdRequest
    } from "@/config/request";
    import {onMounted, ref} from "vue";
    import {defaultBlogComment} from "@/d/defautls";

    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()
    executeCallback.value = deleteComments

    const router = useRouter()
    const route = useRoute()
    const userStore = useUserStore()

    const commentField = ref<{
        comments: BlogComment[]
        pageInfo: PageInfoType<BlogComment>,
        loading: boolean,
        emptyField: boolean
    }>({
        comments: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })

    onMounted(function () {
        loadCommentList()
    })

    function loadCommentList() {
        commentField.value.loading = false
        const {pageNum, pageSize} = {...commentField.value.pageInfo}
        getCommentByOtherPersonIdAndUserIdRequest(userStore.getUser.id, +route.params.userId, pageNum, pageSize).then(res => {
                if (res.list.length) {
                    commentField.value.comments = [...res.list]
                    commentField.value.pageInfo = {...res} as any
                    commentField.value.emptyField = commentField.value.comments.length > 0 ? false : true
                    loadingAction(commentField.value)
                    getCommentAll(res.list[0])
                }
            }
        )
    }

    function deleteComments(ids: number[]) {
        deleteCommentsRequest(ids).then(res => {
            if (res) {
                commentField.value = {
                    comments: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: true,
                    emptyField: false
                }
                loadCommentList()
            }
        })
    }

    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize) >= total) {
            pageInfo.pageInfo = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            }
            pageInfo.loading = false
        } else {
            pageInfo.loading = true
            pageInfo.pageInfo.pageNum++
        }
    }


    const articleTitle = ref<string>("")

    const commentArticleField = ref<{
        comments: BlogComment[]
        pageInfo: PageInfoType<BlogComment>,
        loading: boolean,
        emptyField: boolean
    }>({
        comments: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })
    let localComment: any = null

    function getLoadArticleComment() {
        const {pageNum, pageSize} = {...commentArticleField.value.pageInfo}
        if (localComment) {
            articleTitle.value = localComment?.articleTitle
            getCommentNofitRequest(userStore.getUser.id, +route.params.userId, +(localComment?.articleContent?.articleId as number), pageNum, pageSize).then(res => {
                commentArticleField.value.comments.push(...res.list)
                commentArticleField.value.pageInfo = {...res} as any
                commentArticleField.value.emptyField = commentArticleField.value.comments.length > 0 ? false : true
                loadingAction(commentArticleField.value)
            })
        }
    }

    function getCommentAll(article: BlogComment) {
        localComment = article.article
        articleTitle.value = article?.article?.articleTitle as string
        // commentField.value.loading = false
        commentArticleField.value = {
            comments: [],
            pageInfo: {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            },
            loading: true,
            emptyField: false
        }

        const {pageNum, pageSize} = commentArticleField.value.pageInfo

        updataCommentAvtiveByPersonIdAndUserIdRequest(userStore.getUser.id, +route.params.userId).then(res => {
            if (res) {
                article.stated = 1
            }
        })
        console.log(article)
        getCommentNofitRequest(userStore.getUser.id, +route.params.userId, +(article?.article?.articleContent?.articleId as number), pageNum, pageSize).then(res => {
            commentArticleField.value.comments.push(...res.list)
            commentArticleField.value.pageInfo = {...res} as any
            commentArticleField.value.emptyField = commentArticleField.value.comments.length > 0 ? false : true
            loadingAction(commentArticleField.value)
        })
    }

    const recoverField = ref<any>()
    const commentContentInputFields = ref<string>("")
    const defaultComment = {...defaultBlogComment}

    /**
     * 回复评论
     * @param commentContent
     */
    function acceptUserAction(comment: BlogComment) {
        console.log(comment)
        recoverField.value = comment
    }

    /*
   * 评论输入框事情焦点时回调的方法
   * */
    function commentInputBlurAction() {
        commentContentInputFields.value = ""
        recoverField.value = null
    }

    function addCommentAction() {
        if (recoverField.value) {
            defaultComment.userId = userStore.getUser.id
            defaultComment.personId = recoverField.value.user.id
            defaultComment.commentKeyId = recoverField.value.commentKeyId
            defaultComment.articleId = recoverField.value.articleId;
            (defaultComment.commentContent as any) = {
                commentContent: commentContentInputFields.value
            }
            addCommentRequst(defaultComment).then(res => {
                if (res) {
                    commentArticleField.value.comments.push(res)
                }
            })
        } else {
            window.$message.error("请选择要回复的消息！")
        }
    }

    function deleteCommentAction(commentContent: BlogCommentContent, index: number) {
        deleteCommentRequst(commentContent?.commentId as number).then(res => {
            if (res) {
                commentArticleField.value.comments.splice(index, 1)
            }
        })
    }
</script>

<template>
    <div class="comment">
        <n-grid :x-gap="12" :y-gap="12" :cols="5" layout-shift-disabled>
            <n-gi :span="2">
                <n-list bordered>
                    <template #header>
                        <n-flex justify="space-between">
                            <div style="display: flex;align-items: center;">
                                <n-dropdown
                                        trigger="hover"
                                        :options="options"
                                        :show-arrow="true"
                                        @select="handleSelect"
                                >
                                    <n-icon size="25">
                                        <EllipsisHorizontalCircleOutline/>
                                    </n-icon>
                                </n-dropdown>
                            </div>
                        </n-flex>
                    </template>
                </n-list>
                <!--        <n-empty style="margin-top: 20px;" description="空空如也..."/>-->
                <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                    <n-scrollbar style="max-height: 300px">
                        <div>
                            <n-list clickable hoverable style="margin-top: 12px;">
                                <n-list-item v-for="ele in commentField.comments">
                                    <template v-if="checkBoxShow" #prefix>
                                        <n-checkbox v-show="checkBoxShow" :value="ele?.commentContent?.commentId"/>
                                        {{pushKey(ele?.commentContent?.commentId)}}
                                    </template>
                                    <n-flex @click="getCommentAll(ele)">
                                        <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                            <n-gi :span="8">
                                                <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                                    <n-gi :span="4">
                                                        <div style="font-size: 16px;font-weight: bold;">
                                                            <n-ellipsis :tooltip="true" style="max-width: 100%">
                                                                {{ele?.article?.articleTitle}}
                                                            </n-ellipsis>
                                                        </div>
                                                    </n-gi>
                                                    <n-gi :span="4">
                                                        <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                                            <n-ellipsis :tooltip="true" style="max-width: 100%">
                                                                {{ele?.updateTime}}
                                                            </n-ellipsis>
                                                        </div>
                                                    </n-gi>
                                                </n-grid>
                                                <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;"
                                                        layout-shift-disabled>
                                                    <n-gi :span="4" style="font-size: 13px;">
                                                        <n-ellipsis :tooltip="true" style="max-width: 100%">
                                                            {{ele?.commentContent?.commentContent}}
                                                        </n-ellipsis>
                                                    </n-gi>
                                                    <n-gi :span="4">
                                                        <div style="height:100%;display: flex;justify-content: flex-end;align-items: center;">
                                                            <n-gradient-text v-if="!ele?.stated" type="error">
                                                                [未读]
                                                            </n-gradient-text>
                                                        </div>
                                                    </n-gi>
                                                </n-grid>
                                            </n-gi>
                                        </n-grid>
                                    </n-flex>
                                </n-list-item>
                                <n-card v-show="commentField.emptyField || commentField.loading">
                                    <n-empty v-if="commentField.emptyField" style="margin-top: 20px;"
                                             description="空空如也..."/>
                                    <Loading v-show="commentField.loading" :callback-action="loadCommentList"/>
                                </n-card>
                            </n-list>
                        </div>
                    </n-scrollbar>
                </n-checkbox-group>
            </n-gi>
            <n-gi :span="3">
                <n-layout style="height: 660px">
                    <n-layout-header style="height: 50px;">
                        <div style="padding: 12px">
                            <n-page-header @back="handleBack">
                                <template #title>
                                    {{articleTitle}}
                                </template>
                            </n-page-header>
                        </div>
                    </n-layout-header>
                    <n-layout has-sider position="absolute" style="top: 58px; bottom: 180px">
                        <n-layout-content
                                content-style="padding: 12px 24px 24px;"
                                :native-scrollbar="false"
                        >
                            <n-card v-show="commentArticleField.emptyField || commentArticleField.pageInfo.loading">
                                <n-empty v-if="commentArticleField.emptyField" style="margin-top: 20px;"
                                         description="空空如也..."/>
                                <Loading v-show="commentArticleField.pageInfo.loading"
                                         :callback-action="getLoadArticleComment"/>
                            </n-card>
                            <div style="margin-top: 12px;" v-for="(ele,index) in commentArticleField.comments"
                                 :key="index">
                                <div v-if="ele.user.id !=userStore.getUser.id"
                                     style="display: flex;align-items: center;justify-content:flex-start;">
                                    <n-list
                                            style="background-color:var(--blog_background_color);max-width: calc(100% - 112px);width: fit-content;padding:0px 12px;margin-left: 12px;">
                                        <n-list-item style="padding-left: 20px">
                                            <template #suffix>
                                                <n-button size="tiny"
                                                          @click="acceptUserAction(ele)"
                                                          quaternary type="info">回复
                                                </n-button>
                                            </template>
                                            <div style="width: 100%;word-break: break-all;display: flex;align-items: center;justify-content:flex-start;height: 100%;">
                                                {{ele?.commentContent?.commentContent}}
                                            </div>
                                        </n-list-item>
                                    </n-list>
                                </div>
                                <div v-else style="display: flex;align-items: center;justify-content: flex-end;">
                                    <n-list
                                            style="background-color:var(--blog_background_color);max-width: calc(100% - 112px);
                                    width: fit-content;padding:0px 12px;margin-right: 8px;">
                                        <n-list-item style="padding-left: 20px">
                                            <template #suffix>
                                                <n-button size="tiny" quaternary
                                                          @click="deleteCommentAction(ele?.commentContent,index)"
                                                          type="error">删除
                                                </n-button>
                                            </template>
                                            <div
                                                    style="width: 100%;word-break: break-all;display: flex;align-items: center;justify-content:flex-end;height: 100%;">
                                                {{ele?.commentContent?.commentContent}}
                                            </div>
                                        </n-list-item>
                                    </n-list>
                                </div>
                            </div>
                        </n-layout-content>
                    </n-layout>
                    <n-layout-footer
                            bordered
                            position="absolute"
                            style="height: 180px;padding-bottom: 12px;background-color: var(--blog_foreground_color)"
                    >
                        <n-space vertical>
                            <n-input
                                    @blur="commentInputBlurAction"
                                    v-model:value="commentContentInputFields"
                                    :placeholder="recoverField ?'回复:'+recoverField?.commentContent?.commentContent:' 输入回复的内容...'"
                                    style="background-color: inherit;"
                                    type="textarea"
                                    :autosize="{
                                minRows: 5,
                                maxRows: 5
                              }"
                                    round clearable>
                                <template #suffix>
                                    <n-button @click="addCommentAction" strong secondary type="success">
                                        发送
                                    </n-button>
                                </template>
                            </n-input>
                            <!--                            <n-flex style="margin: 0 12px;" justify="space-between">-->
                            <!--                                <div style="display: flex;align-items: center;">-->
                            <!--                                    <n-icon style="cursor: pointer;" size="20">-->
                            <!--                                        <ImageSharp/>-->
                            <!--                                    </n-icon>-->
                            <!--                                </div>-->
                            <!--                                <div>-->
                            <!--                                    <n-button @click="addCommentAction" strong secondary type="success">-->
                            <!--                                        发送-->
                            <!--                                    </n-button>-->
                            <!--                                </div>-->
                            <!--                            </n-flex>-->
                        </n-space>
                    </n-layout-footer>
                </n-layout>
            </n-gi>
        </n-grid>
    </div>
</template>

<style lang="scss" scoped>
    .comment {
        width: 100%;
    }

    ::v-deep(.n-scrollbar-container ) {
        background-color: var(--blog_foreground_color);
    }

    ::v-deep(.n-input__suffix) {
        .n-button {
            align-self: flex-end !important;
            margin-bottom: 12px;
        }
    }
</style>
