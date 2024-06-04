import {darkTheme, lightTheme, MenuOption, NAvatar} from "naive-ui";
import {h, watch, ref, nextTick} from "vue";
import {RouterLink} from "vue-router";
import {BuiltInGlobalTheme} from "naive-ui/lib/themes/interface";
import {changeMdTheme} from "@/config/data/create";
import {useUserStore} from "@/config/store/user";
import {router} from "@/config/router";
import {useNotificationsSettingsStore} from "@/config/store/notificationsSettings";
import {renderIcon} from "@/util/currency";
import {Cafe, Help} from "@vicons/ionicons5";
import {Home} from "@vicons/fa";

const menuOptions: MenuOption[] = [
    {
        label: () =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'homePage',
                    }
                },
                '首页'
            ),
        key: 'homePage',
        icon: renderIcon(Home)
    },
    {
        label: () =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'topic',
                    }
                },
                '话题'
            ),
        key: 'topic',
        icon: renderIcon(Cafe)
    },
]


const options = [
    {
        label: () => {
            const userStore = useUserStore()
            return h(
                RouterLink,
                {
                    to: {
                        name: 'personalHomepage',
                        params: {
                            userId: userStore.getUser.id
                        }
                    }
                },
                '个人主页'
            )
        },
        key: 'personalHomepage',
    },
    {
        label: () =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'administration',
                    }
                },
                '管理'
            ),
        key: 'administration',
    },
    {
        label: () => h(
            RouterLink,
            {
                target: "_blank",
                to: {
                    name: 'create',
                    params: {
                        id: 0,
                        type: "other"
                    }
                }
            },
            '创作'
        ),
        key: 'create'
    },
    {
        label: () => h(
            "div",
            {
                onClick() {
                    const userStore = useUserStore()
                    const useNof = useNotificationsSettingsStore()
                    userStore.$reset()
                    useNof.$reset()
                    router.push({
                        name: "index"
                    })
                    window.$message.success(`退出登录成功`);
                    window.location.reload()
                }
            },
            '退出登录'
        ),
        key: 'logout'
    }
]


// const showModal = ref<boolean>(false)

const theme = ref<BuiltInGlobalTheme>(lightTheme)
const themeState = ref<boolean>()

nextTick(function () {
    const userStore = useUserStore()
    themeState.value = userStore.getThemeState
})

function changeThemeState() {
    const userStore = useUserStore()
    themeState.value = !themeState.value
    userStore.$patch({
        themeState: themeState.value
    })

}

function changeTheme() {
    const userStore = useUserStore()
    theme.value = themeState.value ? lightTheme : darkTheme
    themeState.value ? userStore.$patch({
        mdTheme: "classic"
    }) : userStore.$patch({
        mdTheme: "dark"
    })
    changeMdTheme()

    nextTick(function () {
        const html = document.documentElement;
        const primaryTheme = html.getAttribute("theme");
        // Object.is(primaryTheme, "light")
        !themeState.value ?
            html.setAttribute("theme", "dark") :
            html.setAttribute("theme", "light")
    })
}

/**
 * @type import('naive-ui').GlobalThemeOverrides
 */
const lightThemeOverrides = {
    common: {
        bodyColor: '#f2f3f5'
    }
    // ...
}
export {
    changeTheme,
    menuOptions,
    options,
    theme,
    themeState,
    // showModal,
    lightThemeOverrides,
    changeThemeState
}