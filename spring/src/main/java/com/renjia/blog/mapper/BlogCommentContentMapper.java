package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogCommentContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 评论内容映射 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogCommentContentMapper extends BaseMapper<BlogCommentContent> {

    BlogCommentContent getCommentContentById(@Param("commentContentId") Long commentContentId);
    Integer addCommentContent(BlogCommentContent commentContent);
}
