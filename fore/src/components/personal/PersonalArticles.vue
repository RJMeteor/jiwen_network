<script setup lang="ts">
    import {onMounted, ref, watch} from "vue"
    import {
        options,
        checkBoxShow,
        pushKey,
        handleUpdateValue,
        citiesRef,
        handleSelect,
        callback
    } from "@/config/data/personalArticles"
    import {
        Search,
        Create,
        EllipsisHorizontalCircleOutline,
        ChatbubbleEllipsesSharp,
        Heart
    } from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import {articleByUserIdAndNameRequest, deleteArticleByIdsRequest} from "@/config/request";
    import Loading from "@/d/Loading.vue"
    import {useUserStore} from "@/config/store/user";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {DropdownOption} from "naive-ui";
    import {useRoute, useRouter} from "vue-router";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";

    const userStore = useUserStore()
    const router = useRouter()
    const route = useRoute()
    const articles = ref<BlogArticle[]>([])
    const searchField = ref<string>("")
    const emptyField = ref<boolean>(false)
    const pageInfo = ref<any>({
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: true
    })


    //请求搜索
    function searchAction() {
        articles.value = []
        pageInfo.value = {
            total: 0,
            pageNum: 1,
            pageSize: 10,
            loading: true
        }
    }

    callback.value = function () {
        deleteArticleByIdsRequest(citiesRef.value).then(res => {
            if (res) {
                searchAction()
                // pageDataAction()
            }
        })
    }


    watch(searchField, value => {
        console.log(value)
    })

    /**
     *
     * 请求分页数据
     * @param
     */
    function pageDataAction() {
        emptyField.value = false
        const tofromUserId = +route.params.userId
        const localUserId = userStore.getUser.id
        console.log("访问人的ID", tofromUserId)
        console.log("本地缓存中用户的ID", localUserId)
        //0公开，1私密
        const isPrivate = localUserId == tofromUserId ? 0 : 1
        const primaryUserId = localUserId == tofromUserId ?  localUserId : tofromUserId

        articleByUserIdAndNameRequest(+primaryUserId,
            isPrivate,
            0,
            searchField.value,
            pageInfo.value.pageNum,
            pageInfo.value.pageSize).then((res: any) => {
            if (res.list) {
                console.log(res.list)
                let domParser = new DOMParser();
                res.list.forEach((value, index) => {
                    renderMarkdownToHtml(value.articleContent.articleMd).then(value1 => {
                        value.articleContent.articleMd = domParser.parseFromString(value1, "text/html").body.textContent
                        articles.value.push(value)
                        emptyField.value = articles.value.length > 0 ? false : true
                    })
                })
                // console.log(res.list)
                delete res.list
                pageInfo.value = {...pageInfo.value, ...res}
            }
            loadingAction()
        }).catch(error => {
            emptyField.value = true
            pageInfo.value.loading = false
        })
    }

    //加载状态验证
    function loadingAction() {
        emptyField.value = articles.value.length> 0 ? false:true
        const {total, pageNum, pageSize} = pageInfo.value
        if ((pageNum * pageSize) >= total) {
            pageInfo.value = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                loading: false
            }
        }else {
            pageInfo.value.loading = true
            pageInfo.value.pageNum++
        }
    }

    /*
    * 阅读文章
    * */
    function readerAction(userId: number, articleId: number) {
        // router.push({
        //     name: "reader",
        //     params: {
        //         userId,
        //         articleId
        //     }
        // })
        let routeUrl = router.resolve({
            name: "reader",
            params: {
                userId,
                articleId
            }
        });
        window.open(routeUrl.href, '_blank');
    }

    /**
     * 跳转到编辑页面
     * @param articleId
     */
    function editArticleAction(articleId: number) {
        let routeUrl = router.resolve({
            name: "create",
            params: {
                type: "request",
                id: articleId
            }
        });
        window.open(routeUrl.href, '_blank');
        // router.push({
        //     name: "create",
        //     params: {
        //         type: "request",
        //         id: articleId
        //     }
        // })
    }
</script>

<template>
    <div class="personalArticles">
        <n-card :bordered="false" :segmented="{
      content: true,
      footer: 'soft'
    }" style="margin-bottom: 16px">
            <template #header>
                <n-input style="max-width: 200px" v-model:value="searchField" round clearable placeholder="文章名">
                    <template #suffix>
                        <n-icon @click="searchAction" class="blog_cursor" :component="Search" :size="20"/>
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
            <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
            <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                <div>
                    <n-list clickable style="padding: 0px 12px;">
                        <n-list-item v-for="ele in articles">
                            <template v-if="checkBoxShow" #prefix>
                                <n-checkbox v-show="checkBoxShow" :value="ele.articleContent.articleId"/>
                                {{pushKey(ele.articleContent.articleId)}}
                            </template>
                            <n-thing @click="readerAction(ele.user.id,ele.articleContent.articleId)">
                                <template #header>
                                    <n-text strong>
                                        <div style="display: flex;justify-content: center;align-items: center">
                                            {{ele.articleTitle}}
                                        </div>
                                    </n-text>
                                </template>
                                <template #footer>
                                    <n-flex justify="space-between" size="large">
                                        <n-text depth="3">
                                            <div style="display: flex;align-items: center;">
                                                <!--                                                renjia-->
                                                <!--                                                <n-divider vertical/>-->
                                                <n-icon size="14">
                                                    <ChatbubbleEllipsesSharp/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                        {{ele.commentCount?ele.commentCoun:""}}
                                        </span>

                                                <n-divider vertical/>
                                                <n-icon size="14">
                                                    <Heart/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                         {{ele.likeUserId.length?ele.likeUserId.length:""}}
                                        </span>

                                                <n-divider vertical/>
                                                <n-icon size="14">
                                                    <EyeRegular/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                        {{ele.browseCount?ele.browseCount:""}}
                                        </span>
                                                <n-divider vertical/>
                                                <n-tooltip v-if="route.params.userId == userStore.getUser.id"
                                                           placement="top-start" trigger="hover">
                                                    <template #trigger>
                                                        <n-icon @click.stop="editArticleAction(ele?.articleContent?.articleId)"
                                                                size="14">
                                                            <Create/>
                                                        </n-icon>
                                                    </template>
                                                    编辑
                                                </n-tooltip>

                                            </div>
                                        </n-text>
                                        <div>
                                            <n-space>
                                                <n-button v-for="la in ele.articleLableClasss" size="tiny" tertiary>
                                                    {{la.articleLables.artName}}
                                                </n-button>
                                            </n-space>
                                        </div>
                                    </n-flex>
                                </template>
                                <div>
                                    <n-grid :x-gap="12"
                                            :y-gap="12" :cols="8" style="margin-top: 5px;"
                                            layout-shift-disabled>
                                        <n-gi :span="ele.articleImage?7 : 8">
                                            <n-ellipsis :tooltip="false" :line-clamp="3" style="max-width: 100%">
                                                <n-text depth="3">
                                                    {{ele.articleContent.articleMd}}
                                                </n-text>
                                            </n-ellipsis>
                                        </n-gi>
                                        <n-gi v-show="ele.articleImage">
                                            <n-image
                                                    width="100%"
                                                    :src="ele.articleImage"
                                            />
                                        </n-gi>
                                    </n-grid>
                                </div>
                            </n-thing>
                        </n-list-item>
                    </n-list>
                </div>
            </n-checkbox-group>
            <Loading v-show="pageInfo.loading" :callback-action="pageDataAction"/>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .personalArticles {
        width: 100%;
    }

    .n-divider:not(.n-divider--vertical) {
        margin: 0px;
    }

    ::v-deep(.n-thing-header) {
        justify-content: space-between !important;
    }

    ::v-deep(.n-card.n-card--content-segmented) {
        > .n-card__content {
            padding-top: 0px;
        }
    }

    ::v-deep(.n-card__content) {
        padding: 0px 0px;
        padding-bottom: 0px;
    }

    ::v-deep( .n-thing-main__content) {
        margin-top: 0px !important;
    }

    ::v-deep(  .n-thing-main__footer) {
        margin-top: 0px !important;
    }

</style>
