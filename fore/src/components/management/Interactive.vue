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
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {deleteAttentionsRequest, getAttentionsRequest, getFensByPersonIdRequest} from "@/config/request";
    import {useUserStore} from "@/config/store/user";

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
    const tabValueField = ref<string>("follow")
    const followField = ref<{
        follow: BlogAttention[]
        pageInfo: PageInfoType<BlogAttention>,
        loading: boolean,
        emptyField: boolean
    }>({
        follow: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: false,
        emptyField: false
    })


    const fensField = ref<{
        fens: BlogAttention[]
        pageInfo: PageInfoType<BlogAttention>,
        loading: boolean,
        emptyField: boolean
    }>({
        fens: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: false,
        emptyField: false
    })

    onMounted(function () {
        getFollowAction()
    })


    function getFollowAction() {
        const {pageNum, pageSize} = followField.value.pageInfo
        getAttentionsRequest(userStore.getUser.id, pageNum, pageSize).then(res => {
            followField.value.follow.push(...res.list as BlogAttention[])
            followField.value.pageInfo = {...res} as any

            followField.value.emptyField = followField.value.follow.length > 0 ? false : true

            loadingAction(followField.value)
        })
    }


    function getFensAction() {
        const {pageNum, pageSize} = fensField.value.pageInfo
        getFensByPersonIdRequest(userStore.getUser.id, pageNum, pageSize).then(res => {
            fensField.value.fens.push(...res.list as BlogAttention[])
            fensField.value.pageInfo = {...res} as any

            fensField.value.emptyField = fensField.value.fens.length > 0 ? false : true

            loadingAction(fensField.value)
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

    function changeTabAction(value: string) {
        citiesRef.value = []
        switch (value) {
            case  "follow": {
                followField.value = {
                    follow: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: false,
                    emptyField: false
                }
                getFollowAction()
                executeCallback.value = function (ids: number[]) {
                    deleteAttentionsRequest(ids).then(res => {
                        if (res) {
                            followField.value = {
                                follow: [],
                                pageInfo: {
                                    total: 0,
                                    pageNum: 1,
                                    pageSize: 10,
                                },
                                loading: true,
                                emptyField: false
                            }
                            getFollowAction()
                        }
                    })
                }
                break
            }
            case "fens": {
                fensField.value = {
                    fens: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: false,
                    emptyField: false
                }
                getFensAction()
                executeCallback.value = function (ids: number[]) {
                    deleteAttentionsRequest(ids).then(res => {
                        if (res) {
                            fensField.value = {
                                fens: [],
                                pageInfo: {
                                    total: 0,
                                    pageNum: 1,
                                    pageSize: 10,
                                },
                                loading: true,
                                emptyField: false
                            }
                            getFensAction()
                        }
                    })
                }
                break
            }
        }
    }

    function toChatViewAction(userId: number) {
        router.push({
            name: "chat",
            params: {
                userId
            }
        })
    }
</script>

<template>
    <div class="interactive">
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
            <n-tabs type="line" @update:value="changeTabAction" v-model:value="tabValueField" animated>
                <n-tab-pane name="follow" tab="关注">
                    <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                        <div>
                            <n-list clickable hoverable style="margin-top: 12px;">
                                <n-list-item v-for="ele in followField.follow">
                                    <template v-if="checkBoxShow" #prefix>
                                        <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                                        {{pushKey(ele?.id)}}
                                    </template>
                                    <template #suffix>

                                        <div style="height: 100%;display: flex;align-items: center;">
                                            <n-button @click="toChatViewAction(ele?.attentionUser?.id)">
                                                私信
                                            </n-button>
                                        </div>
                                    </template>
                                    <n-flex>
                                        <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                            <n-gi :span="1">
                                                <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                                    <n-avatar
                                                            round
                                                            size="medium"
                                                            :src="ele?.attentionUser?.userImage"
                                                    />
                                                </div>
                                            </n-gi>
                                            <n-gi :span="7">
                                                <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                                    <n-gi :span="8">
                                                        <div style="font-size: 16px;font-weight: bold;">
                                                            {{ele?.attentionUser?.userName}}
                                                        </div>
                                                    </n-gi>
                                                </n-grid>
                                                <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;"
                                                        layout-shift-disabled>
                                                    <n-gi :span="8" style="font-size: 13px;">
                                                        <n-ellipsis :tooltip="false" style="max-width: 100%">
                                                            {{ele?.attentionUser?.userIntro?ele?.attentionUser?.userIntro:"无"}}
                                                        </n-ellipsis>
                                                    </n-gi>
                                                </n-grid>
                                            </n-gi>
                                        </n-grid>
                                    </n-flex>
                                </n-list-item>
                                <n-card v-show="followField.emptyField || followField.loading">
                                    <n-empty v-if="followField.emptyField" style="margin-top: 20px;"
                                             description="空空如也..."/>
                                    <Loading v-show="followField.loading" :callback-action="getFollowAction"/>
                                </n-card>
                            </n-list>
                        </div>
                    </n-checkbox-group>
                </n-tab-pane>
                <n-tab-pane name="fens" tab="粉丝">
                    <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                        <div>
                            <n-list clickable hoverable style="margin-top: 12px;">
                                <n-list-item v-for="ele in fensField.fens">
                                    <template v-if="checkBoxShow" #prefix>
                                        <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                                        {{pushKey(ele?.id)}}
                                    </template>
                                    <template #suffix>

                                        <div style="height: 100%;display: flex;align-items: center;">
                                            <n-button @click="toChatViewAction(ele?.user?.id)">
                                                私信
                                            </n-button>
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
                                                    <n-gi :span="8">
                                                        <div style="font-size: 16px;font-weight: bold;">
                                                            {{ele?.user?.userName}}
                                                        </div>
                                                    </n-gi>
                                                </n-grid>
                                                <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;"
                                                        layout-shift-disabled>
                                                    <n-gi :span="8" style="font-size: 13px;">
                                                        <n-ellipsis :tooltip="false" style="max-width: 100%">
                                                            {{ele?.user?.userIntro?ele?.user?.userIntro:"无"}}
                                                        </n-ellipsis>
                                                    </n-gi>
                                                </n-grid>
                                            </n-gi>
                                        </n-grid>
                                    </n-flex>
                                </n-list-item>
                                <n-card v-show="fensField.emptyField || fensField.loading">
                                    <n-empty v-if="fensField.emptyField" style="margin-top: 20px;"
                                             description="空空如也..."/>
                                    <Loading v-show="fensField.loading" :callback-action="getFensAction"/>
                                </n-card>
                            </n-list>
                        </div>
                    </n-checkbox-group>
                </n-tab-pane>
            </n-tabs>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .interactive {
        width: 100%;
    }

    .n-divider:not(.n-divider--vertical) {
        margin: 0px;
    }
</style>
