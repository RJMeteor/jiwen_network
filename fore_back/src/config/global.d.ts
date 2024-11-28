//分页主页type
interface PageType{
    pageNum: number,
    pageSize: number,
    total: number
}

// 用户
interface BlogUser {
    id: number; // 用户ID
    userName: string; // 用户名
    userPass: string; // 密码
    userEmail: string; // QQ邮箱
    userPhone: string; // 电话号码
    userSex: number; // 性别 0:女，1:男
    userImage: string; // 头像地址
    userIntro: string; // 个人简介
    createTime: string; // 创建时间
    updateTime: string; // 更新时间
    deleted: number; // 是否删除 0:未删除，1:已删除
    attentionUserId?: number[]
    fensUserId?: number[]
}

// 文章
interface BlogArticle {
    id: number; // 文章ID
    userId: number; // 发布文章用户ID
    personId: number //被收藏的用户id
    articleLableClasss?: BlogArticleLabelClass[]
    user?: BlogUser; // 发布文章用户ID
    articleImage: string; //文章头像
    articleTitle: string; // 文章标题
    articleLimiter: number; // 文章类型 0:文章，1:话题
    articleContentId: number; // 文章内容ID
    articleContent?: BlogArticleContent;
    updateTime: string; // 更新时间
    articlePrivacy: number //0:公开，1:私密
    commentCount?: number // 评论数
    browseCount?: number // 浏览数
    likeUserId?: number[]
    favoriteUserId?: number[]
}

// 文章内容映射
interface BlogArticleContent {  
    id: number; // 映射ID
    articleId?: number //文章id;
    articleMd: string; // 文章内容
}

// 文章标签类别
interface BlogArticleLabelClass {
    id: number;
    articleId: number;// 文章ID
    article?: BlogArticle; // 文章ID
    articleLablesId: number;  // 文章标签ID
    articleLables?: BlogArticleLabel;
    updateTime: string; // 更新时间
}

// 文章标签
interface BlogArticleLabel {
    id: number;
    artName: string; // 文章标签名
    updateTime: string; // 更新时间
}

// 点赞/浏览
interface BlogLikeBrowse {
    id: number;
    userId: number;// 点赞/浏览的用户id
    user?: BlogUser;
    personId: number;// 被点赞/浏览的用户id
    person?: BlogUser;
    articleId: number;// 文章id
    article?: BlogArticle;
    likeBrowseLimiter: number; // 0:点赞，1:浏览
    updateTime: string; // 更新时间
    active: number; // 0:在推送列表里，1：不在
    stated: number; // 0:未读，1:已读
}

// 博客评论
interface BlogComment {
    id: number; // 评论id
    userId: number;// 评论用户id
    user?: BlogUser;
    commentKeyId: number; // 评论的id
    personId: number;// 父级评论用户id
    person?: BlogUser;
    articleId: number;
    article?: BlogArticle; // 文章id
    commentContentId: number;// 评论内容id
    commentContent?: BlogCommentContent;
    active: number; // 0:在推送列表里，1：不在
    comments?: BlogComment[]
    updateTime: string; // 更新时间
    isChildren?: number //是否有子元素
    stated: number; // 0:未读，1:已读
}

// 博客评论内容
interface BlogCommentContent {
    id: number; // 评论内容id
    commentContent: string; // 评论内容
    commentId?: number  //评论ID
}

// 博客关注
interface BlogAttention {
    id: number; // 关注id
    userId: number; // 用户id
    attentionUserId: number; // 关注的用户id
    updateTime: string; // 更新时间
    active: number; // 0:在推送列表里，1：不在
    stated: number; // 0:未读，1:已读
}

// 博客聊天记录
interface BlogChattingRecords {
    id: number; // 聊天记录id
    senderUserId: number;  // 发送方用户id
    senderUser?: BlogUser;
    acceptorUserId: number;// 接收方用户id
    acceptorUser?: BlogUser;
    chattingRecordsContentId: number; // 聊天内容id
    chattingRecordsContent?: BlogChattingRecordsContent;
    readStatus: number; // 0:未读，1:已读
    updateTime: string; // 更新时间
    active: number; // 0:在推送列表里，1：不在
}

// 聊天记录关系映射
interface BlogChattingRecordsContent {
    id: number; // 主键
    chattingRecordsContent: string; // 聊天内容
}

// 收藏夹
interface BlogFavorite {
    id: number; // 主键
    userId: number;// 用户id
    user?: BlogUser;
    personId: number; //被收藏的用户id
    person?: BlogUser;
    favoriteClassId: number; // 收藏夹类别管理id
    favoriteClass?: BlogFavoriteClass;
    articleId: number; // 文章id
    article?: BlogArticle;
    active?: number;
    updateTime: string; // 创建时间
}

// 收藏夹类别管理
interface BlogFavoriteClass {
    id: number; // 主键
    userId: number;// 用户id
    user?: BlogUser;
    favoriteClassName: string; // 收藏夹名字
    favoriteClassPrivacy?: number; //'0:公开，1:私密'
    updateTime: string; // 创建时间
}

// 反馈
interface BlogFeedback {
    id: number; // 主键
    userId: number; // 用户id
    user?: BlogUser;
    feedbackContentId: number; // 反馈内容ID
    feedbackContent?: BlogFeedbackContent;
    active: number; // 0:未受理，1:已受理
    updateTime: string; // 创建时间
}

// 反馈内容回复表
interface BlogFeedbackContent {
    id: number; // 主键
    feedbackContent: string; // 反馈内容
    feedbackReplyContent?: string | null; // 回复内容
    updateTime: string; // 创建时间
}

// 后台消息通知表
interface BlogBackMessages {
    id: number; // 主键
    userId: number; // 用户id
    user?: BlogUser;
    backContent: string; // 内容
    updateTime: string; // 创建时间
}

// 用户消息推送状态表
interface BlogUserNotificationSettings {
    id: number; // 主键
    userId: number; // 用户id
    user?: BlogUser;
    privateLetterActive: number; // 私信，0:不推送，1:推送
    commentActive: number; // 评论，0:不推送，1:推送
    followActive: number; // 关注，0:不推送，1:推送
    collectActive: number; // 收藏，0:不推送，1:推送
    likeActive: number; // 点赞，0:不推送，1:推送
    updateTime: string; // 创建时间
}

interface PageInfoType<T> {
    list?: T[]
    total: number,
    pageNum: number,
    pageSize: number,
}

