<script setup lang="ts">
    import {ref, reactive} from "vue"
    import {MessagePlugin} from 'tdesign-vue-next';
    import {loginRequest, resetCodeRequest, resetRequest} from "@/config/request";
    import {defaultUser} from "@/data/defautls";
    import {useUserStore} from "@/config/store/user";
    import {router} from "@/config/router";

    const changeBox = ref<boolean>(false)
    const resetBox = ref<boolean>(false)

    const loginField = ref<BlogUser>({...defaultUser})

    const onReset = () => {
        MessagePlugin.success('重置成功');
    };

    const userStore = useUserStore()
    let timer: any = null
    const codeField = ref<string>("") //验证码
    const codeTimeField = ref<number>(60) //验证码倒计时
    //登录
    function loginAction() {
        const {userName, userPass} = loginField.value
        if (!userName) {
            MessagePlugin.error("用户名不能为空")
            return
        }
        if (!userPass) {
            MessagePlugin.error("用户名不能为空")
            return
        }
        loginRequest(loginField.value).then(value => {
            if (value) {
                userStore.$patch({
                    init: true,
                    token: (value as any).token,
                    user: {
                        ...value
                    }
                })
                MessagePlugin.success(`登录成功`);
                router.push({
                    name: "index"
                })
            }
        })
    }

    //忘记密码，重置密码
    function resetAction() {
        const {userPass,userEmail} = loginField.value
        if (!userEmail) {
            MessagePlugin.error("邮箱不能为空")
            return
        }
        if (!userPass) {
            MessagePlugin.error("用户名不能为空")
            return
        }
        if (!codeField.value) {
            MessagePlugin.error("验证码不能为空")
            return
        }
        resetRequest(loginField.value, codeField.value)
        timer = setInterval(function () {
            if (!codeTimeField.value) {
                clearInterval(timer)
                codeTimeField.value = 60;
            }
        }, 1000)
    }

    /**
     * 放入微队列
     * @param callback
     */
    function microqueueIn(callback: () => void) {
        new Promise(resolve => {
            resolve("")
        }).then(function () {
            callback && callback()
        })
    }

    if (userStore.$state.user.id) {
        router.push({
            name: "index"
        })
    }

    //忘记密码，重置密码验证码
    function resetCodeAction() {
        const {userEmail} = loginField.value
        if (!userEmail) {
            MessagePlugin.error("邮箱不能为空")
            return
        }
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
</script>
<template>
    <div class="login">
        <div class="login_box">
            <t-card v-show="resetBox" title="重置密码" :bordered="false" style="width: 100%;height: fit-content;">
                <template #actions>
                    <span @click="resetBox = !resetBox">返回</span>
                </template>
                <div style="width: 100%;">
                    <t-form ref="form" :data="formData" :colon="true" :label-width="0">
                        <t-form-item label="新增">
                            <t-input v-model="loginField.userEmail" clearable placeholder="请输入邮箱"></t-input>
                            <t-button
                                    variant="outline"
                                    @click="resetCodeAction"
                                    :disabled="codeTimeField==60?false:true" theme="default">
                                {{codeTimeField==60?"发送验证码":codeTimeField+"秒"}}
                            </t-button>
                        </t-form-item>
                        <t-form-item name="password">
                            <t-input v-model="codeField" type="password" clearable placeholder="请输入验证码">
                                <template #prefix-icon>
                                    <t-icon name="link"/>
                                </template>
                            </t-input>
                        </t-form-item>
                        <t-form-item name="password">
                            <t-input v-model="loginField.userPass" type="password" clearable placeholder="请输入密码">
                                <template #prefix-icon>
                                    <t-icon name="link"/>
                                </template>
                            </t-input>
                        </t-form-item>
                        <t-form-item>
                            <t-button @click="resetAction" theme="primary" block>重置</t-button>
                        </t-form-item>
                    </t-form>
                </div>
            </t-card>


            <!--          账号登录-->
            <t-card v-if="!changeBox && !resetBox" title="账号密码登录" :bordered="false"
                    style="width: 100%;height: fit-content;">
                <!--                <template #actions>-->

                <!--                    <span @click="changeBox = !changeBox">邮箱注册</span>-->
                <!--                </template>-->
                <div style="width: 100%;">
                    <t-form ref="form" :data="loginField" :colon="true" :label-width="0">
                        <t-form-item name="account">
                            <t-input v-model="loginField.userName" clearable placeholder="请输入账户名">
                                <template #prefix-icon>
                                    <t-icon name="link"/>
                                </template>
                            </t-input>
                        </t-form-item>

                        <t-form-item name="password">
                            <t-input v-model="loginField.userPass" type="password" clearable placeholder="请输入密码">
                                <template #prefix-icon>
                                    <t-icon name="link"/>
                                </template>
                            </t-input>
                        </t-form-item>
                        <t-form-item>
                            <t-link theme="danger" hover="color">
                                <jump-icon slot="suffixIcon"/>
                                <span @click="resetBox = !resetBox">忘记密码？</span>
                            </t-link>
                        </t-form-item>
                        <t-form-item>
                            <t-button @click="loginAction" theme="primary" type="submit" block>登录</t-button>
                        </t-form-item>
                    </t-form>
                </div>
            </t-card>
        </div>
    </div>
</template>

<style scoped lang="less">

    .login {
        width: 100vw;
        height: 100vh;
        /*background-image: url("/login.svg");*/
        background-image: url("https://taichu-web.ia.ac.cn/chat/assets/file/contactef6b57d1.svg");
        /*background-image: url("/back.png");*/
        background-repeat: no-repeat;
        background-size: cover;
        display: flex;
        align-items: center;
        justify-content: center;

        ::v-deep(.t-input) {
            background-color: rgba(255, 255, 255, 0.2) !important;
        }

        .t-card {
            /*backdrop-filter: blur(2px) saturate(180%);*/
            /*-webkit-backdrop-filter: blur(2px) saturate(180%);*/
            /*background-color: rgba(255, 255, 255, 0.5);*/
            background-color: rgba(255, 255, 255, 0.3);
            border-radius: 12px;
            /*background-color: transparent;*/
            /*border: 1px solid rgba(209, 213, 219, 0.3);*/
        }

        .login_box {
            width: 400px;
            height: fit-content;
        }
    }

</style>
