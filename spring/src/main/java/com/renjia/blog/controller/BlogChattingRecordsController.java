package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogChattingRecords;
import com.renjia.blog.service.IBlogChattingRecordsService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 聊天记录 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogChattingRecords")
public class BlogChattingRecordsController {
    @Resource
    private IBlogChattingRecordsService iBlogChattingRecordsService;

    /**
     * @param blogChattingRecord
     * @return
     * @throws
     */
    @PostMapping("chat")
    public BaseResponse chat(@RequestBody BlogChattingRecords blogChattingRecord) throws Exception {
        BlogChattingRecords blogChattingRecords = iBlogChattingRecordsService.addChat(blogChattingRecord);
        return ResultUtils.success(blogChattingRecords);
    }


    /**
     *
     * @param personUserId
     * @param sendUserId
     * @param page
     * @param size
     * @throws Exception
     */
    @GetMapping("getChat")
    public BaseResponse getChat(@RequestParam("personUserId")Long personUserId,
                                @RequestParam("sendUserId")Long sendUserId,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) {
        PageInfo<BlogChattingRecords> chat = iBlogChattingRecordsService.getChat(personUserId, sendUserId, page, size);
        return ResultUtils.success(chat);
    }

    @GetMapping("getChatByOtherPersonId")
    public BaseResponse getChatByOtherPersonId(@RequestParam("personId") Long personId,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        PageInfo<BlogChattingRecords> commentNofit = iBlogChattingRecordsService.getChatByOtherPersonId(personId, page, size);
        return ResultUtils.success(commentNofit);
    }

    /**
     * active批量聊天
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteChats")
    public BaseResponse deleteChats(@RequestBody List<Long> ids) {
        Integer integer = iBlogChattingRecordsService.deleteChats(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    /**
     *
     * @param personUserId
     * @param sendUserId
     * @throws Exception
     */
    @PutMapping("updataChatAvtiveByPersonIdAndUserId")
    public BaseResponse updataChatAvtiveByPersonIdAndUserId(@RequestParam("personUserId")Long personUserId,
                                @RequestParam("sendUserId")Long sendUserId) {
        Integer i = iBlogChattingRecordsService.updataChatAvtiveByPersonIdAndUserId(personUserId, sendUserId);
        return i > 0 ? ResultUtils.success("ok")
                : ResultUtils.error(ErrorCode.ERROR);
    }
}

