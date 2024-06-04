package com.renjia.blog.controller;


import com.renjia.blog.service.IBlogFeedbackContentService;
import com.renjia.blog.service.IBlogFeedbackService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 反馈内容回复 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogFeedbackContent")
public class BlogFeedbackContentController {
    @Resource
    private IBlogFeedbackContentService iBlogFeedbackContentService;

    @PutMapping("editFeedback")
    public BaseResponse editFeedback(@RequestParam("feedbackId") Long feedbackId,
                                     @RequestParam("feedbackContent") String feedbackContent) {
        Integer integer = iBlogFeedbackContentService.editFeedback(feedbackId, feedbackContent);
        return integer > 0 ?
                ResultUtils.success("ok", "修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }
}

