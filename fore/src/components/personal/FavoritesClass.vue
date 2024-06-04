<script setup lang="ts">
    import Loading from "@/d/Loading.vue"
    import {
        Search,
        Create,
        Trash,
        EllipsisHorizontalCircleOutline,
    } from "@vicons/ionicons5"
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";
    import {ref, onMounted, watch} from "vue";
    import {
        addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest,
        addFavoriteClassByUserId,
        addLikeByArticleIdAndUserIdAndpersonIdRequest,
        deleteFavoriteByUserIdAndArticleIdRequest,
        deleteFavoriteClassByIdRequest,
        deleteLikeByArticleIdAndUserIdRequest,
        getFavoriteClassByUseridRequest
    } from "@/config/request";
    import {favoriteShow} from "@/config/data/reader";
    import {useUserStore} from "@/config/store/user";
    import {defaultArticle} from "@/d/defautls";
    import {useRoute} from "vue-router";

    const userStore = useUserStore()
    const route = useRoute()
    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()

    const defaultFavoriteField = {
        favorites: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true
    }
    const favoriteField = ref<{
        favorites: BlogFavoriteClass[] | undefined
        pageInfo: PageInfoType<BlogFavoriteClass>,
        loading: boolean,
    }>({
        ...defaultFavoriteField
    })
    const favoriteSearchField = ref<string>("")

    // executeCallback.value = favoritesRequest
    executeCallback.value = deleteBatchFavorites

    function deleteBatchFavorites(all: number[]) {
        deleteFavoriteClassByIdRequest(all).then(res => {
            if (res) {
                favoriteField.value = {
                    ...defaultFavoriteField
                }
                favoriteField.value.favorites = []
                favoritesRequest()
            }
        })
    }

    watch(favoriteShow, value => {
        if (!value) {
            favoriteField.value = {
                ...defaultFavoriteField
            }
            favoriteField.value.favorites = []
        }
    })

    //触发获取文件夹的回调
    function favoritesRequest() {
        favoriteField.value.loading = false
        const tofromUserId = +route.params.userId
        const localUserId = userStore.getUser.id
        console.log("访问人的ID", tofromUserId)
        console.log("本地缓存中用户的ID", localUserId)
        //0公开，1私密
        let isPrivate: number = localUserId == tofromUserId ? 0 : 1
        let primaryUserId = localUserId == tofromUserId ? localUserId : tofromUserId

        if (route.name == "reader") {
            primaryUserId = localUserId
            isPrivate = 0
        }
        const {pageNum, pageSize} = favoriteField.value.pageInfo
        //得到收藏文件夹
        getFavoriteClassByUseridRequest(primaryUserId, isPrivate, favoriteSearchField.value, pageNum, pageSize).then(res => {
            favoriteField.value.favorites?.push(...res.list as any)
            favoriteField.value.pageInfo = {...favoriteField.value.pageInfo, ...res}
            favoriteSearchField.value = ""
            loadingAction(favoriteField.value)
        })
    }


    function favoriteSearchAction() {
        favoriteField.value = {
            ...defaultFavoriteField
        }
        favoriteField.value.favorites = []
        favoritesRequest()
    }

    //新建收藏夹的名字
    const newFavoriteInputField = ref<string>("")
    const favoriteIsPrivate = ref<boolean>(false)  //新建收藏夹是否私密
    const favoriteShowCreate = ref<boolean>(false)  //新建收藏夹遮罩层装台

    /*
     * 添加收藏文件夹
     * */
    function addFavoriteClassAction() {
        let isPrivate: number = favoriteIsPrivate.value ? 1 : 0
        addFavoriteClassByUserId(newFavoriteInputField.value, userStore.getUser.id, isPrivate).then(res => {
            if (res) {
                favoriteField.value = {
                    ...defaultFavoriteField
                }
                favoriteField.value.favorites = []
                favoriteIsPrivate.value = false
                newFavoriteInputField.value = ""
                favoriteShowCreate.value = false
                favoriteSearchAction()
            }
        })
    }

    const emit = defineEmits(["addFavorite"])

    /*
    * 收藏文章，收藏自己的文章personId 为0
    * */
    function addFavorite(favoriteClass: BlogFavoriteClass) {
        emit("addFavorite", favoriteClass)
    }


    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize) >= total) {
            pageInfo.loading = false
            pageInfo.page = {
                total: 0,
                pageNum: 0,
                pageSize: 10,
            }
        } else {
            pageInfo.loading = true
            favoriteField.value.pageInfo.pageNum++
        }
    }

    function favoriteSearchFieldAction() {
        favoriteShowCreate.value = !favoriteShowCreate.value
        favoriteSearchField.value = ''
    }

    defineExpose({
        favoritesRequest,
    })
</script>

<template>
    <div class="favorites_class">
        <n-drawer
                v-model:show="favoriteShow"
                :default-width="300"
                placement="right"
                resizable
        >
            <n-drawer-content id="favorites" :native-scrollbar="false">
                <template #header>
                    <div style="width: 100%;display: flex;justify-content: space-between;align-items: center;">
                        <div>
                            收藏夹
                        </div>
                        <div>
                            <n-dropdown
                                    v-if="route.params.userId == userStore.getUser.id || route.name=='reader'"
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
                    </div>
                </template>
                <template #footer>
                    <n-flex justify="space-betweent">
                        <n-input style="margin-bottom: 12px;width: 100%!important;" v-model:value="favoriteSearchField"
                                 round clearable
                                 placeholder="搜索">
                            <template #suffix>
                                <n-icon @click="favoriteSearchAction" class="blog_cursor" :component="Search"
                                        :size="20"/>
                            </template>
                        </n-input>
                        <n-button v-if="route.params.userId == userStore.getUser.id || route.name=='reader'"
                                  style="width: 100%;" type="primary"
                                  @click="favoriteSearchFieldAction">新建
                        </n-button>
                    </n-flex>
                </template>
                <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                    <div v-for="ele in favoriteField.favorites">
                        <n-list clickable hoverable style="margin-top: 12px;">
                            <n-list-item>
                                <template v-if="checkBoxShow" #prefix>
                                    <n-checkbox v-show="checkBoxShow" :value="ele.id"/>
                                    {{pushKey(ele.id)}}
                                </template>
                                <template v-if="route.params.userId == userStore.getUser.id" #suffix>
                                    <n-space>
                                        <n-button strong secondary circle size="tiny" type="primary">
                                            <template #icon>
                                                <n-icon>
                                                    <Create/>
                                                </n-icon>
                                            </template>
                                        </n-button>
                                    </n-space>
                                </template>
                                <n-thing @click="addFavorite(ele)">
                                    <template #header>
                                        <span>{{ele.favoriteClassName}}</span>
                                    </template>
                                </n-thing>
                            </n-list-item>
                        </n-list>
                    </div>
                    <n-empty
                            v-if="!favoriteField.loading && favoriteField.favorites && favoriteField.favorites.length == 0"
                            style="margin-top: 20px;" description="空空如也..."/>
                    <Loading v-show="favoriteField.loading" :callback-action="favoritesRequest"/>
                </n-checkbox-group>
            </n-drawer-content>
        </n-drawer>
        <n-drawer
                v-model:show="favoriteShowCreate"
                :default-width="280"
                placement="right"
                resizable
        >
            <n-drawer-content>
                <template #header>
                    <div style="width: 100%;display: flex;justify-content: space-between;align-items: center;">
                        <div>
                            新建
                        </div>
                        <div>
                        </div>
                    </div>
                </template>
                <template #footer>
                    <n-button @click="addFavoriteClassAction" type="primary">保存</n-button>
                </template>
                <n-form
                        ref="formRef"
                        :style="{
      maxWidth: '640px'
    }"
                >
                    <n-form-item label="名称" path="textareaValue">
                        <n-input
                                v-model:value="newFavoriteInputField"
                                placeholder="收藏夹名"
                                type="textarea"
                                :autosize="{
          minRows: 3,
          maxRows: 5
        }"
                        />
                    </n-form-item>
                    <n-form-item label="私密" path="switchValue">
                        <n-switch v-model:value="favoriteIsPrivate"/>
                    </n-form-item>
                </n-form>
            </n-drawer-content>
        </n-drawer>
    </div>
</template>

<style lang="scss" scoped>
    .favorites_class {
        width: 100%;

        ::v-deep(.n-drawer-footer) {
            justify-content: center !important;
        }
    }

    ::v-deep(.n-drawer-header) {
        display: block !important;
    }

    #favorites {
        ::v-deep(.n-drawer-footer) {
            justify-content: center !important;
        }
    }

</style>
