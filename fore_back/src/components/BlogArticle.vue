<script setup lang="tsx">
    import {ref, onMounted, nextTick, onUnmounted, reactive} from 'vue';
    import {MessagePlugin, Link} from 'tdesign-vue-next';

    import {showArticle, showArticleMDRef, tableInit} from "@/data/table";
    import BPagination from "@/components/BPagination.vue"
    import MD from '@/components/MD.vue'
    import tag from "tdesign-icons-vue-next/esm/components/tag";
    import {instructionsArticle} from "@/data/Instructions";
    import {tableStructure} from "@/hooks/tableStructure";
    import {
        cancelDisabledArticleIdsRequest,
        cancelDisabledUserIdsRequest, disabledArticleIdsRequest,
        disabledUserIdsRequest, getArticleListRequest,
        getLablesRequest,
        getUserListRequest
    } from "@/config/request";

    const {
        deletedStatus, //判断删除类型
        selectEmpty, //控制删除按钮显示
        selectedRowKeys,
        columns,
        data,
        handleRowClick,
        rehandleSelectChange
    } = tableInit();
    columns.value = tableStructure(instructionsArticle)
    data.value = []

    const lables = ref<BlogArticleLabel[]>([])
    const tableHeight = ref<number>(110)
    onMounted(function () {
        nextTick(function () {
            tableHeight.value = (document.getElementById("content_box")?.offsetHeight as number) - 170
        })
        getLablesRequest().then(res => {
            lables.value = res
        })
    })
    onUnmounted(function () {
        showArticle.value = false
    })
    const articleField = reactive<{
        articleTitle: string,
        userName: string,
        lableId: number | undefined
    }>({
        articleTitle: "",
        userName: "",
        lableId: undefined
    })

    const pageInfoP = ref()
    const requestData = (pageInfo) => {
        getArticleListRequest(articleField.userName, articleField.articleTitle, articleField.lableId ? articleField.lableId : 0, pageInfo.pageNum, pageInfo.pageSize).then(res => {
            data.value = res.list?.map(value => {
                const info = {index: 0, ...value,selectKeys:selectedRowKeys}
                info.index = value.articleContent?.articleId as any
                return info
            })
            pageInfo.total = res.total
            pageInfoP.value = pageInfo
        })
        // MessagePlugin.success(`pageSize变化为${JSON.stringify(pageInfo)}`);
        pageInfoP.value = pageInfo
    };

    function cancelDisabledArticleIds() {
        cancelDisabledArticleIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }

    function disabledArticleIds() {
        disabledArticleIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }
</script>
<template>
    <t-drawer v-model:visible="showArticle"
              :confirmBtn="false"
              size="large"
              :footer="false"
              show-in-attached-element
              :header="false">
        <MD ref="showArticleMDRef"/>
    </t-drawer>
    <t-card style="border-radius: 0px;" :bordered="false">
        <t-space direction="vertical">
            <div style="display: flex;justify-content: space-between;">
                <div>
                    <t-space>
                        <t-input label="文章名：" v-model="articleField.articleTitle" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-input label="用户名：" v-model="articleField.userName" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-select v-model="articleField.lableId" label="标签：" placeholder="请选择标签" auto-width clearable>
                            <t-option v-for="item in lables" :key="item.id" :value="item.id"
                                      :label="item.artName"></t-option>
                        </t-select>
                        <t-button @click="(pageInfoP.pageNum = 1 ) &&requestData(pageInfoP)" theme="primary" shape="rectangle" variant="base">
                            搜索
                        </t-button>
                    </t-space>
                </div>
                <div>
                    <t-space v-if="selectEmpty">
                        <t-button @click="cancelDisabledArticleIds" v-if="deletedStatus" theme="success">
                            取消禁用
                        </t-button>
                        <t-button @click="disabledArticleIds" v-else theme="danger">
                            禁用
                        </t-button>
                    </t-space>
                </div>
            </div>

            <t-table
                    row-key="index"
                    :data="data"
                    :height="tableHeight"
                    :headerAffixedTop="true"
                    :columns="columns"
                    :stripe="false"
                    :bordered="false"
                    :hover="true"
                    :table-layout="'fixed'"
                    size="small"
                    :select-on-row-click="false"
                    :selected-row-keys="selectedRowKeys"
                    @select-change="rehandleSelectChange"
                    :show-header="true"
                    cell-empty-content="-"
                    resizable
                    lazy-load
                    @row-click="handleRowClick"
            >
            </t-table>
            <BPagination @page="requestData"/>
        </t-space>
    </t-card>
</template>


