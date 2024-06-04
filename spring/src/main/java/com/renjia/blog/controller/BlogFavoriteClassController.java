package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavoriteClass;
import com.renjia.blog.service.IBlogFavoriteClassService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收藏夹类别管理 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogFavoriteClass")
public class BlogFavoriteClassController {
    @Resource
    private IBlogFavoriteClassService iBlogFavoriteClassService;

    /**
     * 获取收藏夹
     *
     * @param userId
     * @param page
     * @param size
     * @param isPrivate
     * @return
     */
    @GetMapping("getFavoriteClassByUserid")
    public BaseResponse getFavoriteClassByUserid(@RequestParam("userId") Long userId,
                                                 @RequestParam("isPrivate") Integer isPrivate,
                                                 @RequestParam("favoriteName") String favoriteName,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size
    ) {
        PageInfo<BlogFavoriteClass> favorite = iBlogFavoriteClassService.getFavoriteClassByUserid(userId,
                isPrivate,
                favoriteName, page, size);
        return ResultUtils.success(favorite);
    }

    /**
     * 添加收藏夹
     *
     * @param userId
     * @param favoriteName
     * @return
     */
    @PutMapping("addFavoriteClassByUserId")
    public BaseResponse addFavoriteClassByUserId(@RequestParam("userId") Long userId,
                                                 @RequestParam("favoriteName") String favoriteName,
                                                 @RequestParam("isPrivate") Integer isPrivate

    ) {
        Integer integer = iBlogFavoriteClassService.addFavoriteByUserId(userId, favoriteName, isPrivate);
        return integer > 0 ? ResultUtils.success("ok", "添加成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "添加失败", "");
    }

    /**
     * 批量删除收藏夹
     *
     * @param ids
     * @return
     */
    @DeleteMapping("deleteFavoriteClassById")
    public BaseResponse deleteFavoriteClassById(@RequestBody List<Long> ids) {
        Integer integer = iBlogFavoriteClassService.deleteFavoriteClassById(ids);
        return integer > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

}

