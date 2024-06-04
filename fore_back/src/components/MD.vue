<script setup lang="ts">
    import {ref, onMounted, nextTick} from "vue"
    import u from "@/util/gasp 高级动画.md?url"
    import "@/assets/markdown.scss"
    import {codeBlockLineNumber, renderMarkdownToHtml} from "@/util/marked";

    const html = ref<string>("")
    const observer = new MutationObserver(function (mutations: MutationRecord[], observer: MutationObserver) {
        codeBlockLineNumber(observer)
    })

    onMounted(function () {
        const ele = document.getElementById("md_box");
        if (ele){
            observer.observe(ele as HTMLElement, {
                childList: true
            });
        }
    })

    defineExpose({
        html
    })

</script>

<template>
    <div v-html="html" data-theme="light" id="md_box" class="markdown-body">

    </div>
</template>


<style scoped lang="scss">
    .markdown-body {
        padding: 20px;
        background-color: var(--blog_background_color);
    }


</style>
