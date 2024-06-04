package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogCommentMapper extends BaseMapper<BlogComment> {


    /**
     * 根据文章id回去文章评论数
     *
     * @param articleId
     * @return
     */
    Long getCommentCountByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据评论id获取评论
     *
     * @param commentId
     * @return
     */
    BlogComment getCommentById(@Param("commentId") Long commentId);

    /**
     * 判断是否有子评论
     *
     * @param commentId
     * @return
     */
    Integer getIsChildrenById(@Param("commentId") Long commentId);

    /**
     * 根据文章Id获取评论内容
     *
     * @param articleId
     * @param commentKeyId 上级评论
     * @return
     */
    List<BlogComment> getCommentsByArticleId(@Param("articleId") Long articleId, @Param("commentKeyId") Long commentKeyId);


    /**
     * 添加评论内容
     *
     * @param blogComment
     * @return
     */
    Integer addComment(BlogComment blogComment);

    /**
     * 删除列表的评论 set  active =1
     *
     * @param ids
     * @return
     */
    Integer updateCommentsActive(List<Long> ids);


    /**
     * 更加评论内容的id获取评论的id
     *
     * @param commentContentId
     * @return
     */
    Long getCommentIdByCommentContentId(@Param("commentContentId") Long commentContentId);


    /**
     * 获取用户的全部评论
     *
     * @param personId
     * @param userId
     * @return
     */
    List<BlogComment> getCommentNofit(@Param("personId") Long personId, @Param("userId") Long userId,@Param("articleId") Long articleId);

    /**
     * 除了博主自己以外的评论
     *
     * @param personId
     * @return
     */
    List<BlogComment> getCommentByOtherPersonId(@Param("personId") Long personId);

    /**
     * 获取评论博主的用户评论的列表
     *
     * @param personId
     * @param userId
     * @return
     */
    List<BlogComment> getCommentByOtherPersonIdAndUserId(@Param("personId") Long personId, @Param("userId") Long userId);
}
