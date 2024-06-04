<template>
    <div class="dp-text-ellipsis-wrapper">
        <div class="text" :class="textClass" :style="textStyleObject">
            <label class="btn" @click="changgeShowall"></label>
            <slot/>
            <!--            {{ info }}-->
        </div>
    </div>
</template>
<script>
    export default {
        name: 'DpTextEllipsis',
        emits: ["addBrowseAction"],
        expose:{

        },
        props: {
            defaultHeight: {
                type: Number,
                default: 300,
            },
            topicContentHeight: {
                type: Number,
                default: 0,
            },
            hiddenBtn: {
                type: Boolean,
                default: false,
            },
        },
        data() {
            return {
                topicContentHeight:0,
                showall: false,
                height: {
                    type: Number,
                    default: 0,
                },
            }
        },
        methods: {
            changgeShowall() {
                this.showall = !this.showall
                this.$emit("addBrowseAction")
            }
        },
        computed: {
            textStyleObject() {
                let min = this.defaultHeight > this.height ? this.height  : this.defaultHeight
                // let min = 5000
                return {
                    'height': this.showall ? `${this.height + 115}px` : `${min}px`,
                }
            },
            textClass() {
                let cls = this.showall ? 'showall' : ''
                // cls = cls + (!this.showall  ? '' : ' hidden-btn')
                return cls
            },
        },
        watch: {
            topicContentHeight(value) {
                this.showall = this.defaultHeight > value
                this.height = value
            }
        },
    }
</script>
<style lang="scss">
    .dp-text-ellipsis-wrapper {
        display: flex;
        /*margin: 6px 0 20px 0;*/
        overflow: hidden;
        font-size: 14px;
        line-height: 20px;
        background-color: red !important;

        .text {
            box-sizing: border-box;
            width: 100%;
            position: relative;
            overflow: hidden;
            line-height: 1.5;
            text-align: justify;
            text-overflow: ellipsis;
            word-break: break-all;
            transition: 0.3s max-height;
        }

        .text::before {
            float: right;
            height: calc(100% - 20px);
            content: '';
        }


        .text::after {
            position: absolute;
            width: 999vw;
            height: 999vw;
            margin-left: -100px;
            box-shadow: inset calc(100px - 999vw) calc(30px - 999vw) 0 0 #fff;
            content: '';
        }

        .btn {
            position: relative;
            float: right;
            clear: both;
            margin-left: 10px;
            font-size: 14px;
            padding: 0 8px;
            color: #206ef7;
            line-height: 20px;
            border-radius: 4px;
            cursor: pointer;
            z-index: 10;
        }

        .btn::after {
            /* stylelint-disable-next-line */
            font-family: element-icons !important;
            content: '展开\25BC';
        }

        .text.showall {
            max-height: none;
        }

        .text.showall .btn::before {
            display: none;
            /*visibility: hidden;*/
        }

        .text.showall .btn::after {
            display: none;
            /*visibility: hidden;*/
        }

        .text.showall.hidden-btn .btn::after {
            display: none;
            /*content: '收起\e78f';*/
            /*visibility: visible;*/
        }

        .btn::before {
            position: absolute;
            left: 1px;
            color: #333;
            transform: translateX(-100%);
            content: '...';
        }
    }
</style>
