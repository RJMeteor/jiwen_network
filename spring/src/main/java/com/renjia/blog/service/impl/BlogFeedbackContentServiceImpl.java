package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.renjia.blog.pojo.BlogFeedback;
import com.renjia.blog.pojo.BlogFeedbackContent;
import com.renjia.blog.mapper.BlogFeedbackContentMapper;
import com.renjia.blog.service.IBlogFeedbackContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 反馈内容回复 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogFeedbackContentServiceImpl extends ServiceImpl<BlogFeedbackContentMapper, BlogFeedbackContent> implements IBlogFeedbackContentService {

    @Override
    public Integer editFeedback(Long feedbackId, String feedbackContent) {
        LambdaUpdateWrapper<BlogFeedbackContent> blogFeedbackLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        blogFeedbackLambdaUpdateWrapper.eq(BlogFeedbackContent::getId, feedbackId).set(BlogFeedbackContent::getFeedbackContent, feedbackContent);
        boolean update = this.update(blogFeedbackLambdaUpdateWrapper);
        return update ? 1 : 0;
    }
}
