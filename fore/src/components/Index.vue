<script setup lang="ts">
    import {ref, watch, onMounted, nextTick, computed} from 'vue'
    import {MenuSharp, Moon, PersonCircle, Search} from "@vicons/ionicons5"
    import {BuiltInGlobalTheme} from "naive-ui/lib/themes/interface"
    import {
        changeThemeState,
        themeState,
    } from "@/config/data/app"
    import {
        // showModal,
        menuOptions,
        options,
    } from "@/config/data/app"
    import {
        getLablesRequest,
        loginRequest,
        notisetByIdRequest,
        registCodeRequest,
        registRequest,
        resetCodeRequest,
        resetRequest
    } from "@/config/request";
    import {defaultUser} from "@/d/defautls";
    import {microqueueIn} from "@/util/currency";
    import {useUserStore} from "@/config/store/user"
    import {router} from "@/config/router";
    import {WebsocketUtil} from '@/util/websocker'

    const scrollRef = ref()


    onMounted(function () {
        // scrollRef.value.$el.nextElementSibling.style.cssText = `max-height:${window.innerHeight}px`;
        scrollRef.value.$el.nextElementSibling.children[0].id = "global_s"
    })

    const userStore = useUserStore()


    let timer: any = null
    const codeField = ref<string>("") //验证码
    const codeTimeField = ref<number>(60) //验证码倒计时
    const loginField = ref<BlogUser>({...defaultUser})

    const autoCompleteOptions = computed(() => {
        return ['@qq.com'].map((suffix) => {
            const prefix = loginField.value.userEmail.split('@')[0]
            return {
                label: prefix + suffix,
                value: prefix + suffix
            }
        })
    })

    function changeLoginStatus() {
        userStore.$patch({
            loginStatus: true
        })
    }

    if (userStore.getUser.id) {
        new WebsocketUtil()
        notisetByIdRequest(userStore.getUser.id).then(value => {
            console.log(value);
            value.collectActive = Object.is(value.collectActive, 1) ? true : false as any
            value.likeActive = Object.is(value.likeActive, 1) ? true : false as any
            value.followActive = Object.is(value.followActive, 1) ? true : false as any
            value.privateLetterActive = Object.is(value.privateLetterActive, 1) ? true : false as any
            value.commentActive = Object.is(value.commentActive, 1) ? true : false as any
            userStore.$patch({
                notiSet: {
                    ...value
                }
            })

        })
    }

    //登录
    function loginAction() {
        const {userName, userPass} = loginField.value
        if (!userName) {
            window.$message.error("用户名不能为空")
            return
        }
        if (!userPass) {
            window.$message.error("密码不能为空")
            return;
        }
        loginRequest(loginField.value).then(value => {
            if (value) {
                userStore.$patch({
                    loginStatus: false
                })
                resetInputAction()
                //获取用户通知设置权限
                userStore.$patch({
                    init: true,
                    token: (value as any).token,
                    user: {
                        ...value
                    }
                })
                router.push({
                    name: "index"
                })
                window.location.reload()
            }
        })
    }

    const registEmail = ref<string>()

    //注册
    function registAction() {
        const {userName, userPass, userEmail} = loginField.value
        if (!userName) {
            window.$message.error("用户名不能为空")
            return
        }
        if (!userPass) {
            window.$message.error("密码不能为空")
            return;
        }
        if (!Object.is(userEmail, registEmail.value)) {
            window.$message.error("邮箱错误")
            return;
        }
        if (!codeField.value) {
            window.$message.error("验证码不能为空")
            return;
        }
        registRequest(loginField.value, codeField.value)
    }

    //注册验证码
    function registCodeAction() {
        const {userEmail} = loginField.value
        if (!userEmail) {
            window.$message.error("邮箱不能为空")
            return
        }
        registEmail.value = loginField.value.userEmail
        registCodeRequest(loginField.value)
        microqueueIn(function () {
            timer = setInterval(function () {
                if (!codeTimeField.value--) {
                    clearInterval(timer)
                    codeTimeField.value = 60;
                }
            }, 1000)
        })
    }

    const resetEmail = ref<string>()

    //忘记密码，重置密码
    function resetAction() {
        const {userName, userPass, userEmail} = loginField.value
        if (!userPass) {
            window.$message.error("密码不能为空")
            return;
        }
        if (!Object.is(userEmail, resetEmail.value)) {
            window.$message.error("邮箱错误")
            return;
        }
        if (!codeField.value) {
            window.$message.error("验证码不能为空")
            return;
        }
        resetRequest(loginField.value, codeField.value)
    }

    //忘记密码，重置密码验证码
    function resetCodeAction() {
        const {userEmail} = loginField.value
        if (!userEmail) {
            window.$message.error("邮箱不能为空");
            return
        }
        resetEmail.value = loginField.value.userEmail
        resetCodeRequest(loginField.value)
        microqueueIn(function () {
            timer = setInterval(function () {
                if (!codeTimeField.value--) {
                    clearInterval(timer)
                    codeTimeField.value = 60;
                }
            }, 1000)
        })
    }


    function resetInputAction() {
        loginField.value = {...defaultUser}
        codeTimeField.value = 60;
        codeField.value = ""
        clearInterval(timer)
        return true;
    }
    const labelsOptionField = ref<any>([])
    onMounted(function () {
        labelsOptionField.value = []
        //得到文章用到的标签
        getLablesRequest().then(res => {
            if (res) {
                res.sort((a, b) => a.artName.localeCompare(b.artName));
                for (let re of res) {
                    labelsOptionField.value.push({
                        label: re.artName,
                        value: re.id
                    })
                }
            }
        })
    })

    function searchAction() {
        if (userStore.$state.routeName == "homePage") {
            userStore.$state.searchAction()
        } else {
            router.push({
                name: "homePage"
            })
        }
    }

    const mobileFied = ref<boolean>(false)
</script>

<template>
    <div style="width: 100vw;height: 100vh">
        <n-drawer id="mobile" v-model:show="mobileFied" placement="right">
            <n-drawer-content>
                <template #header>
                    <n-space justify="space-between">
                        <n-icon size="27" v-if="!userStore.getInit" @click="changeLoginStatus">
                            <PersonCircle/>
                        </n-icon>
                        <n-dropdown
                                v-else
                                placement="bottom-start"
                                trigger="click"
                                size="large"
                                :show-arrow="true"
                                :options="options"
                        >
                            <n-avatar
                                    round
                                    :size="25"
                                    :src="userStore.getUser.userImage"
                            />
                        </n-dropdown>
                        <n-icon @click="changeThemeState" size="25">
                            <Moon/>
                        </n-icon>
                    </n-space>
                </template>
                <n-menu
                        v-model:value="userStore.$state.routeName"
                        :options="menuOptions"
                        responsive
                />
                <n-menu
                        ref="menuInstRef"
                        v-model:value="userStore.$state.routeName"
                        :options="userStore.$state.mobileMenu"
                        :accordion="true"
                        :default-expanded-keys="['messagePush']"
                />
            </n-drawer-content>
        </n-drawer>
        <n-modal v-model:show="userStore.$state.loginStatus">
            <n-card
                    style="width: 400px"
                    :bordered="false"
                    size="huge"
                    role="dialog"
                    aria-modal="true"
            >
                <n-tabs
                        default-value="signin"
                        @before-leave="resetInputAction"
                        size="large"
                        justify-content="space-evenly">
                    <n-tab-pane name="signin" tab="登录">
                        <n-form>
                            <n-form-item-row label="用户名/邮箱">
                                <n-input v-model:value="loginField.userName"/>
                            </n-form-item-row>
                            <n-form-item-row label="密码">
                                <n-input v-model:value="loginField.userPass"/>
                            </n-form-item-row>
                        </n-form>
                        <n-button @click="loginAction" type="primary" block secondary strong>
                            登录
                        </n-button>
                    </n-tab-pane>
                    <n-tab-pane name="signup" tab="注册">
                        <n-form>
                            <n-form-item-row label="用户名">
                                <n-input v-model:value="loginField.userName"/>
                            </n-form-item-row>
                            <n-form-item-row label="密码">
                                <n-input v-model:value="loginField.userPass"/>
                            </n-form-item-row>
                            <n-form-item-row label="邮箱">
                                <n-input-group>
                                    <n-auto-complete
                                            v-model:value="loginField.userEmail"
                                            :options="autoCompleteOptions"
                                            placeholder="邮箱"
                                    />
                                    <n-button
                                            @click="registCodeAction"
                                            :disabled="codeTimeField==60?false:true"
                                            type="primary" ghost>
                                        {{codeTimeField==60?"发送验证码":codeTimeField+"秒"}}
                                    </n-button>
                                </n-input-group>
                            </n-form-item-row>
                            <n-form-item-row label="验证码">
                                <n-input v-model:value="codeField"/>
                            </n-form-item-row>
                        </n-form>
                        <n-button @click="registAction" type="primary" block secondary strong>
                            注册
                        </n-button>
                    </n-tab-pane>
                    <n-tab-pane name="signpa" tab="重置">
                        <n-form>
                            <n-form-item-row label="邮箱">
                                <n-input-group>
                                    <n-auto-complete
                                            v-model:value="loginField.userEmail"
                                            :options="autoCompleteOptions"
                                            placeholder="邮箱"
                                    />
                                    <n-button
                                            @click="resetCodeAction"
                                            :disabled="codeTimeField==60?false:true"
                                            type="primary" ghost>
                                        {{codeTimeField==60?"发送验证码":codeTimeField+"秒"}}
                                    </n-button>
                                </n-input-group>
                            </n-form-item-row>
                            <n-form-item-row label="验证码">
                                <n-input v-model:value="codeField"/>
                            </n-form-item-row>
                            <n-form-item-row label="密码">
                                <n-input v-model:value="loginField.userPass"/>
                            </n-form-item-row>
                        </n-form>
                        <n-button @click="resetAction" type="primary" block secondary strong>
                            重置
                        </n-button>
                    </n-tab-pane>
                </n-tabs>
            </n-card>
        </n-modal>
        <n-scrollbar id="global_scrollbar" ref="scrollRef" style="max-height: 100vh;height: 100vh;">
            <n-layout>
                <n-layout-header v-csdnScrollHeader id="nono" class="box_sizeing_header">
                    <div class="blog_container" id="header">
                        <n-avatar
                                size="medium"
                                style="margin-right: 12px;background-color: transparent;width: 42px;height: 42px;"
                                src="/logo.png"
                        />
                        <div id="pc" class="header_blog">
                            <n-menu
                                    v-model:value="userStore.$state.routeName"
                                    mode="horizontal"
                                    :options="menuOptions"
                                    responsive
                            />
                        </div>
                        <div class="header_blog">
                            <div id="modile_dd" style="display: flex;align-items: center;flex: 1">
                                <n-input-group style="display:flex;flex: 1;">
                                    <n-select style="flex: 1"
                                              v-model:value="userStore.$state.searchLabelField" clearable
                                              :options="labelsOptionField"/>
                                    <n-input style="flex: 2"
                                             v-model:value="userStore.$state.searchArticlesNameField"
                                             clearable
                                             placeholder="搜索">
                                        <template #suffix>
                                            <n-icon @click="searchAction" class="blog_cursor" :component="Search"
                                                    :size="20"/>
                                        </template>
                                    </n-input>
                                </n-input-group>
                                <!--                                手机-->
                                <n-icon style="margin-left: 12px;" id="mobile" @click="mobileFied = !mobileFied"
                                        size="28">
                                    <MenuSharp/>
                                </n-icon>
                                <!--                                电脑-->
                                <n-icon id="pc" style="margin-left: 12px;" @click="changeThemeState" size="25">
                                    <Moon/>
                                </n-icon>
                                <!--                                <n-icon size="27" v-if="!userStore.getInit" @click="showModal = true">-->
                                <n-icon id="pc" style="margin-left: 12px;" size="27" v-if="!userStore.getInit"
                                        @click="changeLoginStatus">
                                    <PersonCircle/>
                                </n-icon>
                                <n-dropdown
                                        v-else
                                        placement="bottom-start"
                                        trigger="hover"
                                        size="large"
                                        :show-arrow="true"
                                        :options="options"
                                >
                                    <n-avatar
                                            id="pc"
                                            style="margin-left: 12px;"
                                            round
                                            :size="30"
                                            :src="userStore.getUser.userImage"
                                    />
                                </n-dropdown>
                            </div>
                        </div>
                    </div>
                </n-layout-header>
                <n-layout-content class="box_sizeing_body">
                    <RouterView/>
                </n-layout-content>
            </n-layout>
            <n-back-top :right="0">
            </n-back-top>
        </n-scrollbar>
    </div>
</template>

<style lang="scss" scoped>
    .blog_container {
        display: flex;
        justify-content: space-between;

        .header_blog {
            display: flex;
            align-items: center;
            justify-content: flex-end;
            flex: 1;
        }
    }

    ::v-deep(.n-drawer-header) {
        padding: 12px !important;
    }

    ::v-deep(.n-drawer-body-content-wrapper) {
        padding: 12px !important;
    }

    ::v-deep(.n-drawer-header__main) {
        flex: 1 !important;
    }

    ::v-deep(.n-layout) {
        background-color: transparent;
    }
</style>
