<script setup lang="ts">
    import {onMounted, ref} from "vue"
    import {menuOptions} from '@/config/data/personalHomepage'
    import {useRoute, useRouter} from "vue-router";
    import {addAttentionRequest, deleteAttentionRequest, getUserByIdRequest} from "@/config/request";
    import {useUserStore} from "@/config/store/user";
    import {defaultUser} from "@/d/defautls";

    const route = useRoute()
    const router = useRouter()
    const userStore = useUserStore()
    const optionField = menuOptions(+route.params.userId);
    const userInfoField = ref<BlogUser>({...defaultUser})
    onMounted(function () {
        //请求用户数据
        getUserByIdRequest(+route.params.userId).then(res => {
            console.log(res)
            userInfoField.value = {
                ...res
            }
        })
    })
    function toChatViewAction() {
        console.log(11111)
        let routeUrl = router.resolve({
            name: "chat",
            params: {
                userId: +route.params.userId
            }
        });
        window.open(routeUrl.href, '_blank');
    }

    function addAttentionAction() {
        const localUserId = userStore.getUser.id
        if (userInfoField.value?.fensUserId && userInfoField.value?.fensUserId.includes(localUserId)) {
            deleteAttentionRequest(+route.params.userId, localUserId).then(res => {
                if (res) {
                    userInfoField.value?.fensUserId.splice(userInfoField.value?.fensUserId?.indexOf(localUserId), 1)
                }
            })
        } else {
            addAttentionRequest(+route.params.userId, localUserId).then(res => {
                if (res) {
                    const fensUserIds = userInfoField.value?.fensUserId
                    if (fensUserIds) {
                        fensUserIds.push(userStore.getUser.id);
                    } else {
                        (userInfoField.value as any).fensUserId.pash(userStore.getUser.id) as any
                    }
                }
            })
        }
    }
</script>

<template>
    <div class="personalHomepage">
        <div id="mobile">
            <n-list bordered>
                <n-list-item>
                    <div style="display: flex;">
                        <div>
                            <n-avatar
                                    round
                                    :size="100"
                                    :src="userInfoField.userImage"
                            />
                        </div>
                        <div style="margin-left: 20px;width: 100%">
                            <n-thing>
                                <template #description>
                                    <div style="display: flex;justify-content: space-between;flex: 1">
                                        <n-space justify="space-between" style="flex: 1">
                                            <div>
                                                <div style="display: flex;justify-content: flex-start;">
                                                    <n-gradient-text :size="24" type="success">
                                                        {{userInfoField.userName}}
                                                    </n-gradient-text>
                                                </div>
                                            </div>
                                            <div>
                                                <n-flex justify="space-between" size="large">
                                                    <n-button
                                                            size="small"
                                                            @click="addAttentionAction()"
                                                            v-if="userStore.getUser.id != userInfoField?.id"
                                                            strong secondary type="tertiary">
                                                        {{userInfoField?.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                                    </n-button>
                                                    <n-button v-if="userStore.getUser.id != userInfoField?.id"
                                                              @click="toChatViewAction()"
                                                              strong secondary
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
                                        {{userInfoField.fensUserId?.length ?userInfoField.fensUserId?.length:"" }}
                                        <template #avatar>
                                            粉丝
                                        </template>
                                    </n-tag>
                                    <n-tag type="success">
                                        {{userInfoField.attentionUserId?.length?userInfoField.attentionUserId?.length:"" }}
                                        <template #avatar>
                                            关注
                                        </template>
                                    </n-tag>
                                </n-space>
                                <p v-show="articles?.user?.userIntro" style="word-break: break-all">
                                    <n-ellipsis :line-clamp="3">
                                        {{userInfoField.userIntro}}
                                    </n-ellipsis>
                                </p>
                            </n-thing>
                        </div>
                    </div>
                </n-list-item>
            </n-list>
            <!--            <n-menu mode="horizontal" :inverted="true" :options="optionField"/>-->
            <RouterView/>
        </div>
        <div id="pc" style="display: flex;width: 100%;">
            <div id="pc" style="flex: 2;">
                <div v-csdnScroll>
                    <n-list bordered>
                        <n-list-item>
                            <n-thing>
                                <template #header>
                                    <n-space vertical>
                                        <div style="display: flex;justify-content: center">
                                            <n-avatar
                                                    round
                                                    :size="100"
                                                    :src="userInfoField.userImage"
                                            />
                                        </div>
                                        <div style="display: flex;justify-content: center">
                                            <n-gradient-text :size="24" type="success">
                                                {{userInfoField.userName}}
                                            </n-gradient-text>
                                        </div>
                                    </n-space>
                                </template>
                                <template #footer>
                                    <n-flex justify="space-between" size="large">
                                        <n-button
                                                size="small"
                                                @click="addAttentionAction()"
                                                v-if="userStore.getUser.id != userInfoField?.id"
                                                strong secondary type="tertiary">
                                            {{userInfoField?.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                        </n-button>
                                        <n-button v-if="userStore.getUser.id != userInfoField?.id"
                                                  @click="toChatViewAction()"
                                                  strong secondary
                                                  type="info">
                                            私信
                                        </n-button>
                                    </n-flex>
                                </template>
                                <template #description>
                                    <n-row>
                                        <n-col :span="12">
                                            <n-statistic label="粉丝">
                                                {{userInfoField.fensUserId?.length ?userInfoField.fensUserId?.length:""
                                                }}
                                            </n-statistic>
                                        </n-col>
                                        <n-col :span="12">
                                            <n-statistic label="关注">
                                                {{userInfoField.attentionUserId?.length
                                                ?userInfoField.attentionUserId?.length:"" }}
                                            </n-statistic>
                                        </n-col>
                                    </n-row>
                                </template>
                                <p style="text-indent: 2em;">
                                    {{userInfoField.userIntro}}
                                </p>
                            </n-thing>
                            <n-menu :options="optionField"/>
                        </n-list-item>
                    </n-list>
                </div>
            </div>
            <div id="pc" style="flex: 5;margin-left: 12px;">
                <RouterView/>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    .personalHomepage {
        width: 100%;
        box-sizing: border-box;
    }

    ::v-deep(.n-thing-header) {
        justify-content: center !important;
    }

    ::v-deep(.n-statistic) {
        display: flex;
        flex-direction: column;;
        justify-content: center;
        align-items: center;
    }
</style>

