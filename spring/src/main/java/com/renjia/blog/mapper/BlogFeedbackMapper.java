package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 反馈 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogFeedbackMapper extends BaseMapper<BlogFeedback> {

    Long getFeedbackId(@Param("feedbackContentId")Long feedbackContentId);

    Integer inserFeedback(BlogFeedback blogFeedback);
    List<BlogFeedback> getFeedbackAndUserNameAndTime(@Param("userName")String userName,
                                                     @Param("startTime") Date startTime,
                                                     @Param("endTime")Date endTime);
    List<BlogFeedback> getFeedbackAndUserId(@Param("userId")Long userId);
}
