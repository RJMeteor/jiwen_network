package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogBackMessages;
import com.renjia.blog.pojo.BlogFeedback;
import com.renjia.blog.service.IBlogBackMessagesService;
import com.renjia.blog.service.IBlogFeedbackService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 反馈 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogFeedback")
public class BlogFeedbackController {
    @Resource
    private IBlogFeedbackService iBlogFeedbackService;

    /**
     * 添加反馈
     *
     * @param userId          用户ID
     * @param feedbackContent 反馈内容
     * @return
     */
    @PostMapping("addFeedback")
    public BaseResponse addFeedback(@RequestParam("userId") Long userId,
                                    @RequestParam("feedbackContent") String feedbackContent) {
        Integer integer = iBlogFeedbackService.addFeedback(userId, feedbackContent);
        return integer > 0 ?
                ResultUtils.success("ok", "添加成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "添加失败", "");
    }



    /**
     * 更加用户ID查询反馈信息
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("feedbackListAndUserId")
    public BaseResponse feedbackListAndUserId(@RequestParam("userId") Long userId) {
        List<BlogFeedback> blogFeedbacks = iBlogFeedbackService.feedbackListAndUserId(userId);
        return ResultUtils.success(blogFeedbacks);
    }

    /**
     * 查询反馈信息
     *
     * @param userName  用户名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    @GetMapping("feedbackListAndUserName")
    public BaseResponse feedbackListAndUserName(@RequestParam(value="userName") String userName,
                                                @RequestParam(value = "startTime", required = false) String startTime,
                                                @RequestParam(value = "endTime", required = false) String endTime,
                                                @RequestParam("page") Integer page,
                                                @RequestParam("size") Integer size) {
        PageInfo<BlogFeedback> backlist = iBlogFeedbackService.feedbackListAndUserName(userName != null ? userName : "", startTime, endTime, page, size);
        return ResultUtils.success(backlist);
    }

    /**
     * 删除反馈
     *
     * @param feedbackId 反馈Id
     * @return
     */
    @DeleteMapping("deleteFeedbackId")
    public BaseResponse deleteFeedbackId(@RequestParam("feedbackId") Long feedbackId) {
        Integer integer = iBlogFeedbackService.deleteFeedbackId(feedbackId);
        return integer > 0 ?
                ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    /**
     * 批量删除反馈
     *
     * @param feedbackIds 反馈列表id
     * @return
     */
    @DeleteMapping("deleteFeedbackIds")
    public BaseResponse deleteFeedbackIds(@RequestBody List<Long> feedbackIds) {
        return iBlogFeedbackService.deleteFeedbackIds(feedbackIds) > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");

    }

    /**
     * 管理员受理反馈
     *
     * @param feedbackId 反馈Id
     * @return
     */
    @PutMapping("activeFeedbackId")
    public BaseResponse activeFeedbackId(@RequestParam("feedbackId") Long feedbackId) {
        Integer integer = iBlogFeedbackService.activeFeedbackId(feedbackId);
        return integer > 0 ?
                ResultUtils.success("ok", "受理成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "受理失败", "");
    }

    /**
     * 管理员取消受理反馈
     *
     * @param feedbackIds 反馈列表id
     * @return
     */
    @PutMapping("activeFeedbackIds")
    public BaseResponse activeDisabledFeedbackIds(@RequestBody List<Long> feedbackIds) {
        return iBlogFeedbackService.activeFeedbackIds(feedbackIds) > 0 ? ResultUtils.success("ok", "受理成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "受理失败", "");

    }
}

