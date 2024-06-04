<script setup lang="ts">
    import {getColor} from "@/util/currency"
    import {ImageSharp} from "@vicons/ionicons5";
    import Loading from "@/d/Loading.vue"
    import {useRoute, useRouter} from "vue-router";
    import {ref, onMounted, nextTick} from "vue"
    import {
        chatRequest,
        getChatRequest,
        getCommentNofitRequest,
        getUserByIdRequest,
        updataChatAvtiveByPersonIdAndUserIdRequest
    } from "@/config/request";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {useUserStore} from "@/config/store/user";
    import {defaultBlogChattingRecords, defaultBlogChattingRecordsContent} from "@/d/defautls";
    import {WebsocketUtil} from "@/util/websocker";

    const router = useRouter()
    const route = useRoute()
    const accptorUserField = ref<BlogUser>()
    const chatInputValueField = ref<string>("")

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
        loading: false,
        emptyField: false
    })

    const scrollFlagActiveField = ref<number>(2222)

    function scrollLisAction(e: Event) {
        const target = e.target as any
        scrollFlagActiveField.value = Math.ceil(target.scrollTop)
    }

    function websocketChat(msg: BlogChattingRecords) {
        chatField.value.chats.push(msg)
    }

    const charBox = ref<Element[]>()

    function bottomChar(isLoaing=false) {
        nextTick(function () {
            if(!isLoaing){
                (charBox as any).value[(charBox.value as any)?.length - 1].scrollIntoView({
                    block: "end",
                    behavior: "smooth"
                });
            }
            loadingAction(chatField.value)
        })
    }

    onMounted(function () {
        WebsocketUtil.proccseCallback.set("privateLetter;chat", websocketChat)
        getUserByIdRequest(+route.params.userId).then(res => {
            accptorUserField.value = res
        })
        updataChatAvtiveByPersonIdAndUserIdRequest(userStore.getUser.id, +route.params.userId)
        getChatAll()
    })

    function handleBack() {
        router.back()
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

    let timer: any = null
    const isL = ref<boolean>(false)

    function getChatAll(isLoading=false) {
        // chatField.value.loading = false
        isL.value = true
        const {pageNum, pageSize} = {...chatField.value.pageInfo}
        clearInterval(timer)
        timer = setTimeout(function () {
            getChatRequest(userStore.getUser.id, +route.params.userId, pageNum, pageSize).then(res => {
                chatField.value.chats.unshift(...(res.list.sort((a, b) => a.id - b.id)))
                chatField.value.pageInfo = {...res} as any
                chatField.value.emptyField = chatField.value.chats.length > 0 ? false : true
                isL.value = false
                bottomChar(isLoading)
            })
        }, 1000)
    }

    //发送聊天内容
    function addChatAction() {
        if (chatInputValueField.value) {
            const chat: BlogChattingRecords = {...defaultBlogChattingRecords}
            chat.chattingRecordsContent = {...defaultBlogChattingRecordsContent}
            chat.acceptorUserId = +route.params.userId
            chat.senderUserId = userStore.getUser.id
            chat.chattingRecordsContent.chattingRecordsContent = chatInputValueField.value
            chatRequest(chat).then(res => {
                if (res) {
                    chatField.value.chats.push(res)
                    bottomChar()
                    // loadingAction(chatField.value)
                }
            })
        } else {
            window.$message.error("聊天发送内容不能为空！")
        }
    }


</script>

<template>
    <div class="chat">
        <n-layout style="height: 640px">
            <n-layout-header style="height: 50px;">
                <div style="padding: 12px">
                    <n-page-header @back="handleBack">
                        <template #title>
                            {{accptorUserField?.userName}}
                        </template>
                    </n-page-header>
                </div>
            </n-layout-header>
            <n-layout id="chatColor" has-sider position="absolute" style="top: 48px; bottom: 130px">
                <n-layout-content
                        @scroll="scrollLisAction"
                        id="chatBox"
                        content-style="padding: 12px 24px 24px;"
                        :native-scrollbar="false"
                >
                    <div  style="display: flex;justify-content: center;">
                        <n-button text type="info" v-show="chatField.loading" :loading="isL" @click="getChatAll(true)">
                            加载更多...
                        </n-button>
                    </div>

                    <div style="margin-top: 12px;" v-for="ele in chatField.chats">
                        <div ref="charBox" v-if="ele.senderUser.id !=userStore.getUser.id"
                             style="display: flex;align-items: center">
                            <n-avatar
                                    :src="ele?.senderUser?.userImage"
                            />
                            <n-list
                                    style="background-color:var(--blog_background_color);max-width: calc(100% - 112px);width: fit-content;padding:0px 12px;margin-left: 12px;">
                                <n-list-item>
                                    <div style="width: 100%;word-break: break-all;display: flex;align-items: center;height: 100%;">
                                        {{ele?.chattingRecordsContent?.chattingRecordsContent}}
                                    </div>
                                </n-list-item>
                            </n-list>
                        </div>
                        <div ref="charBox" v-else style="display: flex;align-items: center;justify-content: flex-end;">
                            <n-list
                                    style="background-color:var(--blog_background_color);max-width: calc(100% - 112px);
                                    width: fit-content;padding:0px 12px;margin-right: 8px;">
                                <n-list-item>
                                    <div style="width: 100%;word-break: break-all;display: flex;align-items: center;justify-content:flex-end;height: 100%;">
                                        {{ele?.chattingRecordsContent?.chattingRecordsContent}}
                                    </div>
                                </n-list-item>
                            </n-list>
                            <n-avatar
                                    :src="ele?.senderUser?.userImage"
                            />
                        </div>
                    </div>
                </n-layout-content>
            </n-layout>
            <n-layout-footer
                    bordered
                    position="absolute"
                    style="height: 130px;padding-bottom: 12px;background-color: var(--blog_foreground_color)"
            >
                <n-space vertical>
                    <n-input v-model:value="chatInputValueField" type="textarea" :autosize="{
                                minRows: 5,
                                maxRows: 5
                              }"
                             placeholder="输入聊天内容..."
                             round clearable>
                        <template #suffix>
                            <n-button @click="addChatAction" strong secondary type="success">
                                发送
                            </n-button>
                        </template>
                    </n-input>
                </n-space>
            </n-layout-footer>
        </n-layout>
    </div>
</template>

<style lang="scss" scoped>
    .chat {
        width: 100%;
    }

    #chatColor {
        ::v-deep(.n-scrollbar-container ) {
            background-color: var(--blog_foreground_color);
        }
    }


    ::v-deep(.n-input__suffix) {
        .n-button {
            align-self: flex-end !important;
            margin-bottom: 12px;
        }
    }

</style>
