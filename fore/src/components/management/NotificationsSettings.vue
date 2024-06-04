<script setup lang="ts">
    import {ref, watch} from "vue";
    import {Edit} from "@vicons/fa"
    import {defaultBlogUserNotificationSettings} from "@/d/defautls";
    import {useUserStore} from "@/config/store/user";
    import {notisetByIdToUpdateRequest} from "@/config/request";

    const formDisable = ref<boolean>(true)

    const model = ref<BlogUserNotificationSettings>({
        ...defaultBlogUserNotificationSettings
    })

    const userStore = useUserStore()
    model.value = userStore.getnotiSet

    function UpdateAction() {
        const m = {...model.value}
        m.collectActive = Object.is(model.value.collectActive, true) ? 1 : 0
        m.likeActive = Object.is(model.value.likeActive, true) ? 1 : 0
        m.followActive = Object.is(model.value.followActive, true) ? 1 : 0
        m.privateLetterActive = Object.is(model.value.privateLetterActive, true) ? 1 : 0
        m.commentActive = Object.is(model.value.commentActive, true) ? 1 : 0
        notisetByIdToUpdateRequest(m, userStore.getUser.id).then(value => {
            if (value) {
                userStore.$patch({
                    notiSet: {
                        ...model.value
                    }
                })
            }
        })
    }
</script>

<template>
    <div class="notificationsSettings">
        <n-card :bordered="false" title="账号管理" style="margin-bottom: 16px">
            <template #header-extra>
                <n-popover
                        placement="right"
                        trigger="hover"
                >
                    <template #trigger>
                        <n-icon style="cursor: pointer;" @click="formDisable = !formDisable" size="20">
                            <Edit/>
                        </n-icon>
                    </template>
                    <span>编辑</span>
                </n-popover>
            </template>
            <n-space vertical>
                <n-form
                        :model="model"
                        label-placement="left"
                        :disabled="formDisable"
                >
                    <n-form-item path="privateLetter">
                        <n-card size="small" title="私信">
                            <n-switch v-model:value="model.privateLetterActive"/>
                            <template #header-extra>
                                关闭私信消息通知时，不会主动提示接到的消息通知
                            </template>
                        </n-card>
                    </n-form-item>
                    <n-form-item path="comment">
                        <n-card size="small" title="评论">
                            <n-switch v-model:value="model.commentActive"/>
                            <template #header-extra>
                                关闭评论消息通知时，不会主动提示接到的消息通知
                            </template>
                        </n-card>

                    </n-form-item>

                    <n-form-item path="follow">
                        <n-card size="small" title="关注">
                            <n-switch v-model:value="model.followActive"/>
                            <template #header-extra>
                                关闭关注消息通知时，不会主动提示接到的消息通知
                            </template>
                        </n-card>
                    </n-form-item>
                    <n-form-item path="collect">
                        <n-card size="small" title="收藏">
                            <n-switch v-model:value="model.collectActive"/>
                            <template #header-extra>
                                关闭收藏消息通知时，不会主动提示接到的消息通知
                            </template>
                        </n-card>
                    </n-form-item>
                    <n-form-item path="like">
                        <n-card size="small" title="点赞">
                            <n-switch v-model:value="model.likeActive"/>
                            <template #header-extra>
                                关闭点赞消息通知时，不会主动提示接到的消息通知
                            </template>
                        </n-card>
                    </n-form-item>
                    <n-flex justify="space-between">
                        <div></div>
                        <n-button @click="UpdateAction" :disabled="formDisable" type="primary">
                            修改
                        </n-button>
                    </n-flex>
                </n-form>
            </n-space>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .notificationsSettings {
        width: 100%;
    }

</style>
