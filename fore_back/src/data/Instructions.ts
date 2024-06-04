import {ref} from "vue"
import {Link, Space, Tag} from "tdesign-vue-next";
import {showArticle, showArticleMDRef} from "@/data/table";
import u from "@/util/gasp 高级动画.md?url"
import {renderMarkdownToHtml} from "@/util/marked";
import {
    activeFeedbackIdRequest,
    cancelDisabledArticleRequest, cancelDisabledLableRequest,
    cancelDisabledUserRequest, deleteBackIdRequest,
    disabledArticleRequest, disabledLableRequest,
    disabledUserRequest
} from "@/config/request";
// 默认用户
let BlogUser: BlogUser
const instructionsUser = {
    "row-select": {
        type: 'multiple',
        width: 50,
    },
    userName: {
        title: "用户名",
        ellipsis: true,
    },
    userEmail: {
        title: "QQ邮箱",
        ellipsis: true,
    },
    userPhone: {
        title: "电话号码",
        ellipsis: true,
    },
    userSex: {
        title: "性别",
        cell: (h, {row}) => {
            return row.userSex == 0 ? "男" : "女"
        },
    },
    createTime: {
        title: "注册时间",
        ellipsis: true,
    },
    deleted: {
        title: "状态",
        cell: (h, {row}) => {
            return row.deleted == 0 ?
                h(Tag, {theme: "success", shape: "round"}, "已激活")
                : h(Tag, {theme: "danger", shape: "round"}, "已禁用")
        },
    },
    operation: {
        title: "操作",
        cell: (h, {row}) => {
            return [
                h(Space, {},
                    [
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                if (row.deleted) {
                                    cancelDisabledUserRequest(row.id).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.id)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1

                                        }
                                    })
                                } else {
                                    disabledUserRequest(row.id).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.id)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1
                                        }
                                    })
                                }
                            }
                        }, row.deleted == 1 ? "取消禁用" : "禁用")
                    ])
            ]
        },
    }
};

// 默认文章
let BlogArticle: BlogArticle
const instructionsArticle = {
    "row-select": {
        type: 'multiple',
        width: 50,
    },
    articleTitle: {
        title: "文章名",
        ellipsis: true,
    },
    "user.userName": {
        title: "发布用户名",
        ellipsis: true,
    },
    articleLimiter: {
        title: "类型",
        cell: (h, {row}) => {
            return row.articleLimiter == 0 ? "文章" : "话题"
        },
    },
    updateTime: {
        title: "发布时间",
        ellipsis: true,
    },
    deleted: {
        title: "状态",
        cell: (h, {row}) => {
            return row.deleted == 0 ?
                h(Tag, {theme: "success", shape: "round"}, "已激活")
                : h(Tag, {theme: "danger", shape: "round"}, "已禁用")
        },
    },
    operation: {
        title: "操作",
        cell: (h, {row}) => {
            return [
                h(Space, {},
                    [
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                showArticle.value = true
                                renderMarkdownToHtml(row.articleContent.articleMd).then(async (res) => {
                                    console.log(showArticleMDRef.value);
                                    (showArticleMDRef.value as any).html = res
                                })
                                // fetch(u).then(res => {
                                //     let reader = res?.body?.getReader();
                                //     reader?.read().then(res => {
                                //         const decoder = new TextDecoder();
                                //         const str = decoder.decode(res.value);
                                //         renderMarkdownToHtml(str).then(async (res) => {
                                //             console.log(showArticleMDRef.value);
                                //             (showArticleMDRef.value as any).html = res
                                //         })
                                //     })
                                // })
                            }
                        }, "预览"),
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                console.log(row.selectKeys.length)
                                if (row.deleted) {
                                    cancelDisabledArticleRequest(row.articleContent.articleId).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.articleContent.articleId)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1
                                        }
                                    })
                                } else {
                                    disabledArticleRequest(row.articleContent.articleId).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.articleContent.articleId)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1
                                        }
                                    })
                                }
                            }
                        }, row.deleted == 1 ? "取消禁用" : "禁用")
                    ])
            ]
        },
    }
};


// 默认文章标签
let BlogArticleLabel: BlogArticleLabel
const instructionsArticleLabel = {
    "row-select": {
        type: 'multiple',
        width: 50,
    },
    artName: {
        title: "标签名",
        ellipsis: true,
    },
    createTime: {
        title: "添加时间",
        ellipsis: true,
    },
    deleted: {
        title: "状态",
        cell: (h, {row}) => {
            return row.deleted == 0 ?
                h(Tag, {theme: "success", shape: "round"}, "已激活")
                : h(Tag, {theme: "danger", shape: "round"}, "已禁用")
        },
    },
    operation: {
        title: "操作",
        cell: (h, {row}) => {
            return [
                h(Space, {},
                    [
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                row.editCallback(row)
                            }
                        }, "编辑"),
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                // console.log(row.selectKeys.length)
                                if (row.deleted) {
                                    cancelDisabledLableRequest(row.id).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.id)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1
                                        }
                                    })
                                } else {
                                    disabledLableRequest(row.id).then(res => {
                                        if (res) {
                                            let start = row.selectKeys.indexOf(row.id)
                                            row.selectKeys.splice(start, 1)
                                            row.deleted = row.deleted == 1 ? 0 : 1
                                        }
                                    })
                                }
                            }
                        }, "禁用")
                    ])
            ]
        },
    }
};


// 默认反馈
let BlogFeedback: BlogFeedback
const instructionsFeedback = {
    "row-select": {
        type: 'multiple',
        width: 50,
        checkProps: ({row}) => {
            return { disabled: row.active == 1}
        },
    },
    "user.userName": {
        title: "用户名",
        ellipsis: true,
    },
    "feedbackContent.feedbackContent": {
        title: "反馈内容",
        ellipsis: true,
    },
    "createTime": {
        title: "反馈时间",
        ellipsis: true,
    },
    deleted: {
        title: "状态",
        cell: (h, {row}) => {
            return row.active == 1 ?
                h(Tag, {theme: "success", shape: "round"}, "已受理")
                : h(Tag, {theme: "danger", shape: "round"}, "未受理")
        },
    },
    operation: {
        title: "操作",
        cell: (h, {row}) => {
            if (row.active == 1){
                return;
            }
            return [
                h(Space, {},
                    [
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                activeFeedbackIdRequest(row.id).then(res => {
                                    if (res) {
                                        row.active = 1
                                    }
                                })
                            }
                        }, "受理")
                    ])
            ]
        },
    }
};


// 默认后台消息通知表
let BlogBackMessages: BlogBackMessages
const instructionsBackMessages = {
    "row-select": {
        type: 'multiple',
        width: 50,
    },
    backContent: {
        title: "公告内容",
        ellipsis: true,
    },
    createTime: {
        title: "发布时间",
        ellipsis: true,
    },
    operation: {
        title: "操作",
        cell: (h, {row}) => {
            return [
                h(Space, {},
                    [
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                row.editCallback(row)
                            }
                        }, "编辑"),
                        h(Link, {
                            theme: "primary",
                            hover: "underline",
                            onClick() {
                                deleteBackIdRequest(row.id).then(res => {
                                    if (res) {
                                        let start = row.selectKeys.indexOf(row.id)
                                        row.datas.forEach((value, index) => {
                                            if (value.id == row.id) {
                                                row.datas.splice(index, 1)
                                                return;
                                            }
                                        })
                                        row.selectKeys.splice(start, 1)

                                    }
                                })
                            }
                        }, "删除")
                    ])
            ]
        },
    }
};


export {
    instructionsArticle,
    instructionsArticleLabel,
    instructionsBackMessages,
    instructionsFeedback,
    instructionsUser
}

