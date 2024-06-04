package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavorite;
import com.renjia.blog.pojo.BlogFavoriteClass;
import com.renjia.blog.service.IBlogFavoriteService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收藏夹 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogFavorite")
public class BlogFavoriteController {
    @Resource
    private IBlogFavoriteService iBlogFavoriteService;

    @GetMapping("getFavoritesByPersonId")
    public BaseResponse getFavoritesByPersonId(@RequestParam("personId") Long personId,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("size") Integer size
    ) {
        PageInfo<BlogFavorite> favorite = iBlogFavoriteService.getFavoritesByPersonId(personId, page, size);
        return ResultUtils.success(favorite);
    }

    /**
     * 获取收藏夹的文章
     *
     * @param userId
     * @param articleName
     * @param favoriteClassId
     * @param isPrivate
     * @param page
     * @param size
     * @return
     */
    @GetMapping("getFavoriteArticleByUserId")
    public BaseResponse getFavoriteArticleByUserId(@RequestParam("userId") Long userId,
                                                   @RequestParam("articleName") String articleName,
                                                   @RequestParam("favoriteClassId") Long favoriteClassId,
                                                   @RequestParam("isPrivate") Integer isPrivate,
                                                   @RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size
    ) {
        BlogFavorite blogFavorite = new BlogFavorite();
        blogFavorite.setUserId(userId);
        blogFavorite.setFavoriteClassId(favoriteClassId);
        PageInfo<BlogFavorite> favorite =
                iBlogFavoriteService.getFavoriteArticleByUserId(userId,
                        articleName,
                        favoriteClassId,
                        isPrivate,
                        page,
                        size
                );
        return ResultUtils.success(favorite);
    }

    /**
     * 删除用户的收藏文章
     *
     * @param userId
     * @param articleId
     * @return
     */
    @DeleteMapping("deleteFavoriteByUserIdAndArticleId")
    public BaseResponse deleteFavoriteByUserIdAndArticleId(@RequestParam("userId") Long userId,
                                                           @RequestParam("articleId") Long articleId
    ) {
        Integer integer = iBlogFavoriteService.deleteFavoriteByUserIdAndArticleId(userId, articleId);
        return integer > 0 ? ResultUtils.success("ok", "取消收藏成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消收藏失败", "");
    }

    /**
     * active批量删除收藏夹
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteFavoriteArticlesOtherById")
    public BaseResponse deleteFavoriteArticlesOtherByIdRequest(@RequestBody List<Long> ids) {
        Integer integer = iBlogFavoriteService.updateFavoritesActive(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    /**
     * 删除用户的收藏文章
     *
     * @param userId
     * @param articleId
     * @return
     */
    @PutMapping("addFavoriteByUserIdAndArticleIdAndFavoriteId")
    public BaseResponse addFavoriteByUserIdAndArticleIdAndFavoriteId(@RequestParam("userId") Long userId,
                                                                     @RequestParam("personId") Long personId,
                                                                     @RequestParam("favoriteClassId") Long favoriteClassId,
                                                                     @RequestParam("articleId") Long articleId
    ) {
        Integer integer = iBlogFavoriteService.addFavoriteByUserIdAndArticleIdAndFavoriteId(userId, personId, favoriteClassId, articleId);
        return integer > 0 ? ResultUtils.success("ok", "收藏成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "你已收藏了", "");
    }

    /**
     * 批量删除收藏的文章
     * @param ids
     * @return
     */
    @DeleteMapping("deleteFavoriteArticleById")
    public BaseResponse deleteFavoriteArticleById(@RequestBody List<Long> ids) {
        Integer integer = iBlogFavoriteService.deleteFavoriteArticleById(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }
}

