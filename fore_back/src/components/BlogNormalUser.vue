<script setup lang="tsx">
    import {ref, onMounted, nextTick, reactive} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';
    import {tableInit} from "@/data/table";
    import BPagination from "@/components/BPagination.vue"
    import {tableStructure} from "@/hooks/tableStructure";
    import {instructionsUser} from "@/data/Instructions";
    import {cancelDisabledUserIdsRequest, disabledUserIdsRequest, getUserListRequest} from "@/config/request";

    const {
        deletedStatus, //判断删除类型
        selectEmpty, //控制删除按钮显示
        selectedRowKeys,
        columns,
        data,
        handleRowClick,
        rehandleSelectChange
    } = tableInit();
    columns.value = tableStructure(instructionsUser)
    // data.value = [
    //     ...new Array(2).fill(null).map((item, i) => ({
    //         index: i + 1,
    //         userName: "用户名",
    //         userEmail: "QQ邮箱",
    //         userPhone: '电话号码',
    //         userSex: "性别",
    //         updateTime: '注册时间',
    //         deleted: "状态",
    //     }))
    // ]

    data.value = []

    const tableHeight = ref<number>(110)

    onMounted(function () {
        nextTick(function () {
            tableHeight.value = (document.getElementById("content_box")?.offsetHeight as number) - 170
        })
    })

    const userField = reactive<{
        userName: string,
        userEmail: string
    }>({
        userName: "",
        userEmail: ""
    })
    const pageInfoP = ref()
    const requestData = (pageInfo: PageType) => {
        getUserListRequest(userField.userName, userField.userEmail, pageInfo.pageNum, pageInfo.pageSize).then(res => {
            data.value = res.list?.map(value => {
                const info = {index: 0, ...value,selectKeys:selectedRowKeys}
                info.index = info.id
                return info
            })
            pageInfo.total = res.total
            pageInfoP.value = pageInfo
        })
        // MessagePlugin.success(`pageSize变化为${JSON.stringify(pageInfo)}`);
        pageInfoP.value = pageInfo
    };

    function cancelDisabledUserIds() {
        cancelDisabledUserIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }

    function disabledUserIds() {
        disabledUserIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }
</script>
<template>
    <t-card style="border-radius: 0px;" :bordered="false">
        <t-space direction="vertical">
            <div style="display: flex;justify-content: space-between;">
                <div>
                    <t-space>
                        <t-input label="用户名：" v-model="userField.userName" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-input label="邮箱：" v-model="userField.userEmail" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-button theme="primary" @click="(pageInfoP.pageNum = 1 ) && requestData(pageInfoP)" shape="rectangle" variant="base">
                            搜索
                        </t-button>
                    </t-space>
                </div>
                <div>
                    <t-space v-if="selectEmpty">
                        <t-button @click="cancelDisabledUserIds" v-if="deletedStatus" theme="success">
                            取消禁用
                        </t-button>
                        <t-button @click="disabledUserIds" v-else theme="danger">
                            禁用
                        </t-button>
                    </t-space>
                </div>
            </div>

            <!-- 当数据为空需要占位时，会显示 cellEmptyContent -->
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

