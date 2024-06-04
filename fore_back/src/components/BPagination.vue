<script setup lang="tsx">
    import {ref, reactive, onMounted, nextTick, watch,} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';

    const pageInfo = reactive<PageType>({
        pageNum: 1,
        pageSize: 20,
        total: 100
    })

   const emit =  defineEmits(["page"])

    onMounted(function () {
        emit("page",pageInfo)
    })

    watch(pageInfo,(value, oldValue) => {
        if (value.total !=oldValue.total) return
        emit("page",pageInfo)
    })



</script>
<template>
    <t-pagination
            v-model="pageInfo.pageNum"
            v-model:pageSize="pageInfo.pageSize"
            :total="pageInfo.total"
            show-jumper
    />
</template>

<style scoped>


</style>


