import {axiosRequset} from "@/config/axios";
import {numberAnimationProps} from "naive-ui";

/**
 *登录
 * @param user  用户数据
 */
function loginRequest(user: BlogUser): Promise<BlogUser> {
    return axiosRequset({
        url: "/blog/login",
        method: "post",
        data: {
            ...user
        }
    })
}

/**
 *添加用户，注册
 * @param user 用户数据
 * @param code  验证码
 */
function registRequest(user: BlogUser, code: string) {
    return axiosRequset({
        url: `/blog/registry/${code}`,
        method: "post",
        data: {
            ...user
        }
    })
}

/**
 *发送注册验证码
 */
function registCodeRequest(user: BlogUser) {
    return axiosRequset({
        url: `/blog/registryCode`,
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

/**
 * 根据用户id获取用户信息
 * @param userId
 */
function getUserByIdRequest(userId: number): Promise<BlogUser> {
    return axiosRequset({
        url: `/blogUser/getUserById/${userId}`,
        method: "GET",
    })
}

/**
 * 根据用户id更新用户信息
 *
 */
function idToAllUpdate(user: BlogUser) {
    return axiosRequset({
        url: `/blogUser/idToAllUpdate`,
        method: "put",
        data: {
            ...user
        }
    })
}

/**
 * 发送修改用户信息的验证码
 * @param user
 */
function updateCodeRequest(user: BlogUser) {
    return axiosRequset({
        url: `/blogUser/updateCode`,
        method: "post",
        data: {
            ...user
        }
    })
}

/**
 * 修改用户信息
 * @param user
 * @param code
 */
function updateUserRequest(user: BlogUser, code: string) {
    return axiosRequset({
        url: `/blogUser/updateUser/${code}`,
        method: "put",
        data: {
            ...user
        }
    })
}

/**
 * 获取用户通知权限设置
 * @param notiset
 * @param userId
 */
function notisetByIdRequest(userId: number): Promise<BlogUserNotificationSettings> {
    return axiosRequset({
        url: `/blogUserNotiSet/notisetById/${userId}`,
        method: "get",
    })
}

/**
 * 修改用户通知权限设置
 * @param notiset
 * @param userId
 */
function notisetByIdToUpdateRequest(notiset: BlogUserNotificationSettings, userId: number) {
    return axiosRequset({
        url: `/blogUserNotiSet/notisetByIdToUpdate/${userId}`,
        method: "put",
        data: {
            ...notiset
        }
    })
}

/**
 * 添加文章
 * @param article
 */
function inserArticleRequest(article: BlogArticle) {
    return axiosRequset({
        url: `/blogArticle/addArticle`,
        method: "POST",
        data: {
            ...article
        }
    })
}

/**
 * 修改文章
 * @param article
 */
function updateArticleRequest(article: BlogArticle) {
    return axiosRequset({
        url: `/blogArticle/updateArticle`,
        method: "PUT",
        data: {
            ...article
        }
    })
}

//得到全部文章标签列表
function getLablesRequest(): Promise<BlogArticleLabel[]> {
    return axiosRequset({
        url: `/blogArticleLables/getLables`,
        method: "GET",
    })
}

/**
 * 通过用户id分页获取文章
 *
 * @param userId 用户id
 * @param name 文章名
 * @param page   第几页
 * @param size   每页条数
 */
function articleByUserIdAndNameRequest(userId: number, isPrivate: number, limiter: number, articlesName: string, page: number, size: number): Promise<PageInfoType<BlogArticle>> {
    return axiosRequset({
        url: `/blogArticle/articleByUserIdAndName`,
        method: "GET",
        params: {
            userId,
            limiter,
            isPrivate,
            articlesName,
            page,
            size
        }
    })
}

/**
 * 通过文章名分页获取文章
 *
 * @param articlesName 文章名
 * @param label 文章标签id
 * @param page 第几页
 * @param size   每页条数
 * @param limiter 文章类型，0文章，1话题
 * @param privacy 0公开，1私密
 */
function articlesByNameAndLabelRequest(articlesName: string, label: number, limiter, privacy, page: number, size: number): Promise<PageInfoType<BlogArticle>> {
    return axiosRequset({
        url: `/blogArticle/articlesByNameAndLabel`,
        method: "GET",
        params: {
            articlesName,
            limiter,
            privacy,
            label,
            page,
            size
        }
    })
}

/**
 * 获取我关注的人的文章
 *
 * @param articlesName 文章名
 * @param label 文章标签id
 * @param userId 用户ID
 * @param page 第几页
 * @param size   每页条数
 * @param limiter 文章类型，0文章，1话题
 * @param privacy 0公开，1私密
 */
function articlesByNameAndLabelAndUserIdRequest(articlesName: string, label: number, userId: number, limiter, privacy, page: number, size: number): Promise<PageInfoType<BlogArticle>> {
    return axiosRequset({
        url: `/blogArticle/articlesByNameAndLabelAndUserId`,
        method: "GET",
        params: {
            articlesName,
            limiter,
            userId,
            privacy,
            label,
            page,
            size
        }
    })
}

/*
 * 根据文章id集合批量删除文章
 * */
function deleteArticleByIdsRequest(articleIds: number[]) {
    return axiosRequset({
        url: `/blogArticle/deleteArticleByIds`,
        method: "delete",
        data: articleIds
    })
}

/**
 * 获取文章详细信息
 * @param userId 用户id
 * @param articlesId 文章id
 */
function getArticlesByUserIdAndArticlesIdRequest(userId: number, articlesId: number): Promise<BlogArticle> {
    return axiosRequset({
        url: `/blogArticle/getArticlesByUserIdAndArticlesId/${userId}/${articlesId}`,
        method: "GET"
    })
}

/**
 * 请求用户的文件夹
 * @param userId
 * @param isPrivate 0私密，1，1公开
 * @param page
 * @param size
 * @param favoriteName  收藏夹名
 */
function getFavoriteClassByUseridRequest(userId: number, isPrivate: number, favoriteName: string, page: number, size: number): Promise<PageInfoType<BlogFavoriteClass>> {
    return axiosRequset({
        url: `/blogFavoriteClass/getFavoriteClassByUserid`,
        method: "GET",
        params: {
            isPrivate,
            favoriteName,
            userId,
            page,
            size
        }

    })
}

/**
 * 添加收藏文件夹
 * @param favoriteName 收藏夹名
 * @param userId 用户id
 */
function addFavoriteClassByUserId(favoriteName: string, userId: number, isPrivate: number) {
    return axiosRequset({
        url: `/blogFavoriteClass/addFavoriteClassByUserId`,
        method: "PUT",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            isPrivate,
            favoriteName,
            userId
        }
    })
}

/**
 * 添加用户收藏的文章
 * @param userId 我ID
 * @param personId 文章的作者ID
 * @param favoriteClassId 收藏夹名ID
 * @param articleId 文章ID
 */
function addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest(userId: number, personId: number,
                                                             favoriteClassId: number, articleId: number) {
    return axiosRequset({
        url: `/blogFavorite/addFavoriteByUserIdAndArticleIdAndFavoriteId`,
        method: "PUT",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            favoriteClassId,
            personId,
            userId,
            articleId
        }
    })
}

/**
 * 批量删除收藏夹
 * @param ids
 */
function deleteFavoriteClassByIdRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogFavoriteClass/deleteFavoriteClassById`,
        method: "delete",
        data: ids
    })
}

/**
 * 根据文章ID删除用户的收藏文章
 * @param userId
 * @param articleId
 * @return
 */
function deleteFavoriteByUserIdAndArticleIdRequest(userId: number, articleId: number) {
    return axiosRequset({
        url: `/blogFavorite/deleteFavoriteByUserIdAndArticleId`,
        method: "DELETE",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            userId,
            articleId
        }
    })
}

/**
 * 根据文章ID删除用户的点赞
 * @param userId
 * @param articleId
 * @return
 */
function deleteLikeByArticleIdAndUserIdRequest(userId: number, articlesId: number) {
    return axiosRequset({
        url: `/blogLikeBrowse/deleteLikeByArticleIdAndUserId`,
        method: "DELETE",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            userId,
            articlesId
        }
    })
}

/**
 * 添加用户点赞的文章
 * @param userId 我ID
 * @param personId 文章的作者ID
 * @param articleId 文章ID
 * @param isLike  0:点赞，1:浏览
 */
function addLikeByArticleIdAndUserIdAndpersonIdRequest(userId: number, personId: number, articlesId: number, isLike: number) {
    return axiosRequset({
        url: `/blogLikeBrowse/addLikeByArticleIdAndUserIdAndpersonId/${isLike}`,
        method: "PUT",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            personId,
            userId,
            articlesId
        }
    })
}

/**
 * 获取文章的评论
 * @param userId
 */
function getCommentByArticleIdRequest(articleId: number, commentKeyId: number, page: number, size: number): Promise<PageInfoType<BlogComment>> {
    return axiosRequset({
        url: `/blogComment/getCommentByArticleId`,
        method: "GET",
        params: {
            commentKeyId,
            articleId,
            page,
            size
        }
    })
}

/**
 * 发送评论
 * @param comment
 */
function addCommentRequst(comment: BlogComment): Promise<BlogComment> {
    return axiosRequset({
        url: `/blogComment/addComment`,
        method: "PUT",
        data: {
            ...comment
        }
    })
}


/**
 * 删除用户的评论
 * @param comment
 */
function deleteCommentRequst(commentId: number): Promise<BlogComment> {
    return axiosRequset({
        url: `/blogComment/deleteComment`,
        method: "delete",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            commentId
        }
    })
}


/**
 * 获取用户收藏夹的收藏内容
 * @param userId
 * @param articleName
 * @param favoriteClassId 0表示全部请求
 * @param isPrivate
 * @param page
 * @param size
 */
function getFavoriteArticleByUserIdRequest(userId: number, articleName: string, favoriteClassId: number, isPrivate: number, page: number, size: number): Promise<PageInfoType<BlogFavorite>> {
    return axiosRequset({
        url: `/blogFavorite/getFavoriteArticleByUserId`,
        method: "GET",
        params: {
            userId, articleName, favoriteClassId, isPrivate, page, size
        }
    })
}


/**
 * 批量删除收藏内容
 * @param favoriteId
 */
function deleteFavoriteArticleByIdRequest(favoriteId: number[]) {
    return axiosRequset({
        url: `/blogFavorite/deleteFavoriteArticleById`,
        method: "DELETE",
        data: favoriteId
    })
}

/**
 * 获取排除自身的收藏，查看别人收藏了你那些内容
 * @param userId
 * @param page
 * @param size
 * @return
 */
function getFavoritesByPersonIdRequest(personId: number, page: number, size: number): Promise<PageInfoType<BlogFavorite>> {
    return axiosRequset({
        url: `/blogFavorite/getFavoritesByPersonId`,
        method: "GET",
        params: {
            personId, page, size
        }
    })
}

/**
 * active批量删除收藏夹从推送列表中剔除）
 * @param ids
 */
function deleteFavoriteArticlesOtherByIdRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogFavorite/deleteFavoriteArticlesOtherById`,
        method: "delete",
        data: ids
    })
}


/**
 * 获取排除自身的点赞，查看别人收藏了你那些内容
 * @param userId
 * @param page
 * @param size
 * @return
 */
function getLikeOtherByPersonIdRequest(personId: number, page: number, size: number): Promise<PageInfoType<BlogFavorite>> {
    return axiosRequset({
        url: `/blogLikeBrowse/getLikeOtherByPersonId`,
        method: "GET",
        params: {
            personId, page, size
        }
    })
}

/**
 * active批量删除点赞（从推送列表中剔除）
 * @param ids
 */
function deleteLikeOtherByIdRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogLikeBrowse/deleteLikeOtherById`,
        method: "delete",
        data: ids
    })
}


/**
 * 获取除自己外关注在消息列表里的
 * @param userId
 * @param page
 * @param size
 * @return
 */
function getAttentionOtherByPersonIdRequest(personId: number, page: number, size: number): Promise<PageInfoType<BlogFavorite>> {
    return axiosRequset({
        url: `/blogAttention/getAttentionOtherByPersonId`,
        method: "GET",
        params: {
            personId, page, size
        }
    })
}

/**
 * 批量删除关注(从点赞推送列表中剔除)
 * @param ids
 */
function deleteAttentionLikeOtherByIdRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogAttention/deleteAttentionLikeOtherById`,
        method: "delete",
        data: ids
    })
}


/**
 * 获取博主自己关注
 *
 * @param childrenId
 * @param page
 * @param size
 * @return
 */
function getAttentionsRequest(childrenId: number, page: number, size: number): Promise<PageInfoType<BlogAttention>> {
    return axiosRequset({
        url: `/blogAttention/getAttentions`,
        method: "GET",
        params: {
            childrenId, page, size
        }
    })
}

/**
 * 获取博主自己粉丝
 * @param personId
 * @param page
 * @param size
 * @return
 */
function getFensByPersonIdRequest(personId: number, page: number, size: number): Promise<PageInfoType<BlogAttention>> {
    return axiosRequset({
        url: `/blogAttention/getFens`,
        method: "GET",
        params: {
            personId, page, size
        }
    })
}

/**
 * 关注用户
 * @param personId
 * @param userId
 */
function addAttentionRequest(personId: number, userId: number) {
    return axiosRequset({
        url: `/blogAttention/addAttention`,
        method: "PUT",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            personId, userId
        }
    })
}

/**
 * 取消用户关注
 * @param personId
 * @param userId
 */
function deleteAttentionRequest(personId: number, userId: number) {
    return axiosRequset({
        url: `/blogAttention/deleteAttention`,
        method: "delete",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            personId, userId
        }
    })
}

/**
 * 批量取消用户关注
 * @param personId
 * @param userId
 */
function deleteAttentionsRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogAttention/deleteAttentions`,
        method: "delete",
        data: ids
    })
}

/**
 * 根据文章ID获取用户的全部评论
 *
 * @param personId
 * @param userId
 * @return
 */
function getCommentNofitRequest(personId: number, userId: number, articleId: number, page: number, size: number) {
    return axiosRequset({
        url: `/blogComment/getCommentNofit`,
        method: "get",
        params: {
            personId, userId, articleId, page, size
        }
    })
}

/**
 * 除了博主自己以外的评论
 *
 * @param personId
 * @return
 */
function getCommentByOtherPersonIdRequest(personId: number, page: number, size: number) {
    return axiosRequset({
        url: `/blogComment/getCommentByOtherPersonId`,
        method: "get",
        params: {
            personId, page, size
        }
    })
}

/**
 * 获取评论博主的用户评论的列表
 *
 * @param personId
 * @param userId
 * @return
 */
function getCommentByOtherPersonIdAndUserIdRequest(personId: number, userId: number, page: number, size: number) {
    return axiosRequset({
        url: `/blogComment/getCommentByOtherPersonIdAndUserId`,
        method: "get",
        params: {
            personId, userId, page, size
        }
    })
}

/**
 * 批量更新
 * @param ids
 */
function deleteCommentsRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogComment/deleteComments`,
        method: "delete",
        data: ids
    })
}


/**
 * 添加聊天
 * @param BlogChattingRecords
 */
function chatRequest(chatings: BlogChattingRecords) {
    return axiosRequset({
        url: `/blogChattingRecords/chat`,
        method: "POST",
        data: {
            ...chatings
        }
    })
}

/**
 * 获取正在聊天的用户私信
 * @param personUserId
 * @param sendUserId
 * @param page
 * @param size
 */
function getChatRequest(personUserId: number, sendUserId: number, page: number, size: number) {
    return axiosRequset({
        url: `/blogChattingRecords/getChat`,
        method: "get",
        params: {
            personUserId, sendUserId, page, size
        }
    })
}

/**
 * 批量更新
 * @param ids
 */
function deleteChatsRequest(ids: number[]) {
    return axiosRequset({
        url: `/blogChattingRecords/deleteChats`,
        method: "delete",
        data: ids
    })
}

/**
 * 除了博主自己以外的私信
 *
 * @param personId
 * @return
 */
function getChatByOtherPersonIdRequest(personId: number, page: number, size: number) {
    return axiosRequset({
        url: `/blogChattingRecords/getChatByOtherPersonId`,
        method: "get",
        params: {
            personId, page, size
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
                                  page: number,
                                  size: number): Promise<PageInfoType<BlogBackMessages>> {
    return axiosRequset({
        url: "/blogBackMessages/backList",
        method: "get",
        params: {
            backName,
            page,
            size
        }
    })
}

/**
 * 根据用户ID查询反馈信息
 *
 * @param userId 用户ID
 * @return
 */
function getFeedbackListAndUserIdRequest(userId: number): Promise<BlogFeedback[]> {
    return axiosRequset({
        url: "/blogFeedback/feedbackListAndUserId",
        method: "get",
        params: {
            userId
        }
    })
}

/**
 * 提交反馈
 *
 * @param userId          用户ID
 * @param feedbackContent 反馈内容
 * @return
 */
function addFeedbackRequest(userId: number, feedbackContent: string): Promise<String> {
    return axiosRequset({
        url: "/blogFeedback/addFeedback",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        method: "post",
        data: {
            userId,
            feedbackContent
        }
    })
}


/**
 * 修改反馈
 *
 * @param feedbackId          反馈内容ID
 * @param feedbackContent 反馈内容
 * @return
 */
function editFeedbackRequest(feedbackId: number, feedbackContent: string): Promise<String> {
    return axiosRequset({
        url: "/blogFeedbackContent/editFeedback",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        method: "put",
        data: {
            feedbackId,
            feedbackContent
        }
    })
}

/**
 * 删除反馈
 *
 * @param feedbackId 反馈Id
 * @return
 */
function deleteFeedbackIdRequest(feedbackId: number): Promise<String> {
    return axiosRequset({
        url: "/blogFeedback/deleteFeedbackId",
        method: "DELETE",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        params: {
            feedbackId,
        }
    })
}


/**
 * 批量删除反馈
 *
 * @param feedbackIds 反馈列表id
 * @return
 */
function deleteFeedbackIdsRequest(feedbackIds: number[]): Promise<PageInfoType<BlogBackMessages>> {
    return axiosRequset({
        url: "/blogFeedback/deleteFeedbackIds",
        method: "delete",
        data: feedbackIds
    })
}


//获取热门文章
function getOhtArticleRequest(): Promise<BlogArticle[]> {
    return axiosRequset({
        url: "/blogLikeBrowse/getOhtArticle",
        method: "get",
    })
}

//获取热门话题
function getOhtTopicRequest(): Promise<BlogArticle[]> {
    return axiosRequset({
        url: "/blogLikeBrowse/getOhtTopic",
        method: "get",
    })
}

//获取热门话题
function getOhtUserRequest(): Promise<BlogAttention[]> {
    return axiosRequset({
        url: "/blogAttention/getOhtUser",
        method: "get",
    })
}


/**
 * 更新评论已读状态
 *
 * @param personId 被评论的用户ID
 * @param userId 评论的用户ID
 * @return
 */
function updataCommentAvtiveByPersonIdAndUserIdRequest(personId:number,userId:number) {
    return axiosRequset({
        url: `/blogComment/updataCommentAvtiveByPersonIdAndUserId`,
        method: "PUT",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            personId,
            userId
        }
    })
}


/**
 * 更新评论已读状态
 *
 * @param personId 被评论的用户ID
 * @param userId 评论的用户ID
 * @return
 */
function updataChatAvtiveByPersonIdAndUserIdRequest(personUserId:number,sendUserId:number) {
    return axiosRequset({
        url: `/blogChattingRecords/updataChatAvtiveByPersonIdAndUserId`,
        method: "PUT",
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: {
            personUserId,
            sendUserId
        }
    })
}

export {
    updataChatAvtiveByPersonIdAndUserIdRequest, //更新聊天已读状态
    updataCommentAvtiveByPersonIdAndUserIdRequest, //更新评论已读状态
    getOhtArticleRequest, //获取热门文章
    getOhtTopicRequest, //获取热门话题
    getOhtUserRequest, //获取热门话题
    loginRequest, //登录
    registRequest, //添加用户(注册)
    resetCodeRequest, //发送重置验证码
    resetRequest, //忘记密码，重置密码
    registCodeRequest, //发送注册验证码
    idToAllUpdate, //根据用户id更新用户信息
    updateCodeRequest, //发送修改用户信息的验证码
    updateUserRequest, // 修改用户信息
    notisetByIdRequest, //获取用户通知权限设置
    notisetByIdToUpdateRequest, //修改用户通知权限设置
    inserArticleRequest, //添加文章
    updateArticleRequest, //修改文章
    getLablesRequest, //得到全部文章标签列表
    getUserByIdRequest,  // 根据用户id获取用户信息
    articleByUserIdAndNameRequest, //通过用户id分页获取文章(个人主页)
    articlesByNameAndLabelRequest, //通过文章名分页获取文章(首页)
    articlesByNameAndLabelAndUserIdRequest,
    deleteArticleByIdsRequest, //根据文章id集合批量删除文章
    getArticlesByUserIdAndArticlesIdRequest, //获取文章详细信息
    getFavoriteClassByUseridRequest, //请求用户的文件夹
    addFavoriteClassByUserId, //添加收藏文件夹
    addFavoriteByUserIdAndArticleIdAndFavoriteIdRequest, //添加用户收藏的文章
    deleteFavoriteClassByIdRequest,  //批量删除收藏夹
    deleteFavoriteByUserIdAndArticleIdRequest, //根据文章ID删除用户的收藏文章
    getCommentByArticleIdRequest, //获取文章的评论
    addLikeByArticleIdAndUserIdAndpersonIdRequest, // 添加用户点赞的文章
    deleteLikeByArticleIdAndUserIdRequest, //根据文章ID删除用户的点赞
    addCommentRequst, //发送评论
    deleteCommentRequst, //删除用户的评论
    getFavoriteArticleByUserIdRequest, //获取用户收藏夹的收藏内容
    deleteFavoriteArticleByIdRequest, //批量删除收藏内容
    getFavoritesByPersonIdRequest,  //获取博主自己收藏内容
    deleteFavoriteArticlesOtherByIdRequest, //active批量删除收藏夹从推送列表中剔除
    getLikeOtherByPersonIdRequest, //获取排除自身的点赞，查看别人收藏了你那些内容(推送列表1)
    deleteLikeOtherByIdRequest, //active批量删除点赞（从推送列表中剔除）
    getAttentionOtherByPersonIdRequest, //获取除自己外关注在消息列表里的
    deleteAttentionLikeOtherByIdRequest, //批量删除点赞(从点赞推送列表中剔除)
    getAttentionsRequest, //获取博主自己关注
    getFensByPersonIdRequest, //获取博主自己粉丝
    addAttentionRequest, //关注用户
    deleteAttentionRequest, //取消用户关注
    deleteAttentionsRequest, //批量取消用户关注
    getCommentNofitRequest, //根据文章ID获取用户的全部评论
    getCommentByOtherPersonIdRequest, //除了博主自己以外的评论
    getCommentByOtherPersonIdAndUserIdRequest,  //获取评论博主的用户评论的列表(在管理文章评论页面)
    deleteCommentsRequest, //批量更新（从推送列表中剔除）
    chatRequest, //添加聊天
    getChatRequest, //获取正在聊天的用户私信
    deleteChatsRequest, //批量更新（从推送列表中剔除）
    getChatByOtherPersonIdRequest,  //除了博主自己以外的私信
    getBackListByNameRequest, //查询公告信息
    getFeedbackListAndUserIdRequest, //根据用户ID查询反馈信息
    addFeedbackRequest,  //提交反馈
    editFeedbackRequest,  //修改反馈
    deleteFeedbackIdRequest, //删除反馈
    deleteFeedbackIdsRequest, //批量删除反馈
}