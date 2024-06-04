package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.pojo.BlogBackMessages;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 后台消息通知 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogBackMessagesService extends IService<BlogBackMessages> {

    /**
     * 添加公告
     * @param backContent 公告内容
     * @return
     */
    public Integer addBack(String backContent);

    /**
     * 编辑公告
     * @param backId 公告ID
     * @param backName 公告名字
     * @return
     */
    public Integer editBack(Long backId, String backName);

    /**
     * 查询公告信息
     *
     * @param backName  标签名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    public PageInfo<BlogBackMessages> backList(String backName,
                                               String startTime,
                                               String endTime,
                                               Integer page,
                                               Integer size);

    /**
     * 管理员删除公告
     *
     * @param backId 公告Id
     * @return
     */
    public Integer deleteBackId(Long backId);

    /**
     * 管理员批量删除公告
     *
     * @param backIds 公告列表id
     * @return
     */
    public Integer disabledBackIds(List<Long> backIds);
}
