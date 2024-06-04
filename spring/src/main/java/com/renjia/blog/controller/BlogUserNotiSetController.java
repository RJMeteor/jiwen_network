package com.renjia.blog.controller;


import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.pojo.BlogUserNotiSet;
import com.renjia.blog.service.IBlogUserNotiSetService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 用户消息推送状态 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogUserNotiSet")
public class BlogUserNotiSetController {
    @Resource
    private IBlogUserNotiSetService iBlogUserNotiSetService;

    @GetMapping("notisetById/{userId}")
    public BaseResponse notisetByIdToSelect(@PathVariable("userId") String userId) {
        BlogUserNotiSet blogUserNotiSet = iBlogUserNotiSetService.notisetByIdToSelect(userId);
        return ResultUtils.success(blogUserNotiSet);
    }

    @PutMapping("notisetByIdToUpdate/{userId}")
    public BaseResponse notisetByIdToUpdate(@RequestBody BlogUserNotiSet blogUserNotiSet, @PathVariable("userId") String userId) {
        Integer integer = iBlogUserNotiSetService.notisetByIdToUpdate(blogUserNotiSet, userId);

        return integer > 0 ? ResultUtils.success("修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }

}

