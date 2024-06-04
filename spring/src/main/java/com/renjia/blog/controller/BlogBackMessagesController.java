package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.pojo.BlogBackMessages;
import com.renjia.blog.service.IBlogArticleLablesService;
import com.renjia.blog.service.IBlogBackMessagesService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台消息通知 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogBackMessages")
public class BlogBackMessagesController {
    @Resource
    private IBlogBackMessagesService iBlogBackMessagesService;

    /**
     * 发布公告
     * @param backContent  公告内容
     * @return
     */
    @PostMapping("addBack")
    public BaseResponse addBack(@RequestParam("backContent") String backContent) {
        Integer integer = iBlogBackMessagesService.addBack(backContent);
        return integer > 0 ?
                ResultUtils.success("ok", "添加成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "添加失败", "");
    }

    /**
     * 修改公告
     * @param backId 公告ID
     * @param backName 公告内容
     * @return
     */
    @PutMapping("editBack")
    public BaseResponse editBack(@RequestParam("backId") Long backId, @RequestParam("backName") String backName) {
        Integer integer = iBlogBackMessagesService.editBack(backId, backName);
        return integer > 0 ?
                ResultUtils.success("ok", "修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }

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
    @GetMapping("backList")
    public BaseResponse backList(@RequestParam("backName") String backName,
                                 @RequestParam(value = "startTime",required = false) String startTime,
                                 @RequestParam(value = "endTime",required = false) String endTime,
                                 @RequestParam("page") Integer page,
                                 @RequestParam("size") Integer size) {
        PageInfo<BlogBackMessages> backlist = iBlogBackMessagesService.backList(backName != null ? backName : "", startTime, endTime, page, size);
        return ResultUtils.success(backlist);
    }

    /**
     * 管理员删除公告
     *
     * @param backId 公告Id
     * @return
     */
    @DeleteMapping("deleteBackId")
    public BaseResponse deleteBackId(@RequestParam("backId") Long backId) {
        Integer integer = iBlogBackMessagesService.deleteBackId(backId);
        return integer > 0 ?
                ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    /**
     * 管理员批量删除公告
     *
     * @param backIds 公告列表id
     * @return
     */
    @DeleteMapping("deleteBackIds")
    public BaseResponse deleteBackIds(@RequestBody List<Long> backIds) {
        return iBlogBackMessagesService.disabledBackIds(backIds) > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");

    }
}

