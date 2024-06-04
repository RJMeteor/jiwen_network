import {defineStore} from "pinia";
import {defaultBlogUserNotificationSettings, defaultUser} from "@/d/defautls";
import {themeState} from "@/config/data/app";
import {mdTheme} from "@/config/data/create";
import {MenuOption} from "naive-ui";

const useUserStore = defineStore('user', {
    state: () => {
        return {
            searchAction: () => {
            },
            searchLabelField: null,
            searchArticlesNameField: "",
            //用于提示消息通知
            routeName: "" as string,
            //md主题
            mdTheme: "classic" as "classic" | "dark",
            //主题状态
            themeState: true,
            mobileMenu: null,
            //登录状态
            init: false,
            //登录遮罩状态
            loginStatus: false,
            //jwt
            token: "",
            //消息设置
            notiSet: {
                ...defaultBlogUserNotificationSettings
            } as BlogUserNotificationSettings,
            user: {
                ...defaultUser
            } as BlogUser
        }
    },
    getters: {
        getRouteName(): string {
            return this.routeName
        },
        getInit(): boolean {
            return this.init
        },
        getThemeState(): boolean {
            return this.themeState
        },
        getMdThemee(): "classic" | "dark" {
            return this.mdTheme
        },
        getnotiSet(): BlogUserNotificationSettings {
            return this.notiSet
        },
        getUser(): BlogUser {
            return this.user
        }
    },
    actions: {
        updateRouteName(routeName: string) {
            this.routeName = routeName
        },
        updateUser(data: BlogUser) {
            this.user = data
        },
        updatenotiSet(notiSet: BlogUserNotificationSettings) {
            this.notiSet = notiSet
        },
        updateInit(init: boolean) {
            this.init = init
        }
    },
    // 表示这个store里的数据都将持久化存储
    persist: true
})

export {useUserStore}

