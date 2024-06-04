package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogChattingRecordsContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 聊天记录关系映射 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogChattingRecordsContentMapper extends BaseMapper<BlogChattingRecordsContent> {

    Integer addContent(BlogChattingRecordsContent blogChattingRecordsContent);

    BlogChattingRecordsContent getContentId(@Param("contentId")Long contentId);
}
