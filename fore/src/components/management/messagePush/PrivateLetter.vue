<script setup lang="ts">
    import {ref,onMounted} from "vue"
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
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {deleteChatsRequest, getChatByOtherPersonIdRequest} from "@/config/request";

    const router = useRouter()
    function toChatViewAction(userId:number) {
        // console.log(userId)

        let routeUrl = router.resolve({
            name:"chat",
            params:{
                userId
            }
        });
        window.open(routeUrl.href, '_blank');
        // router.push({
        //     name:"chat",
        //     params:{
        //         userId
        //     }
        // })
    }

    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()

    const userStore = useUserStore()

    const chatField = ref<{
        chats: BlogChattingRecords[]
        pageInfo: PageInfoType<BlogChattingRecords>,
        loading: boolean,
        emptyField: boolean
    }>({
        chats: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })

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

    executeCallback.value = deleteChats

    onMounted(function () {
        loadChatList()
    })

    function loadChatList() {
        chatField.value.loading = false
        const {pageNum, pageSize} = {...chatField.value.pageInfo}
        getChatByOtherPersonIdRequest(userStore.getUser.id, pageNum, pageSize).then(res => {
                    chatField.value.chats.push(...res.list)
                    chatField.value.pageInfo = {...res} as any
                    chatField.value.emptyField = chatField.value.chats.length > 0 ? false : true
                    loadingAction(chatField.value)
            }
        )
    }

    function deleteChats(ids: number[]) {
        deleteChatsRequest(ids).then(res => {
            if (res) {
                chatField.value = {
                    chats: [],
                    pageInfo: {
                        total: 0,
                        pageNum: 1,
                        pageSize: 10,
                    },
                    loading: true,
                    emptyField: false
                }
                loadChatList()
            }
        })
    }



</script>

<template>
    <div class="privateLetter">
        <n-card :bordered="false" style="margin-bottom: 16px">
            <template #header>
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
        <n-checkbox-group  :value="citiesRef" @update:value="handleUpdateValue">
            <div>
                <n-list clickable hoverable style="margin-top: 12px;">
                    <n-list-item v-for="ele in chatField.chats" @click="toChatViewAction(ele?.senderUser?.id)">
                        <template v-if="checkBoxShow" #prefix>
                            <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                            {{pushKey(ele?.id)}}
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
                                                :src="ele?.senderUser?.userImage"
                                        />
                                    </div>
                                </n-gi>
                                <n-gi :span="7">
                                   <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>
                                    <n-gi :span="4">
                                        <div style="font-size: 16px;font-weight: bold;">
                                            {{ele?.senderUser?.userName}}
                                        </div>
                                    </n-gi>
                                       <n-gi :span="4">
                                           <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                               {{ele?.updateTime}}
                                           </div>
                                       </n-gi>
                                   </n-grid>
                                   <n-grid :x-gap="12" :y-gap="12" :cols="8" style="margin-top: 5px;" layout-shift-disabled>
                                    <n-gi :span="7" style="font-size: 13px;">
                                        <n-ellipsis :tooltip="false" style="max-width: 100%">
                                            {{ele?.chattingRecordsContent?.chattingRecordsContent}}
                                        </n-ellipsis>
                                    </n-gi>
                                       <n-gi :span="1">
                                           <div style="height:100%;display: flex;justify-content: center;align-items: center;">
                                               <n-gradient-text v-if="!ele?.readStatus" type="error">
                                                   [未读]
                                               </n-gradient-text>
                                           </div>
                                       </n-gi>
                                   </n-grid>
                                </n-gi>
                            </n-grid>
                        </n-flex>
                    </n-list-item>
                    <n-card v-show="chatField.emptyField || chatField.loading">
                        <n-empty v-if="chatField.emptyField" style="margin-top: 20px;" description="空空如也..."/>
                        <Loading v-show="chatField.loading" :callback-action="loadChatList"/>
                    </n-card>
                </n-list>
            </div>
        </n-checkbox-group>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .privateLetter {
        width: 100%;
    }
    .n-divider:not(.n-divider--vertical)
    {
        margin: 0px;
    }

</style>
