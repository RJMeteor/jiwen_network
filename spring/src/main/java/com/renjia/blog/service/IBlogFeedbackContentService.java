package com.renjia.blog.service;

import com.renjia.blog.pojo.BlogFeedbackContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 反馈内容回复 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogFeedbackContentService extends IService<BlogFeedbackContent> {
    /**
     * 修改反馈
     *
     * @param feedbackId      反馈ID
     * @param feedbackContent 反馈内容
     * @return
     */
    public Integer editFeedback(Long feedbackId, String feedbackContent);
}
