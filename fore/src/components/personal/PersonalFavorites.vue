<script setup lang="ts">
    import {ref, onUnmounted, onMounted} from "vue"
    import FavoritesClass from "@/components/personal/FavoritesClass.vue"
    import {
        Menu, Search, SearchOutline, Create,
        Trash,
        EllipsisHorizontalCircleOutline
    } from "@vicons/ionicons5"
    import {
        favoritesComponentInitField,
        favoritesComponentTableInitField,
        RowData
    } from "@/config/data/personalFavorites";
    import {
        addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest,
        getFavoriteArticleByUserIdRequest
    } from "@/config/request";
    import {favoriteShow} from "@/config/data/reader";
    import {useUserStore} from "@/config/store/user";
    import {useRoute} from "vue-router";

    const userStore = useUserStore()
    const maxHeight = ref<number>()
    const route = useRoute()
    onUnmounted(function () {
        //防止共享收藏夹遮罩层状态紊乱
        favoriteShow.value = false
    })
    const {
        deleteASExecuteCallback,
        executeCallback,
        columns,
        rowProps,
        rowKey,
        checkedRowKeysRef,
        handleCheck,
        data
    } = favoritesComponentTableInitField()
    const favoritesClassRef = ref<{
        favoritesRequest: () => void;
    }>()  //收藏遮罩层实例，获取暴露出来的回调和数据

    const favoritesField = ref<{
        favorites: RowData[]
        pageInfo: PageInfoType<BlogFavorite>,
        loading: boolean
    }>({
        favorites: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true
    })
    const favoriteClassNameField = ref<string>("全部")   //在那个目录下
    const favoriteClassSearchField = ref<number>(0)   //搜索用到的收藏夹Id
    const articlesNameSearchField = ref<string>("")   //搜索的文章名


    onMounted(function () {
        maxHeight.value = document.documentElement.offsetHeight - 297
        getFavoritesClasssRequest()
    })
    executeCallback.value = getAll
    deleteASExecuteCallback.value = getFavoritesClasssRequest

    function getAll() {
        favoriteClassNameField.value = "全部"
        favoriteClassSearchField.value = 0
        getFavoritesClasssRequest()
    }

    /*
   * 点击收藏的收藏文件夹的文章
   * */
    function clickSearchFavorite(favoriteClass: BlogFavoriteClass) {
        favoriteClassNameField.value = favoriteClass.favoriteClassName
        favoriteClassSearchField.value = favoriteClass.id
        getFavoritesClasssRequest()
    }

    function getFavoritesClasssRequest() {
        favoritesField.value.loading = false
        const tofromUserId = +route.params.userId
        const localUserId = userStore.getUser.id
        console.log("访问人的ID", tofromUserId)
        console.log("本地缓存中用户的ID", localUserId)
        //0公开，1私密
        const isPrivate = localUserId == tofromUserId ? 0 : 1

        // personId == userid ? 0
        getFavoriteArticleByUserIdRequest(tofromUserId,
            articlesNameSearchField.value,
            favoriteClassSearchField.value,
            isPrivate,
            favoritesField.value.pageInfo.pageNum,
            favoritesField.value.pageInfo.pageSize).then(res => {
            favoritesField.value.favorites = []
            if (res) {
                console.log(res)
                res.list?.forEach(value => {
                    const label: any = []
                    value.article?.articleLableClasss?.forEach(value1 => {
                        label.push(value1.articleLables?.artName)
                    })
                    let data: RowData = {
                        key: value.id + "-" + (value?.article?.articleContent as any).articleId + "-" + (value.article?.user as any).id,
                        articlesName: value.article?.articleTitle as string,
                        userName: (value.person ? value.person.userName : value.user?.userName) as string,
                        lable: label
                    }
                    favoritesField.value.favorites.push(data)
                })
            }
            loadingAction(favoritesField.value)
        }).catch(() => {
            loadingAction(favoritesField.value)
        })
    }

    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize ) >= total) {
            pageInfo.pageInfo = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            }
            pageInfo.loading = false
        }else {
            pageInfo.loading = true
            pageInfo.pageInfo.pageNum++
        }
    }

    /*
    * 打开收藏夹遮罩层，并请求收藏夹的数据
    * */
    function favoritesClassAction() {
        favoritesClassRef.value && favoritesClassRef.value.favoritesRequest()
        favoriteShow.value = !favoriteShow.value
    }

</script>

<template>
    <div class="personalFavorites">
        <FavoritesClass @addFavorite="clickSearchFavorite" ref="favoritesClassRef"/>
        <n-card :bordered="false" style="margin-bottom: 16px">
            <template #header>
                <div style="display: flex;align-items: center;">
                    <span style="font-size: 18px;">{{favoriteClassNameField}}</span>
                </div>
            </template>
            <template #header-extra>
                <div style="display: flex;align-items: center;">
                    <div style="display: flex;align-items: center;">
                        <n-input round clearable v-model:value="articlesNameSearchField" placeholder="文章名">
                            <template #suffix>
                                <n-icon class="blog_cursor" @click="" :component="Search" :size="20"/>
                            </template>
                        </n-input>
                        <div style="margin-left: 12px;"></div>
                        <n-icon @click="favoritesClassAction" size="25" style="cursor: pointer;">
                            <Menu/>
                        </n-icon>
                    </div>
                </div>
            </template>
            <n-spin :show="favoritesField.loading">
                <n-data-table
                        :style="{ height: `${maxHeight}px` }"
                        :max-height="maxHeight"
                        flex-height
                        style="margin-top:12px;"
                        :columns="columns"
                        :data="favoritesField.favorites"
                        :row-key="rowKey"
                        :row-props="rowProps"
                        @update:checked-row-keys="handleCheck"
                />
            </n-spin>
            <n-pagination
                    style="margin-top: 12px"
                    v-model:page="favoritesField.pageInfo.pageNum"
                    v-model:page-size="favoritesField.pageInfo.pageSize"
                    :page-count="favoritesField.pageInfo.total"
                    :page-sizes="pageSizes"
                    :disabled="!favoritesField.favorites.length"
                    :page-slot="5"
                    @update:page="getFavoritesClasssRequest"
                    @update:page-size="getFavoritesClasssRequest"
                    size="large"
                    show-quick-jumper
                    show-size-picker
            />
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .personalFavorites {
        width: 100%;
    }

    ::v-deep(.n-drawer-header__main,.n-checkbox-group) {
        width: 100%;
    }

    ::v-deep(.n-list-item__suffix) {
        flex: none !important;
    }
</style>
