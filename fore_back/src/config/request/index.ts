import {axiosRequset} from "@/config/axios";

/**
 *用户列表数据
 * @param userName  用户名
 * @param userEmail 用户邮箱
 * @param page      第几页
 * @param size      每页大小
 * @return
 */
function getUserListRequest(userName: string, userEmail: string, page: number, size: number): Promise<PageInfoType<BlogUser>> {
    return axiosRequset({
        url: "/blogUser/userList",
        method: "get",
        params: {
            userName,
            userEmail,
            page,
            size
        }
    })
}

/**
 * 管理员禁用用户（拉黑）
 *
 * @param userId  用户Id
 * @return
 */
function disabledUserRequest(userId: number): Promise<string> {
    return axiosRequset({
        url: "/blogUser/disabledUser",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            userId
        }
    })
}

/**
 * 管理员批量禁用用户（拉黑）
 *
 * @param userIds  用户Id
 * @return
 */
function disabledUserIdsRequest(userIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogUser/disabledUserIds",
        method: "put",
        data: userIds
    })
}


/**
 * 管理员取消禁用用户（拉黑）
 *
 * @param userId  用户Id
 * @return
 */
function cancelDisabledUserRequest(userId: number): Promise<string> {
    return axiosRequset({
        url: "/blogUser/cancelDisabledUser",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            userId
        }
    })
}

/**
 * 管理员批量取消禁用用户（拉黑）
 *
 * @param userIds  用户Id
 * @return
 */
function cancelDisabledUserIdsRequest(userIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogUser/cancelDisabledUserIds",
        method: "put",
        data: userIds
    })
}


//单独得到文章标签列表
function getLablesRequest(): Promise<BlogArticleLabel[]> {
    return axiosRequset({
        url: `/blogArticleLables/getLables`,
        method: "GET",
    })
}


/**
 * 查询文章信息
 *
 * @param userName     用户名
 * @param articleTitle 文章名
 * @param lableId      文章标签id
 * @param page         第几页
 * @param size         每页大小
 * @return
 */
function getArticleListRequest(userName: string, articleTitle: string, lableId: number, page: number, size: number): Promise<PageInfoType<BlogArticle>> {
    return axiosRequset({
        url: "/blogArticle/articleList",
        method: "get",
        params: {
            userName,
            articleTitle,
            lableId,
            page,
            size
        }
    })
}

/**
 * 管理员禁用文章（拉黑）
 *
 * @param articleId 文章Id
 * @return
 */
function disabledArticleRequest(articleId: number): Promise<string> {
    return axiosRequset({
        url: "/blogArticle/disabledArticle",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            articleId
        }
    })
}

/**
 * 管理员批量禁用文章（拉黑）
 *
 * @param articleIds  文章Id
 * @return
 */
function disabledArticleIdsRequest(articleIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogArticle/disabledArticleIds",
        method: "put",
        data: articleIds
    })
}


/**
 * 管理员批量取消禁用文章（拉黑）
 *
 * @param articleId  文章Id
 * @return
 */
function cancelDisabledArticleRequest(articleId: number): Promise<string> {
    return axiosRequset({
        url: "/blogArticle/cancelDisabledArticle",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            articleId
        }
    })
}

/**
 * 管理员批量取消禁文章（拉黑）
 *
 * @param articleIds  文章Id
 * @return
 */
function cancelDisabledArticleIdsRequest(articleIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogArticle/cancelDisabledArticleIds",
        method: "put",
        data: articleIds
    })
}


/**
 * 查询标签信息
 *
 * @param lableName     标签名
 * @param page         第几页
 * @param size         每页大小
 * @return
 */
function getLableListByNameRequest(lableName: string, page: number, size: number): Promise<PageInfoType<BlogArticleLabel>> {
    return axiosRequset({
        url: "/blogArticleLables/lableList",
        method: "get",
        params: {
            lableName,
            page,
            size
        }
    })
}

/**
 * 管理员禁用标签（拉黑）
 *
 * @param lableId 标签Id
 * @return
 */
function disabledLableRequest(lableId: number): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/disabledLable",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            lableId
        }
    })
}

/**
 * 管理员批量禁用标签（拉黑）
 *
 * @param lableIds  标签Id
 * @return
 */
function disabledLableIdsRequest(lableIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/disabledLableIds",
        method: "put",
        data: lableIds
    })
}


/**
 * 管理员批量取消禁用标签（拉黑）
 *
 * @param lableId  标签Id
 * @return
 */
function cancelDisabledLableRequest(lableId: number): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/cancelDisabledLable",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            lableId
        }
    })
}

/**
 * 管理员批量取消禁标签（拉黑）
 *
 * @param lableIds  标签Id
 * @return
 */
function cancelDisabledLableIdsRequest(lableIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/cancelDisabledLableIds",
        method: "put",
        data: lableIds
    })
}

/**
 * 管理员批添加标签
 *
 * @param lables  标签名
 * @return
 */
function addLablesRequest(lables: string[]): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/addLables",
        method: "post",
        data: lables
    })
}

/**
 * 管理员批修改标签
 *
 * @param lableId  标签ID
 * @param lableName  标签名
 * @return
 */
function editLableRequest(lableId: number, lableName: string): Promise<string> {
    return axiosRequset({
        url: "/blogArticleLables/editLable",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            lableId,
            lableName
        }
    })
}


/**
 * 查询公告信息
 *
 * @param backName  标签名
 * @param startTime 开始时间
 * @param endTime   结束时间
 * @param page      第几页
 * @param size      每页大小
 * @return
 */
function getBackListByNameRequest(backName: string,
                                  startTime: string,
                                  endTime: string,
                                  page: number,
                                  size: number): Promise<PageInfoType<BlogBackMessages>> {
    return axiosRequset({
        url: "/blogBackMessages/backList",
        method: "get",
        params: {
            backName,
            startTime,
            endTime,
            page,
            size
        }
    })
}


/**
 * 管理员删除公告
 *
 * @param backId 公告Id
 * @return
 */
function deleteBackIdRequest(backId: number): Promise<string> {
    return axiosRequset({
        url: "/blogBackMessages/deleteBackId",
        method: "delete",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            backId
        }
    })
}

/**
 * 管理员批量删除公告
 *
 * @param backIds 公告列表id
 * @return
 */
function deleteBackIdsRequest(backIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogBackMessages/deleteBackIds",
        method: "delete",
        data: backIds
    })
}

/**
 * 发布公告
 * @param backContent  公告内容
 * @return
 */
function addBackRequest(backContent: string): Promise<string> {
    return axiosRequset({
        url: "/blogBackMessages/addBack",
        method: "post",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            backContent
        }
    })
}

/**
 * 修改公告
 * @param backId 公告ID
 * @param backName 公告内容
 * @return
 */
function editBackRequest(backId: number, backName: string): Promise<string> {
    return axiosRequset({
        url: "/blogBackMessages/editBack",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            backId,
            backName
        }
    })
}


/**
 * 查询反馈信息
 *
 * @param userName  用户名
 * @param startTime 开始时间
 * @param endTime   结束时间
 * @param page      第几页
 * @param size      每页大小
 * @return
 */
function getFeedbackListAndUserNameRequest(userName: string,
                                           startTime: string,
                                           endTime: string,
                                           page: number,
                                           size: number): Promise<PageInfoType<BlogFeedback>> {
    return axiosRequset({
        url: "/blogFeedback/feedbackListAndUserName",
        method: "get",
        params: {
            userName,
            startTime,
            endTime,
            page,
            size
        }
    })
}


/**
 * 管理员受理反馈
 *
 * @param feedbackId 反馈Id
 * @return
 */
function activeFeedbackIdRequest(feedbackId: number): Promise<string> {
    return axiosRequset({
        url: "/blogFeedback/activeFeedbackId",
        method: "put",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            feedbackId
        }
    })
}

/**
 * 管理员批量受理反馈
 *
 * @param feedbackId 反馈Id
 * @return
 */
function activeFeedbackIdsRequest(feedbackIds: number[]): Promise<string> {
    return axiosRequset({
        url: "/blogFeedback/activeFeedbackIds",
        method: "put",
        data: feedbackIds
    })
}

/**
 *登录
 * @param user  用户数据
 */
function loginRequest(user: BlogUser): Promise<BlogUser> {
    return axiosRequset({
        url: "/blog/admin",
        method: "post",
        data: {
            ...user
        }
    })
}

/**
 *忘记密码，重置密码
 * @param user 用户数据
 * @param code  验证码
 */
function resetRequest(user: BlogUser, code: string) {
    return axiosRequset({
        url: `/blog/reset/${code}`,
        method: "post",
        data: {
            ...user
        }
    })
}

/**
 *发送重置验证码
 */
function resetCodeRequest(user: BlogUser) {
    return axiosRequset({
        url: `/blog/resetCode`,
        method: "post",
        data: {
            ...user
        }
    })
}
export {
    getUserListRequest,  //获取用户数据列表
    disabledUserRequest, //管理员禁用用户（拉黑）
    cancelDisabledUserRequest, //管理员取消禁用用户（拉黑）
    getLablesRequest, //单独得到文章标签列表
    cancelDisabledUserIdsRequest, //管理员批量取消禁用用户（拉黑）
    disabledUserIdsRequest, //管理员批量禁用用户（拉黑）
    getArticleListRequest, //查询文章信息
    disabledArticleRequest, //管理员禁用文章（拉黑）
    disabledArticleIdsRequest, //管理员批量禁用文章（拉黑）
    cancelDisabledArticleRequest,  //管理员批量取消禁用文章（拉黑）
    cancelDisabledArticleIdsRequest, //管理员批量取消禁文章（拉黑）
    getLableListByNameRequest,   //查询标签信息
    disabledLableRequest, //管理员禁用标签（拉黑）
    disabledLableIdsRequest, // 管理员批量禁用标签（拉黑）
    cancelDisabledLableRequest, //管理员批量取消禁用标签（拉黑）
    cancelDisabledLableIdsRequest, //管理员批量取消禁用标签（拉黑）
    addLablesRequest, //管理员批添加标签
    editLableRequest, //管理员批修改标签
    getBackListByNameRequest, //查询公告信息
    deleteBackIdRequest,  //管理员删除公告
    deleteBackIdsRequest,  //管理员批量删除公告
    addBackRequest, //发布公告
    editBackRequest, //修改公告
    getFeedbackListAndUserNameRequest, //查询反馈信息
    activeFeedbackIdRequest, //管理员受理反馈
    activeFeedbackIdsRequest, //管理员批量受理反馈
    loginRequest, //登录
    resetRequest, //忘记密码，重置密码
    resetCodeRequest,//发送重置验证码
}