import {MenuInst, MenuOption} from "naive-ui";
import {h,ref} from "vue";
import {
    Heart,
    Notifications,
    Person,
    PeopleCircleOutline,
    SettingsSharp,
    StarSharp,
    LogoTwitter,
    TvSharp,
    AlertCircle,
    EarthSharp,
    ChatbubbleEllipsesSharp,

} from "@vicons/ionicons5";
import {renderIcon} from "@/util/currency";
import {RouterLink} from "vue-router";

const menuOptions: MenuOption[] = [
    // {
    //     label: () =>
    //         h(
    //             'a',
    //             {
    //                 href: 'https://baike.baidu.com/item/%E4%B8%94%E5%90%AC%E9%A3%8E%E5%90%9F',
    //                 target: '_blank',
    //                 rel: 'noopenner noreferrer'
    //             },
    //             "数据分析"
    //         ),
    //     key: 'dataAnalysis',
    //     icon: renderIcon(BookIcon)
    // },
    {
        label:
            () =>
                h(
                    RouterLink,
                    {
                        to: {
                            name: 'accountManagement',
                        }
                    },
                    "账号管理"
                ),
        key: 'accountManagement',
        icon: renderIcon(Person)
    },
    {
        label:() =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'notificationsSettings',
                    }
                },
                "通知设置"
            ),
        key: 'notificationsSettings',
        icon: renderIcon(SettingsSharp)
    },
    {
        label: "消息推送",
        key: 'messagePush',
        icon: renderIcon(Notifications),
        children: [
            {
                label: () =>
                    h(
                        RouterLink,
                        {
                            to: {
                                name: 'privateLetter',
                            }
                        },
                        "私信"
                    ),
                key: 'privateLetter',
                icon: renderIcon(ChatbubbleEllipsesSharp),
            },
            {
                label: () =>
                    h(
                        RouterLink,
                        {
                            to: {
                                name: 'comment',
                            }
                        },
                        "评论"
                    ),
                key: 'comment',
                icon: renderIcon(LogoTwitter),
            },
            {
                label: () =>
                    h(
                        RouterLink,
                        {
                            to: {
                                name: 'followWithInterest',
                            }
                        },
                        "关注"
                    ),
                key: 'followWithInterest',
                icon: renderIcon(PeopleCircleOutline),
            },
            {
                label: () =>
                    h(
                        RouterLink,
                        {
                            to: {
                                name: 'collect',
                            }
                        },
                        "收藏"
                    ),
                key: 'collect',
                icon: renderIcon(StarSharp),
            },
            {
                label:() =>
                    h(
                        RouterLink,
                        {
                            to: {
                                name: 'like',
                            }
                        },
                        "点赞"
                    ),
                key: 'like',
                icon: renderIcon(Heart),
            },

        ]
    },
    {
        label: () =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'interactive',
                    }
                },
                "关注/粉丝"
            ),
        key: 'interactive',
        icon: renderIcon(EarthSharp)
    },
    {
        label:() =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'feedback',
                    }
                },
                "反馈"
            ),
        key: 'feedback',
        icon: renderIcon(AlertCircle)
    },
    {
        label: () =>
            h(
                RouterLink,
                {
                    to: {
                        name: 'backend',
                    }
                },
                "后台通知"
            ),
        key: 'backend',
        icon: renderIcon(TvSharp)
    },

]

const selectedKeyRef = ref('难吃')
const menuInstRef = ref<MenuInst | null>(null)
const selectAndExpand = (key: string) => {
    selectedKeyRef.value = key
    menuInstRef.value?.showOption(key)
}
export {
    menuOptions,
    selectedKeyRef,
    selectAndExpand,
    menuInstRef
}