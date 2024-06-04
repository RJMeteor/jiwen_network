<script setup lang="ts">
    import {
        EllipsisHorizontalCircleOutline,
    } from "@vicons/ionicons5"
    import {useRoute, useRouter} from "vue-router";
    import {useUserStore} from "@/config/store/user";
    import {favoritesComponentInitField, RowData} from "@/config/data/personalFavorites";
    import {onMounted, ref} from "vue";
    import {
        deleteFavoriteArticlesOtherByIdRequest, deleteLikeOtherByIdRequest,
        getFavoritesByPersonIdRequest,
        getLikeOtherByPersonIdRequest
    } from "@/config/request";
    import Loading from "@/d/Loading.vue"
    const userStore = useUserStore()
    const route = useRoute()
    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()

    executeCallback.value = reloadLike

    const LikeField = ref<{
        likes: BlogLikeBrowse[]
        pageInfo: PageInfoType<BlogLikeBrowse>,
        loading: boolean,
        emptyField: boolean
    }>({
        likes: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })

    onMounted(function () {
        getLikes();
    })

    function reloadLike(ids: number[]) {
        deleteLikeOtherByIdRequest(ids).then(res => {
            if (res) {
                LikeField.value = {
                    likes: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: true,
                    emptyField: false
                }
                getLikes()
            }
        })
    }


    function getLikes() {
        LikeField.value.loading = false
        const userId = userStore.getUser.id
        getLikeOtherByPersonIdRequest(userId, LikeField.value.pageInfo.pageNum, LikeField.value.pageInfo.pageSize).then(res => {
                LikeField.value.likes.push(...res.list as any)
                LikeField.value.pageInfo = {...res} as any

                LikeField.value.emptyField = LikeField.value.likes.length > 0 ? false : true
                loadingAction(LikeField.value)
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
</script>

<template>
    <div class="like">
        <n-card :bordered="false" style="margin-bottom: 16px">
            <template #header>
                <div style="visibility: hidden;">点赞</div>
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
                <n-list clickable hoverable style="margin-top: 12px;" @click="chat">
                    <n-list-item v-for="ele in LikeField.likes">
                        <template v-if="checkBoxShow" #prefix>
                            <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                            {{pushKey(ele?.id)}}
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
                                        <n-gi :span="8" style="font-size: 13px;">
                                            <n-ellipsis :tooltip="false" style="max-width: 100%">
                                                点赞了
                                                你的{{ele?.article?.articleLimiter?"话题":"文章"}}
                                                {{ele?.article?.articleTitle}}

                                            </n-ellipsis>
                                        </n-gi>
                                    </n-grid>
                                </n-gi>
                            </n-grid>
                        </n-flex>
                    </n-list-item>
                    <n-card v-show="LikeField.emptyField || LikeField.loading">
                        <n-empty v-if="LikeField.emptyField" style="margin-top: 20px;" description="空空如也..."/>
                        <Loading v-show="LikeField.loading" :callback-action="getLikes"/>
                    </n-card>
                </n-list>
            </n-checkbox-group>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .like {
        width: 100%;
    }

    .n-divider:not(.n-divider--vertical) {
        margin: 0px;
    }

</style>
