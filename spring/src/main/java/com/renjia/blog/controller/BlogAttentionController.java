package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogAttention;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogAttentionService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 关注 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogAttention")
public class BlogAttentionController {

    @Resource
    private IBlogAttentionService iBlogAttentionService;

    @PutMapping("addAttention")
    public BaseResponse addAttention(@RequestParam("personId") Long personId,
                                     @RequestParam("userId") Long userId
    ) {
        Integer i = iBlogAttentionService.addAttention(personId, userId);
        return i > 0 ? ResultUtils.success("ok", "关注成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "关注失败", "");
    }

    @DeleteMapping("deleteAttention")
    public BaseResponse deleteAttention(@RequestParam("personId") Long personId,
                                        @RequestParam("userId") Long userId
    ) {
        Integer i = iBlogAttentionService.deleteAttention(personId, userId);
        return i > 0 ? ResultUtils.success("ok", "取消关注成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消关注失败", "");
    }


    @GetMapping("getAttentionOtherByPersonId")
    public BaseResponse getAttentionByPersonId(@RequestParam("personId") Long personId,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size
    ) {
        PageInfo<BlogAttention> favorite = iBlogAttentionService.getAttentionByPersonId(personId, page, size);
        return ResultUtils.success(favorite);
    }

    @GetMapping("getAttentions")
    public BaseResponse getAttentions(@RequestParam("childrenId") Long personId,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size
    ) {
        PageInfo<BlogAttention> favorite = iBlogAttentionService.getAttentions(personId, page, size);
        return ResultUtils.success(favorite);
    }

    @GetMapping("getFens")
    public BaseResponse getFens(@RequestParam("personId") Long personId,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size
    ) {
        PageInfo<BlogAttention> favorite = iBlogAttentionService.getFens(personId, page, size);
        return ResultUtils.success(favorite);
    }

    /**
     * active批量删除收藏夹
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteAttentionLikeOtherById")
    public BaseResponse deleteAttentionLikeOtherById(@RequestBody List<Long> ids) {
        Integer integer = iBlogAttentionService.updateAttentionActive(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteAttentions")
    public BaseResponse deleteAttentions(@RequestBody List<Long> ids) {
        Integer integer = iBlogAttentionService.deleteAttentions(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    @GetMapping("getOhtUser")
    public BaseResponse getOhtUser() {
        List<BlogAttention> ohtUser = iBlogAttentionService.getOhtUser();
        return ResultUtils.success(ohtUser);
    }

}

