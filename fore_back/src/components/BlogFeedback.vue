<script setup lang="tsx">
    import {ref, onMounted, nextTick, reactive} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';

    import {tableInit} from "@/data/table";
    import BPagination from "@/components/BPagination.vue"
    import {tableStructure} from "@/hooks/tableStructure";
    import {instructionsArticleLabel, instructionsFeedback} from "@/data/Instructions";
    import {
        activeFeedbackIdsRequest,
        deleteBackIdsRequest,
        getFeedbackListAndUserNameRequest,
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

    const tableHeight = ref<number>(110)
    columns.value = tableStructure(instructionsFeedback)
    data.value = [
        ...new Array(5).fill(null).map((item, i) => ({
            index: i + 1,
            "user.userName": "renjia",
            "feedbackContent.feedbackContent": "反馈内容",
            "createTime": '反馈时间',
            deleted: 0,
        }))
    ]

    onMounted(function () {
        nextTick(function () {
            tableHeight.value = (document.getElementById("content_box")?.offsetHeight as number) - 170
        })
    })

    const feebackField = reactive<{
        feebackName: string,
        startTime: string,
        endTime: string
    }>({
        feebackName: "",
        startTime: "",
        endTime: ""
    })

    const pageInfoP = ref()

    const requestData = (pageInfo) => {
        getFeedbackListAndUserNameRequest(feebackField.feebackName, feebackField.startTime, feebackField.endTime, pageInfo.pageNum, pageInfo.pageSize).then(res => {
            data.value = res.list?.map(value => {
                const info = {index: 0, ...value, selectKeys: selectedRowKeys}
                info.index = info.id
                return info
            })
            pageInfo.total = res.total
            pageInfoP.value = pageInfo
        })
        pageInfoP.value = pageInfo
        // MessagePlugin.success(`pageSize变化为${JSON.stringify(pageInfo)}`);
        // console.log(pageInfo);
    };

    function handleRangePick(value, context) {
        let times = context.dayjsValue.map((d) => d.format('YYYY-MM-DD'))
        feebackField.startTime = times[0]
        feebackField.endTime = times[1]
    }

    function activeBackIds() {
        activeFeedbackIdsRequest(selectedRowKeys.value).then(res => {
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
                        <t-input label="反馈方：" v-model="feebackField.feebackName" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-date-range-picker allow-input clearable @change="handleRangePick"/>
                        <t-button @click="requestData(pageInfoP)" theme="primary" shape="rectangle" variant="base">
                            搜索
                        </t-button>
                    </t-space>
                </div>
                <div>
                    <t-space v-if="selectEmpty">
                        <t-button @click="activeBackIds"  theme="success">
                            受理
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
                    size="medium"
                    :select-on-row-click="false"
                    :selected-row-keys="selectedRowKeys"
                    @select-change="rehandleSelectChange"
                    :show-header="true"
                    cell-empty-content="-"
                    resizable
                    lazy-load
                    @row-click="handleRowClick"
            >
                <template #expandedRow="{ row }">
                    <div class="more-detail">
                        <p class="title"><b>反馈用户:</b>{{row.user?.userName}}</p>
                        <br/>
                        <p class="title"><b>反馈内容:</b></p>
                        <p v-html="row.feedbackContent?.feedbackContent?.replaceAll('\n','<br/>')"></p>
                    </div>
                </template>
            </t-table>
            <BPagination @page="requestData"/>
        </t-space>
    </t-card>
</template>


