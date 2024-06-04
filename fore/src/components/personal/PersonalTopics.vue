<script setup lang="ts">
    import Loading from "@/d/Loading.vue"
    import TopicContent from "@/components/TopicContent.vue"
    import {
        articleByUserIdAndNameRequest,
        articlesByNameAndLabelRequest,
        deleteArticleByIdsRequest
    } from "@/config/request";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {ref} from "vue";
    import {useRoute} from "vue-router";
    import {useUserStore} from "@/config/store/user";
    import {
        Create,
        Search,
        EllipsisHorizontalCircleOutline,
    } from "@vicons/ionicons5"
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {router} from "@/config/router";

    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()

    const articles = ref<BlogArticle[]>([])
    const route = useRoute()
    const userStore = useUserStore()
    const emptyField = ref<boolean>(false)
    const pageInfo = ref<any>({
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: true
    })

    executeCallback.value = reloadTopic
    const searchField = ref<string>("")

    function reloadTopic(ids: number[]) {
        deleteArticleByIdsRequest(ids).then(res => {
            if (res) {
                searchField.value = ""
                pageInfo.value = {
                    total: 0,
                    pageNum: 1,
                    pageSize: 10,
                    loading: false
                }
                searchTopicAction()
            }
        })
    }

    function searchTopicAction() {
        articles.value = []
        pageInfo.value = {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        }
        pageDataAction()
    }

    function pageDataAction() {
        emptyField.value = false
        const tofromUserId = +route.params.userId
        const localUserId = userStore.getUser.id
        console.log("访问人的ID", tofromUserId)
        console.log("本地缓存中用户的ID", localUserId)

        //0公开，1私密
        const isPrivate = localUserId == tofromUserId ? 0 : 1
        const primaryUserId = localUserId == tofromUserId ? localUserId : tofromUserId
        articleByUserIdAndNameRequest(+primaryUserId,
            isPrivate,
            1,
            searchField.value,
            pageInfo.value.pageNum,
            pageInfo.value.pageSize).then((res: any) => {
            if (res.list) {
                articles.value = res.list
                // let domParser = new DOMParser();
                // let index = 0
                // for (let value of res.list) {
                //     console.log(++index)
                //     renderMarkdownToHtml(value.articleContent.articleMd).then(value1 => {
                //         value.articleContent.articleMd = domParser.parseFromString(value1, "text/html")
                //         articles.value.push(value)
                //         emptyField.value = articles.value.length > 0 ? false : true
                //     })
                // }
                // delete res.list
                pageInfo.value = {...pageInfo.value, ...res}
            }
            emptyField.value = articles.value.length > 0 ? false : true
            searchField.value = ""
            loadingAction()
        }).catch(error => {
            emptyField.value = true
            pageInfo.value.loading = false
        })
    }

    //加载状态验证
    function loadingAction() {
        emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.value
        if ((pageNum * pageSize) >= total) {

            pageInfo.value = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                loading: false
            }
        } else {
            pageInfo.value.loading = true
            pageInfo.value.pageNum++
        }
    }

    /**
     * 跳转到编辑页面
     * @param articleId
     */
    function editArticleAction(articleId: number) {
        router.push({
            name: "create",
            params: {
                type: "request",
                id: articleId
            }
        })
    }
</script>

<template>
    <div class="personalTopics">
        <n-card :bordered="false" :segmented="{
      content: true,
      footer: 'soft'
    }" style="margin-bottom: 16px">
            <template #header>
                <n-input style="max-width: 200px;" v-model:value="searchField" round clearable placeholder="文章名">
                    <template #suffix>
                        <n-icon @click="searchTopicAction" class="blog_cursor" :component="Search" :size="20"/>
                    </template>
                </n-input>
            </template>
            <template #header-extra>
                <div style="display: flex;align-items: center;">
                    <n-dropdown
                            v-if="route.params.userId == userStore.getUser.id"
                            trigger="hover"
                            :options="options"
                            :show-arrow="true"
                            @select="handleSelect"
                    >
                        <n-icon size="25">
                            <EllipsisHorizontalCircleOutline/>
                        </n-icon>
                    </n-dropdown>
                </div>
            </template>
        </n-card>
        <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
            <div>
                <n-list v-for="item in articles" clickable style="margin-top: 12px;padding: 0px 12px;">
                    <n-list-item>
                        <template v-if="checkBoxShow" #prefix>
                            <n-checkbox v-show="checkBoxShow" :value="item.articleContent.articleId"/>
                            {{pushKey(item.articleContent.articleId)}}
                        </template>
                        <n-thing>
                            <template #header-extra>
                                <n-tooltip v-if="route.params.userId == userStore.getUser.id"
                                           placement="top-start" trigger="hover">
                                    <template #trigger>
                                        <n-icon @click.stop="editArticleAction(item?.articleContent?.articleId)"
                                                size="14">
                                            <Create/>
                                        </n-icon>
                                    </template>
                                    编辑
                                </n-tooltip>
                            </template>
                        </n-thing>
                        <TopicContent :articles="item"/>
                    </n-list-item>
                </n-list>
            </div>
        </n-checkbox-group>
        <!--        <n-list v-for="item in articles" style="margin-top: 12px;padding: 0px 20px;">-->
        <!--            <TopicContent :articles="item"/>-->
        <!--        </n-list>-->
        <n-card v-show="emptyField || pageInfo.loading">
            <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
            <Loading v-show="pageInfo.loading" :callback-action="pageDataAction"/>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .personalTopics {
        width: 100%;
    }

    ::v-deep(.n-thing-header) {
        justify-content: space-between !important;
    }
</style>
