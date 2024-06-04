import {MenuOption} from "naive-ui";
import {h} from "vue";
import {Book, FolderOpenSharp, Help, StarSharp} from "@vicons/ionicons5";
import {renderIcon} from "@/util/currency";
import {RouterLink, useRoute} from "vue-router";
import {useUserStore} from "@/config/store/user";


function menuOptions(userId:number) {
    // menuOptions: MenuOption[]
    return  [
        {
            label: () =>
                h(
                    RouterLink,
                    {
                        to: {
                            name: 'personalArticles',
                            params: {
                                userId
                            }
                        }
                    },
                    "文章"
                ),
            key: 'personalArticles',
            icon: renderIcon(Book)
        },
        {
            label: () =>
                h(
                    RouterLink,
                    {
                        to: {
                            name: 'personalFavorites',
                            params: {
                                userId
                            }
                        }
                    },
                    "收藏"
                ),
            key: 'collection',
            icon: renderIcon(FolderOpenSharp)
        },
        {
            label: () =>
                h(
                    RouterLink,
                    {
                        to: {
                            name: 'personalTopics',
                            params: {
                                userId
                            }
                        }
                    },
                    "话题"
                ),
            key: 'personalTopics',
            icon: renderIcon(Help)
        }
    ]
}



export {
    menuOptions
}