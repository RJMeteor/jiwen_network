import {ref} from "vue"
// 默认用户
const defaultUser: BlogUser = {
    id: 0,
    userName: '',
    userPass: '',
    userEmail: '',
    userPhone: '',
    userSex: 0,
    userImage: '',
    userIntro: '',
    createTime: '',
    updateTime: '',
    deleted: 0
};
// 默认文章
const defaultArticle: BlogArticle = {
    personId: 0,
    articlePrivacy: 0,
    articleImage: "",
    articleContentId: 0,
    userId: 0,
    id: 0,
    articleTitle: '',
    articleLimiter: 0,
    updateTime: ''
};

// 默认文章内容映射
const defaultArticleContent: BlogArticleContent = {
    id: 0,
    articleMd: ''
};


// 默认文章标签类别
const defaultArticleLabelClass: BlogArticleLabelClass = {
    articleId: 0, articleLablesId: 0,
    id: 0,
    updateTime: ''
};

// 默认文章标签
const defaultArticleLabel: BlogArticleLabel = {
    id: 0,
    artName: '',
    updateTime: '',
};

// 默认点赞/浏览
const defaultLikeBrowse: BlogLikeBrowse = {
    articleId: 0, personId: 0, userId: 0,
    id: 0,
    likeBrowseLimiter: 0,
    updateTime: '',
    active: 0,
    stated: 0
};

// 默认博客评论
const defaultBlogComment: BlogComment = {
    articleId: 0, commentContentId: 0, personId: 0, userId: 0,
    id: 0,
    commentKeyId: 0,
    active: 0,
    updateTime: '',
    stated: 0
};

// 默认博客评论内容
const defaultBlogCommentContent: BlogCommentContent = {
    id: 0,
    commentContent: ''
};

// 默认博客关注
const defaultBlogAttention: BlogAttention = {
    id: 0,
    userId: 0,
    attentionUserId: 0,
    updateTime: '',
    active: 0,
    stated: 0
};

// 默认博客聊天记录
const defaultBlogChattingRecords: BlogChattingRecords = {
    acceptorUserId: 0, chattingRecordsContentId: 0, senderUserId: 0,
    id: 0,
    readStatus: 0,
    updateTime: '',
    active: 0
};

// 默认聊天记录关系映射
const defaultBlogChattingRecordsContent: BlogChattingRecordsContent = {
    id: 0,
    chattingRecordsContent: ''
};

// 默认收藏夹
const defaultBlogFavorite: BlogFavorite = {
    personId: 0,
    userId: 0,
    id: 0,
    favoriteClassId: 0,
    articleId: 0,
    updateTime: ''
};

// 默认收藏夹类别管理
const defaultBlogFavoriteClass: BlogFavoriteClass = {
    userId: 0,
    id: 0,
    favoriteClassName: '',
    updateTime: ''
};

// 默认反馈
const defaultBlogFeedback: BlogFeedback = {
    userId: 0,
    id: 0,
    feedbackContentId: 0,
    active: 0,
    updateTime: ''
};

// 默认反馈内容回复表
const defaultBlogFeedbackContent: BlogFeedbackContent = {
    id: 0,
    feedbackContent: '',
    feedbackReplyContent: null,
    updateTime: '',
};


// 默认后台消息通知表
const defaultBlogBackMessages: BlogBackMessages = {
    userId: 0,
    id: 0,
    backContent: '',
    updateTime: ''
};

// 默认用户消息推送状态表
const defaultBlogUserNotificationSettings: BlogUserNotificationSettings = {
    userId: 0,
    id: 0,
    privateLetterActive: 0,
    commentActive: 0,
    followActive: 0,
    collectActive: 0,
    likeActive: 0,
    updateTime: ''
};


export {
    defaultArticle,
    defaultArticleContent,
    defaultArticleLabel,
    defaultArticleLabelClass,
    defaultBlogAttention,
    defaultBlogBackMessages,
    defaultBlogChattingRecords,
    defaultBlogChattingRecordsContent,
    defaultBlogComment,
    defaultBlogCommentContent,
    defaultBlogFavorite,
    defaultBlogFavoriteClass,
    defaultBlogFeedback,
    defaultBlogFeedbackContent,
    defaultBlogUserNotificationSettings,
    defaultLikeBrowse,
    defaultUser
}

