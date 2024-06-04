<script setup lang="ts">
    import { menuOptions ,selectedKeyRef} from '@/config/data/administration'
    import {useUserStore} from "@/config/store/user";
    const userStore = useUserStore()
</script>

<template>
    <div class="administration">
        <div id="mobile">
            <RouterView/>
        </div>

        <div id="pc" style="display: flex;width: 100%;">
            <div id="pc" style="flex: 2;">
                <div v-csdnScroll>
                    <n-list bordered>
                        <n-list-item>
                            <n-menu
                                    ref="menuInstRef"
                                    v-model:value="userStore.$state.routeName"
                                    :options="menuOptions"
                                    :accordion="true"
                                    :default-expanded-keys="['messagePush']"
                            />
                        </n-list-item>
                    </n-list>
                </div>
            </div>
            <div id="pc" style="flex: 5;margin-left: 12px;">
                <RouterView/>
            </div>
        </div>
<!--        <n-grid :x-gap="12" :y-gap="12" :cols="8" layout-shift-disabled>-->
<!--            <n-gi :span="2">-->
<!--                <div v-csdnScroll>-->
<!--                <n-list bordered>-->
<!--                    <n-list-item>-->
<!--                        <n-menu-->
<!--                                ref="menuInstRef"-->
<!--                                v-model:value="userStore.$state.routeName"-->
<!--                                :options="menuOptions"-->
<!--                                :accordion="true"-->
<!--                                :default-expanded-keys="['messagePush']"-->
<!--                        />-->
<!--                    </n-list-item>-->
<!--                </n-list>-->
<!--                </div>-->
<!--            </n-gi>-->
<!--            <n-gi :span="6">-->
<!--                <RouterView/>-->
<!--            </n-gi>-->
<!--        </n-grid>-->

    </div>

</template>

<style lang="scss" scoped>
    .administration {
        width: 100%;
        box-sizing: border-box;
    }

    ::v-deep(.n-thing-header) {
        justify-content: center !important;
    }

    ::v-deep(.n-statistic) {
        display: flex;
        flex-direction: column;;
        justify-content: center;
        align-items: center;
    }
</style>
