<script setup lang="ts">
    import {ref, onMounted} from "vue"
    import Loading from "@/d/Loading.vue"
    import {
        Search,
        Create,
        EllipsisHorizontalCircleOutline,
        ChatbubbleEllipsesSharp,
        ChevronForward,
        Heart
    } from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import {useRouter} from "vue-router";
    import {useUserStore} from "@/config/store/user";
    import {
        deleteCommentsRequest,
        deleteFavoriteArticlesOtherByIdRequest,
        getCommentByOtherPersonIdRequest
    } from "@/config/request";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";

    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()

    const router = useRouter()
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
    executeCallback.value = deleteComments

    onMounted(function () {
        loadCommentList()
    })

    function loadCommentList() {
        commentField.value.loading = false
        const {pageNum, pageSize} = {...commentField.value.pageInfo}
        getCommentByOtherPersonIdRequest(userStore.getUser.id, pageNum, pageSize).then(res => {
                    commentField.value.comments.push(...res.list)
                    commentField.value.pageInfo = {...res} as any
                    commentField.value.emptyField = commentField.value.comments.length > 0 ? false : true
                    loadingAction(commentField.value)
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

    function chat(userId:number) {
        router.push({
            name: `commentPush`,
            params:{
                userId
            }
        })
    }
</script>

<template>
    <div class="comment">
        <n-card :bordered="false" style="margin-bottom: 16px">
            <template  #header>
                <n-input style="max-width: 200px;visibility: hidden;" round clearable placeholder="搜索">
                    <template #prefix>
                        <n-icon :component="Search" :size="20"/>
                    </template>
                </n-input>
            </template>
            <template #header-extra>
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
            </template>
            <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                <div >
                    <n-list clickable hoverable style="margin-top: 12px;" >
                        <n-list-item v-for="ele in commentField.comments" @click="chat(ele?.user?.id)">
                            <template v-if="checkBoxShow" #prefix>
                                <n-checkbox v-show="checkBoxShow" :value="ele?.commentContent?.commentId"/>
                                {{pushKey(ele?.commentContent?.commentId)}}
                            </template>
                            <template #suffix>
                                <div style="height: 100%;display: flex;align-items: center;">
                                    <n-icon size="20">
                                        <ChevronForward/>
                                    </n-icon>
                                </div>
                            </template>
                            <n-flex>
                                <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                    <n-gi :span="1">
                                        <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                            <n-avatar
                                                    round
                                                    size="medium"
                                                    :src="ele?.user?.userImage"
                                            />
                                        </div>
                                    </n-gi>
                                    <n-gi :span="7">
                                        <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                            <n-gi :span="4">
                                                <div style="font-size: 16px;font-weight: bold;">
                                                    {{ele?.user?.userName}}
                                                </div>
                                            </n-gi>
                                            <n-gi :span="4">
                                                <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                                    {{ele?.updateTime}}
                                                </div>
                                            </n-gi>
                                        </n-grid>
                                        <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;"
                                                layout-shift-disabled>
                                            <n-gi :span="7" style="font-size: 13px;">
                                                <n-ellipsis :tooltip="false" style="max-width: 100%">
                                                    {{ele?.commentContent?.commentContent}}
                                                </n-ellipsis>
                                            </n-gi>
                                            <n-gi :span="1">
                                                <div style="height:100%;display: flex;justify-content: center;align-items: center;">
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
                            <n-empty v-if="commentField.emptyField" style="margin-top: 20px;" description="空空如也..."/>
                            <Loading v-show="commentField.loading" :callback-action="loadCommentList"/>
                        </n-card>
                    </n-list>
                </div>
            </n-checkbox-group>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .comment {
        width: 100%;
    }

    .n-divider:not(.n-divider--vertical) {
        margin: 0px;
    }
</style>
