<script setup lang="ts">
    import Loading from "@/d/Loading.vue"
    import {ref} from "vue";
    import {getBackListByNameRequest, getCommentByOtherPersonIdAndUserIdRequest} from "@/config/request";

    const backField = ref<{
        backs: BlogBackMessages[]
        pageInfo: PageInfoType<BlogBackMessages>,
        loading: boolean,
        emptyField: boolean
    }>({
        backs: [],
        pageInfo: {
            total: 0,
            pageNum: 1,
            pageSize: 10,
        },
        loading: true,
        emptyField: false
    })

    function loadBackList() {
        backField.value.loading = false
        const {pageNum, pageSize} = {...backField.value.pageInfo}
        getBackListByNameRequest("", pageNum, pageSize).then(res => {
                if (res.list?.length) {
                    backField.value.backs = [...res.list]
                    backField.value.pageInfo = {...res} as any
                    backField.value.emptyField = backField.value.backs.length > 0 ? false : true
                    loadingAction(backField.value)
                }
            }
        )
    }

    //加载状态验证
    function loadingAction(pageInfo: any) {
        // emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.pageInfo
        if ((pageNum * pageSize) >= total) {
            pageInfo.pageInfo = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
            }
            pageInfo.loading = false
        } else {
            pageInfo.loading = true
            pageInfo.pageInfo.pageNum++
        }
    }
</script>

<template>
    <div class="backendNotification">
        <n-card>
            <n-collapse :default-expanded-names="[0]">
                <n-collapse-item v-for="(value,index) in backField.backs" :key="index" title="张三" :name="index">
                    <p v-html="value.backContent.replaceAll('\n','<br/>')"></p>
                    <template #header-extra>
                        {{value.createTime}}
                    </template>
                    <template #header>
                        <n-ellipsis style="max-width: 300px" :tooltip="false">
                            {{value.backContent}}
                        </n-ellipsis>
                    </template>
                </n-collapse-item>
            </n-collapse>
        </n-card>
        <n-card v-show="backField.emptyField || backField.loading">
            <n-empty v-if="backField.emptyField" style="margin-top: 20px;" description="空空如也..."/>
            <Loading v-show="backField.loading" :callback-action="loadBackList"/>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .backendNotification {
        width: 100%;
    }

</style>
