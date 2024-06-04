import {defineStore} from "pinia";

interface NotificationsSettings {
    collect: boolean,
    like: boolean,
    follow: boolean,
    comment: boolean,
    privateLetter: boolean
}

const useNotificationsSettingsStore = defineStore('notificationsSettings', {
    state: () => {
        return {
            init: false,
            notificationsSettings: {
                collect: false,
                like: false,
                follow: false,
                comment: false,
                privateLetter: false
            },
        }
    },
    getters: {
        getInit(): boolean {
            return this.init
        },
        getSetting(): boolean {
            return this.init
        }
    },
    actions: {
        updateSetting(data: NotificationsSettings) {
            this.notificationsSettings = data
        },
        updateInit(init: boolean) {
            this.init = init
        }
    },
    // 表示这个store里的数据都将持久化存储
    persist: true
})

export {useNotificationsSettingsStore}

export type {
    NotificationsSettings
}