import {defineStore} from "pinia";
import {defaultUser} from "@/data/defautls";


const useUserStore = defineStore('user', {
    state: () => {
        return {
            //md主题
            mdTheme: "light" as "light" | "dark",
            // 激活的菜单项
            activeMenu: "home" as string,
            //登录状态
            init: false,
            //jwt
            token: "",
            user: {
                ...defaultUser
            } as BlogUser
        }
    },
    getters: {
        getInit(): boolean {
            return this.init
        },
        getActiveMenu(): string {
            return this.activeMenu
        },

        getMdTheme(): "light" | "dark" {
            return this.mdTheme
        },

        getUser(): BlogUser {
            return this.user
        }
    },
    actions: {

        updateUser(data: BlogUser) {
            this.user = data
        },
        updateActiveMenu(activeMenu: string) {
            this.activeMenu = activeMenu
        },
        updateInit(init: boolean) {
            this.init = init
        }
    },
    // 表示这个store里的数据都将持久化存储
    persist: true
})

export {useUserStore}

