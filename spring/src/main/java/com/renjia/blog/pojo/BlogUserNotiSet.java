package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 用户消息推送状态
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@ToString
@TableName("blog_user_noti_set")
public class BlogUserNotiSet implements Serializable {

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
     * 私信,0:不推送，1:推送
     */
    private Integer privateLetterActive;

    /**
     * 评论，0:不推送，1:推送
     */
    private Integer commentActive;

    /**
     * 关注，0:不推送，1:推送
     */
    private Integer followActive;

    /**
     * 收藏，0:不推送，1:推送
     */
    private Integer collectActive;

    /**
     * 点赞，0:不推送，1:推送
     */
    private Integer likeActive;

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

}
