package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 评论内容映射
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@ToString
@TableName("blog_comment_content")
public class BlogCommentContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String commentContent;

    @TableField(exist = false)
    private Long commentId;


}
