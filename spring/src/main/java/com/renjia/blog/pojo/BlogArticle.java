package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@TableName("blog_article")
public class    BlogArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布文章用户id
     */
    private Long userId;
    @TableField(exist = false)
    private BlogUser user;

    @TableField(exist = false)
    private ArrayList<BlogArticleLableClass> articleLableClasss;

    /**
     * 文章封面地址
     */
    private String articleImage;
    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 0:文章，1:话题
     */
        private Integer articleLimiter;

    /**
     * 文章内容ID
     */
    private Long articleContentId;
    @TableField(exist = false)
    private BlogArticleContent articleContent;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 0:公开，1:私密
     */
    private Integer articlePrivacy;
    /**
     * 0:未删除，1:已删除
     */
    private Integer deleted;

    /*
     * 评论数
     * */
    @TableField(exist = false)
    private Long commentCount;


    /*
     * 浏览数
     * */
    @TableField(exist = false)
    private Long browseCount;

    /*
     * 点赞用户id
     * */
    @TableField(exist = false)
    private List<Long> likeUserId;

    /*
     * 收藏用户id
     * */
    @TableField(exist = false)
    private List<Long>  favoriteUserId;

//    /*
//     * 0：未点赞，1：已点赞
//     * */
//    @TableField(exist = false)
//    private Integer isLike;
//
//    /*
//     * 0：未收藏，1：已收藏
//     * */
//    @TableField(exist = false)
//    private Integer isFavorite;
}
