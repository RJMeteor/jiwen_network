<script setup lang="ts">
    import {
        EllipsisHorizontalCircleOutline,
    } from "@vicons/ionicons5"
    import {useRoute, useRouter} from "vue-router";
    import {useUserStore} from "@/config/store/user";
    import {favoritesComponentInitField, RowData} from "@/config/data/personalFavorites";
    import {ref, onMounted} from "vue";
    import {deleteFavoriteArticlesOtherByIdRequest, getFavoritesByPersonIdRequest} from "@/config/request";
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

    executeCallback.value = reloadCollect

    const collectField = ref<{
        collects: BlogFavorite[]
        pageInfo: PageInfoType<BlogFavorite>,
        loading: boolean,
        emptyField: boolean
    }>({
        collects: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })
    executeCallback.value = reloadCollect


    onMounted(function () {
        getCollects();
    })

    function reloadCollect(ids: number[]) {
        deleteFavoriteArticlesOtherByIdRequest(ids).then(res => {
            if (res) {
                collectField.value = {
                    collects: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: true,
                    emptyField: false
                }
                getCollects()
            }
        })
    }


    function getCollects() {
        collectField.value.loading = false
        const userId = userStore.getUser.id
        getFavoritesByPersonIdRequest(userId, collectField.value.pageInfo.pageNum, collectField.value.pageInfo.pageSize).then(res => {
                collectField.value.collects.push(...res.list as BlogFavorite[])
                collectField.value.pageInfo = {...res} as any
                collectField.value.emptyField = collectField.value.collects.length > 0 ? false : true
                loadingAction(collectField.value)
        })
    }

    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize ) >= total) {
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
    <div class="collect">
        <n-card :bordered="false" style="margin-bottom: 16px">
            <template #header>
                <div style="visibility: hidden;">收藏</div>
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

                <n-list clickable hoverable style="margin-top: 12px;">
                    <n-list-item v-for="ele in collectField.collects">
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
                                            <div style="height:100%;display: flex;justify-content: flex-end;align-items: center;">
                                                {{ele?.updateTime}}
                                            </div>
                                        </n-gi>
                                    </n-grid>
                                    <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;"
                                            layout-shift-disabled>
                                        <n-gi :span="8" style="font-size: 13px;">
                                            <n-ellipsis :tooltip="false" style="max-width: 100%">
                                                收藏了你的{{ele?.article?.articleLimiter?"话题":"文章"}}{{ele?.article?.articleTitle}}
                                            </n-ellipsis>
                                        </n-gi>
                                    </n-grid>
                                </n-gi>
                            </n-grid>
                        </n-flex>
                    </n-list-item>
                    <n-card v-show="collectField.emptyField || collectField.loading">
                        <n-empty v-if="collectField.emptyField" style="margin-top: 20px;" description="空空如也..."/>
                        <Loading v-show="collectField.loading" :callback-action="getCollects"/>
                    </n-card>
                </n-list>
            </n-checkbox-group>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .collect {
        width: 100%;
    }

    .n-divider:not(.n-divider--vertical) {
        margin: 0px;
    }
</style>
