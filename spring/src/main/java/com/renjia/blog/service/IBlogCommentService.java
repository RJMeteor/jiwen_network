package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogCommentService extends IService<BlogComment> {

    /**
     * 获取文章的分页评论
     *
     * @param articleId    文章ID
     * @param commentKeyId 父级评论ID
     * @param page         第几页
     * @param size         每页大小
     * @return
     */
    PageInfo<BlogComment> getCommentByArticleId(Long articleId, Long commentKeyId, Integer page, Integer size);

    /**
     * 添加评论
     *
     * @param blogComment 评论实体类
     * @return
     */
    BlogComment addComment(BlogComment blogComment);

    /**
     * 删除用户的评论
     *
     * @param commentId 评论的ID
     * @return
     */
    Integer deleteComment(Long commentId);

    Integer deleteComments(List<Long> ids);

    /**
     * 获取回复用户的全部评论
     *
     * @param personId 被评论的用户ID
     * @param userId   评论的用户ID
     * @return
     */
    PageInfo<BlogComment> getCommentNofit(Long personId, Long userId, Long articleId, Integer page, Integer size);

    /**
     * 除了博主自己以外的评论
     *
     * @param personId 被评论的用户ID
     * @return
     */
    PageInfo<BlogComment> getCommentByOtherPersonId(Long personId, Integer page, Integer size);

    /**
     * 获取评论博主的用户评论的列表
     *
     * @param personId 被评论的用户ID
     * @param userId   评论的用户ID
     * @return
     */
    PageInfo<BlogComment> getCommentByOtherPersonIdAndUserId(Long personId, Long userId, Integer page, Integer size);


    /**
     * 更新评论已读状态
     *
     * @param personId 被评论的用户ID
     * @param userId   评论的用户ID
     * @return
     */
    Integer updataCommentAvtiveByPersonIdAndUserId(Long personId, Long userId);
}
