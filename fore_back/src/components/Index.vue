<script setup lang="ts">
    import {ref, onMounted} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';
    import {useUserStore} from "@/config/store/user";
    import {router} from "@/config/router";

    const userStore = useUserStore()

    const collapsed = ref(true);
    const iconUrl = ref('https://oteam-tdesign-1258344706.cos.ap-guangzhou.myqcloud.com/site/logo%402x.png');
    const changeTheme = () => {
        const root = document.documentElement
        let theme = Object.is(root.getAttribute('theme-mode'), "light") ? "dark" : "light"
        userStore.$patch({
            mdTheme: theme as "dark" | "light"
        })
        document.documentElement.setAttribute('theme-mode', userStore.getMdTheme);
    }
    const changeCollapsed = () => {
        collapsed.value = !collapsed.value;
        iconUrl.value = collapsed.value
            ? 'https://oteam-tdesign-1258344706.cos.ap-guangzhou.myqcloud.com/site/logo%402x.png'
            : 'https://tdesign.gtimg.com/site/baseLogo-light.png';
    };
    const activeMenu = ref<string>("home")
    onMounted(function () {
        document.documentElement.setAttribute('theme-mode', userStore.getMdTheme);
        activeMenu.value = userStore.getActiveMenu
    })

    const changeHandler = (active) => {
        userStore.$patch({
            activeMenu: active
        })
    };

    const options = [
        // {content: '个人信息', value: 1},
        {content: '退出登录', value: 2},
    ];

    const clickHandler = (data) => {
        const userStore = useUserStore()
        if (Object.is(data.content,"退出登录")){
            userStore.$reset()
            router.push({
                name:"login"
            })
            MessagePlugin.success(`退出登录成功`);
        }
    };

</script>
<template>
    <t-layout style="height: 100vh;width: 100vw;">
        <t-aside style="width: fit-content;">
            <t-menu default-value="item2" v-model:value="activeMenu" :collapsed="collapsed" height="550px"
                    @change="changeHandler">
                <template #logo>
                    <!--                    <img  :width="collapsed ? 35 : 136" :src="iconUrl" alt="logo"/>-->
                    <img style="visibility: hidden;" :width="collapsed ? 35 : 136" :src="iconUrl" alt="logo"/>
                </template>
<!--                <t-menu-item :to="{name:'home'}" value="home">-->
<!--                    <template #icon>-->
<!--                        <t-icon name="houses"/>-->
<!--                    </template>-->
<!--                    首页-->
<!--                </t-menu-item>-->
                <t-menu-group title="用户">
<!--                    <t-menu-item :to="{name:'administrator'}" value="administrator">-->
<!--                        <template #icon>-->
<!--                            <t-icon name="user-safety"/>-->
<!--                        </template>-->
<!--                        管理员管理-->
<!--                    </t-menu-item>-->
                    <t-menu-item :to="{name:'normal'}" value="normal">
                        <template #icon>
                            <t-icon name="user-list"/>
                        </template>
                        用户管理
                    </t-menu-item>
                </t-menu-group>
                <t-menu-group title="文章">
                    <t-menu-item :to="{name:'article'}" value="article">
                        <template #icon>
                            <t-icon name="pen"/>
                        </template>
                        文章管理
                    </t-menu-item>
                    <t-menu-item :to="{name:'tag'}" value="tag">
                        <template #icon>
                            <t-icon name="bookmark-double"/>
                        </template>
                        标签管理
                    </t-menu-item>
                </t-menu-group>
                <t-menu-group title="其他">
                    <t-menu-item :to="{name:'feedback'}" value="feedback">
                        <template #icon>
                            <t-icon name="bug-report"/>
                        </template>
                        反馈管理
                    </t-menu-item>

                    <t-menu-item :to="{name:'annunciation'}" value="annunciation">
                        <template #icon>
                            <t-icon name="flight-takeoff"/>
                        </template>
                        通告管理
                    </t-menu-item>
                </t-menu-group>
                <template #operations>
                    <t-button class="t-demo-collapse-btn" variant="text" shape="square" @click="changeCollapsed">
                        <template #icon>
                            <t-icon name="view-list"/>
                        </template>
                    </t-button>
                </template>
            </t-menu>
        </t-aside>
        <t-layout>
            <t-header>
                <t-head-menu height="120px">
                    <template #operations>
                        <t-space :size="6">
<!--                            <t-icon class="t-menu__operations-icon" name="search"/>-->
<!--                            <t-icon class="t-menu__operations-icon" name="notification"/>-->
                            <t-icon class="t-menu__operations-icon" @click="changeTheme" name="moon"/>
<!--                            <t-icon class="t-menu__operations-icon" name="setting"/>-->
                            <t-dropdown :options="options" trigger="hover" @click="clickHandler">
                                <t-avatar shape="round" image="https://tdesign.gtimg.com/site/avatar.jpg"
                                          size="medium"/>
                            </t-dropdown>
                        </t-space>
                    </template>
                </t-head-menu>
            </t-header>
            <t-content id="content_box"
                       style="position: relative;box-sizing: border-box;padding: 20px;padding-right:20px;height: calc(100% - 40px);width: calc(100% - 0px);border-radius: 0px;">

                <router-view v-slot="{ Component, route }">
                    <transition :duration="{ enter: 300, leave: 500 }"
                                enter-active-class="animate__animated animate__fadeIn"
                                leave-active-class="animate__animated animate__fadeOut">
                        <!--                        <keep-alive>-->
                        <component :is="Component"/>
                        <!--                        </keep-alive>-->
                    </transition>
                </router-view>

                <!--                    <RouterView/>-->
            </t-content>
        </t-layout>
    </t-layout>
</template>


<style scoped>
    .pageHome {
        display: flex;
        height: 100vh;
        width: 100vw;
    }

    .pageHome > div:last-child {
        flex: 1;
    }

    #content_box {
        overflow-y: auto;
    }

    #content_box::-webkit-scrollbar-thumb {
        border: 0px solid transparent;
        background-clip: content-box;
        background-color: var(--td-scrollbar-color);
        border-radius: 11px;
    }

    #content_box::-webkit-scrollbar {
        width: 6px;
        height: 6px;
    }
</style>
