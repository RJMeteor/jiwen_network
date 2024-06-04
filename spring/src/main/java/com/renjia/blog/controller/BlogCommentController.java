package com.renjia.blog.controller;


import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogComment;
import com.renjia.blog.service.IBlogCommentService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogComment")
public class BlogCommentController {

    @Resource
    private IBlogCommentService iBlogCommentService;

    /**
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("getCommentByArticleId")
    public BaseResponse getCommentByArticleId(@RequestParam("articleId") Long articleId,
                                              @RequestParam("commentKeyId") Long commentKeyId,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size) {
        PageInfo<BlogComment> blogArticles = iBlogCommentService.getCommentByArticleId(articleId, commentKeyId, page, size);
        return ResultUtils.success(blogArticles);
    }

    @PutMapping("addComment")
    public BaseResponse addComment(@RequestBody BlogComment blogComment) {
        BlogComment blogComment1 = iBlogCommentService.addComment(blogComment);
        return ResultUtils.success(blogComment1);
    }

    /**
     * active批量删除收藏夹
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteComments")
    public BaseResponse deleteComments(@RequestBody List<Long> ids) {
        Integer integer = iBlogCommentService.deleteComments(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    @DeleteMapping("deleteComment")
    public BaseResponse deleteComment(@RequestParam("commentId") Long commentId) {
        Integer i = iBlogCommentService.deleteComment(commentId);
        return i > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    @GetMapping("getCommentNofit")
    public BaseResponse getCommentNofit(@RequestParam("personId") Long personId,
                                        @RequestParam("userId") Long userId,
                                        @RequestParam("articleId") Long articleId,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        PageInfo<BlogComment> commentNofit = iBlogCommentService.getCommentNofit(personId, userId, articleId, page, size);
        return ResultUtils.success(commentNofit);
    }


    @GetMapping("getCommentByOtherPersonId")
    public BaseResponse getCommentByOtherPersonId(@RequestParam("personId") Long personId,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        PageInfo<BlogComment> commentNofit = iBlogCommentService.getCommentByOtherPersonId(personId, page, size);
        return ResultUtils.success(commentNofit);
    }

    @GetMapping("getCommentByOtherPersonIdAndUserId")
    public BaseResponse getCommentByOtherPersonIdAndUserId(@RequestParam("personId") Long personId,
                                                           @RequestParam("userId") Long userId,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {
        PageInfo<BlogComment> commentNofit = iBlogCommentService.getCommentByOtherPersonIdAndUserId(personId, userId, page, size);
        return ResultUtils.success(commentNofit);
    }

    @PutMapping("updataCommentAvtiveByPersonIdAndUserId")
    public BaseResponse updataCommentAvtiveByPersonIdAndUserId(@RequestParam("personId") Long personId,
                                                               @RequestParam("userId") Long userId) {
        Integer i = iBlogCommentService.updataCommentAvtiveByPersonIdAndUserId(personId, userId);
        return i > 0 ? ResultUtils.success("ok")
                : ResultUtils.error(ErrorCode.ERROR);
    }


}

