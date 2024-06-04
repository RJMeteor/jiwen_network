package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 收藏夹
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@TableName("blog_favorite")
public class BlogFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    @TableField(exist = false)
    private BlogUser user;

    /**
     * 被收藏的用户id
     */
    private Long personId;
    @TableField(exist = false)
    private BlogUser person;

    /**
     * 收藏夹类别管理id
     */
    private Long favoriteClassId;
    @TableField(exist = false)
    private BlogFavoriteClass favoriteClass;
    /**
     * 文章id
     */
    private Long articleId;
    @TableField(exist = false)
    private BlogArticle article;

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
     * 0:未删除，1:已删除
     */
    private Integer deleted;


    /**
     * "0:在推送列表里，1：不在",
     */
    private Integer active;



}
