package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@TableName("blog_user")
public class BlogUser implements Serializable {

//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

//    @TableField(exist = false)
//    private Long idp;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * QQ邮箱
     */
    private String userEmail;

    /**
     * 电话号码
     */
    private String userPhone;

    /**
     * 0:女，1:男
     */
    private Integer userSex;

    /**
     * 头像地址
     */
    private String userImage;

    /**
     * 个人简介
     */
    private String userIntro;

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
     * 个人简介
     */
    @TableField(exist = false)
    private String token;

    /**
     * 0:未删除，1:已删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<Long> attentionUserId;

    @TableField(exist = false)
    private List<Long> fensUserId;
}
