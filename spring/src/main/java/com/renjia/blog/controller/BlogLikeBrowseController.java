package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogFavorite;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.service.IBlogLikeBrowseService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 点赞/浏览 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogLikeBrowse")
public class BlogLikeBrowseController {
    @Resource
    private IBlogLikeBrowseService iBlogLikeBrowseService;

    @GetMapping("getLikeOtherByPersonId")
    public BaseResponse getLikeByPersonId(@RequestParam("personId") Long personId,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size
    ) {
        PageInfo<BlogLikeBrowse> favorite = iBlogLikeBrowseService.getLikeByPersonId(personId, page, size);
        return ResultUtils.success(favorite);
    }

    /**
     * active批量删除点赞
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteLikeOtherById")
    public BaseResponse deleteFavoriteArticlesOtherByIdRequest(@RequestBody List<Long> ids) {
        Integer integer = iBlogLikeBrowseService.updateLikeActive(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }


    @DeleteMapping("deleteLikeByArticleIdAndUserId")
    public BaseResponse deleteLikeByArticleIdAndUserId(@RequestParam("articlesId") Long articlesId,
                                                       @RequestParam("userId") Long userId
    ) {
        Integer integer = iBlogLikeBrowseService.deleteLikeByArticleIdAndUserIdAndpersonId(articlesId, userId);
        return integer > 0 ? ResultUtils.success("ok", "取消点赞成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消点赞失败", "");
    }

    @PutMapping("addLikeByArticleIdAndUserIdAndpersonId/{isLike}")
    public BaseResponse addLikeByArticleIdAndUserIdAndpersonId(@RequestParam("articlesId") Long articlesId,
                                                               @RequestParam("userId") Long userId,
                                                               @RequestParam("personId") Long personId,
                                                               @PathVariable("isLike") Integer isLike
    ) {
        Integer integer = iBlogLikeBrowseService.addLikeByArticleId(articlesId, userId, personId, isLike);
        return integer > 0 ? ResultUtils.success("ok", "", "")
                : ResultUtils.error(ErrorCode.ERROR, "", "");
    }

    //获取热门文章
    @GetMapping("getOhtArticle")
    public BaseResponse getOhtArticle() {
        List<BlogLikeBrowse> ohtArticle = iBlogLikeBrowseService.getOhtArticle();
        return ResultUtils.success(ohtArticle);
    }

    //获取热门话题
    @GetMapping("getOhtTopic")
    public BaseResponse getOhtTopic() {
        List<BlogLikeBrowse> ohtTopic = iBlogLikeBrowseService.getOhtTopic();
        return ResultUtils.success(ohtTopic);
    }
}

