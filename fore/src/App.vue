<script setup lang="ts">
    import Message from "@/util/message"
    import Notification from "@/util/notification"
    import {ref, watch, onMounted, nextTick, computed} from 'vue'
    import {
        themeState,
        theme,
        changeTheme,
        lightThemeOverrides
    } from "./config/data/app"
    import {useUserStore} from "@/config/store/user";

    const userStore = useUserStore()
    const broadcastChannel = new BroadcastChannel("theme");
    watch(themeState, value => {
        changeTheme()
        broadcastChannel.postMessage(value)
    })

    broadcastChannel.onmessage = function (e) {
        themeState.value = e.data
        // console.log(111, e.data)
        changeTheme()
    }
    onMounted(function () {
        themeState.value = userStore.getThemeState
        changeTheme()
    })

</script>

<template>
    <div style="width: 100vw;height:100vh">
        <n-config-provider :theme="theme" :theme-overrides="themeState ? lightThemeOverrides : null">
            <n-message-provider>
                <Message/>
            </n-message-provider>
            <n-notification-provider placement="bottom-right" :max="3">
                <Notification/>
            </n-notification-provider>
            <RouterView/>
        </n-config-provider>
    </div>
</template>

<style lang="scss" scoped>
    .blog_container {
        display: flex;
        justify-content: space-between;

        .header_blog {
            display: flex;
            align-items: center;
        }
    }
</style>
