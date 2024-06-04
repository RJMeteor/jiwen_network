package com.renjia.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 聊天记录关系映射
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Getter
@Setter
@TableName("blog_chatting_records_content")
public class BlogChattingRecordsContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 聊天内容
     */
    private String chattingRecordsContent;


}
