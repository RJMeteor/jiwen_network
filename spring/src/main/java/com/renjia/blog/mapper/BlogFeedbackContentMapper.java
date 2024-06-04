package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogFeedbackContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 反馈内容回复 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogFeedbackContentMapper extends BaseMapper<BlogFeedbackContent> {



    BlogFeedbackContent getFeedbackContent(@Param("feedbackId") Long feedbackId);

    Integer inserFeedbackContent(BlogFeedbackContent blogFeedbackContent);
}
