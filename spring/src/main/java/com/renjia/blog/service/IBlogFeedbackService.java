package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogBackMessages;
import com.renjia.blog.pojo.BlogFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 反馈 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogFeedbackService extends IService<BlogFeedback> {
    /**
     * 添加反馈
     *
     * @param userId          用户ID
     * @param feedbackContent 反馈内容
     * @return
     */
    public Integer addFeedback(Long userId, String feedbackContent);



    /**
     * 更加用户ID查询反馈信息
     *
     * @param userId 用户ID
     * @return
     */
    public List<BlogFeedback> feedbackListAndUserId(@RequestParam("userId") Long userId);

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
    public PageInfo<BlogFeedback> feedbackListAndUserName(String userName,
                                                          String startTime,
                                                          String endTime,
                                                          Integer page,
                                                          Integer size);

    /**
     * 删除反馈
     *
     * @param feedbackId 反馈Id
     * @return
     */
    public Integer deleteFeedbackId(Long feedbackId);

    /**
     * 管理员批量删除公告
     *
     * @param feedbackIds 反馈列表id
     * @return
     */
    public Integer deleteFeedbackIds(List<Long> feedbackIds);

    /**
     * 管理员受理反馈
     *
     * @param feedbackId 反馈Id
     * @return
     */
    public Integer activeFeedbackId(Long feedbackId);

    /**
     * 管理员取消受理反馈
     *
     * @param feedbackIds 反馈列表id
     * @return
     */
    public Integer activeFeedbackIds(@RequestBody List<Long> feedbackIds);
}
