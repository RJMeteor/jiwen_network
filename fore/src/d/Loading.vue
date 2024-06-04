<script setup lang="ts">
    import {ref, onMounted, onUnmounted} from "vue"

    const loadingRef = ref()

    let timer: any = null
    const props = defineProps({
        callbackAction: {
            type: Function,
            defalut: () => {
            }
        }
    })

    const observer = new IntersectionObserver(function (entry, observer) {
        for (let intersectionObserverEntry of entry) {
            if (intersectionObserverEntry.isIntersecting) {
                clearTimeout(timer)
                timer = setTimeout(() => {
                    props.callbackAction && props.callbackAction()
                }, 500)
            }
        }
    })
    onMounted(function () {
        observer.observe(loadingRef.value)
    })


    onUnmounted(function () {
        observer.disconnect()
    })


</script>

<template>
    <div ref="loadingRef" class="loading">
        <n-spin size="small"/>
    </div>
</template>

<style lang="scss" scoped>
    .loading {
        width: 100%;
        margin-top: 10px;
        transform: translateX(50%);
    }

</style>
