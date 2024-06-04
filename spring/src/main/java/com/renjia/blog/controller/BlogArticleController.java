package com.renjia.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogArticleLableClass;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogArticleService;
import com.renjia.blog.service.IBlogUserService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.exceptions.EmptyArticleException;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogArticle")
public class BlogArticleController {
    @Resource
    private IBlogArticleService iBlogArticleService;

    /**
     * 添加文章的图片
     *
     * @param file 文件
     * @return
     */
    @PostMapping("uploadimg")
    public HashMap<String, String> uploadimg(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = iBlogArticleService.uploadImg(file);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fileName", imgUrl);
        hashMap.put("url", imgUrl);
        return hashMap;
    }

    /**
     * 查询用户信息
     *
     * @param userName     用户名
     * @param articleTitle 文章名
     * @param lableId      文章标签id
     * @param page         第几页
     * @param size         每页大小
     * @return
     */
    @GetMapping("articleList")
    public BaseResponse articleListByNameAndTitleAndLableId(@RequestParam("userName") String userName,
                                                            @RequestParam("articleTitle") String articleTitle,
                                                            @RequestParam("lableId") Long lableId,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size) {
        PageInfo<BlogArticle> userlist = iBlogArticleService.articleListByNameAndTitleAndLableId(userName != null ? userName : "", articleTitle != null ? articleTitle : "", lableId, page, size);
        return ResultUtils.success(userlist);
    }


    /**
     * 管理员禁用文章（拉黑）
     *
     * @param articleId 文章Id
     * @return
     */
    @PutMapping("disabledArticle")
    public BaseResponse disabledArticle(@RequestParam("articleId") Long articleId) {
        Integer integer = iBlogArticleService.disabledArticle(articleId);
        return integer > 0 ?
                ResultUtils.success("ok", "拉黑成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "拉黑失败", "");
    }

    /**
     * 管理员批量禁用文章（拉黑）
     *
     * @param articleIds 文章列表id
     * @return
     */
    @PutMapping("disabledArticleIds")
    public BaseResponse disabledArticleIds(@RequestBody List<Long> articleIds) {
        return iBlogArticleService.disabledArticleIds(articleIds) > 0 ? ResultUtils.success("ok", "拉黑成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "拉黑失败", "");
    }

    /**
     * 管理员取消禁用文章（拉黑）
     *
     * @param articleId 文章Id
     * @return
     */
    @PutMapping("cancelDisabledArticle")
    public BaseResponse cancelDisabledArticle(@RequestParam("articleId") Long articleId) {
        Integer integer = iBlogArticleService.cancelDisabledArticle(articleId);
        return integer > 0 ?
                ResultUtils.success("ok", "取消成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }

    /**
     * 管理员批量取消禁用用户（拉黑）
     *
     * @param articleIds 文章Id列表
     * @return
     */
    @PutMapping("cancelDisabledArticleIds")
    public BaseResponse cancelDisabledArticleIds(@RequestBody List<Long> articleIds) {
        return iBlogArticleService.cancelDisabledArticleIds(articleIds) > 0 ?
                ResultUtils.success("ok", "取消成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }

    @PutMapping("updateArticle")
    public BaseResponse updateArticle(@RequestBody BlogArticle blogArticle) {
        System.out.println(blogArticle);
        return iBlogArticleService.updateArticle(blogArticle, blogArticle.getArticleLableClasss()) > 0 ? ResultUtils.success("ok", "修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }

    @PostMapping("addArticle")
    public BaseResponse inserArticle(@RequestBody BlogArticle blogArticle) {
        return iBlogArticleService.inserArticle(blogArticle, blogArticle.getArticleLableClasss()) > 0 ? ResultUtils.success("ok", "提交成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "提交失败", "");
    }

    @DeleteMapping("deleteArticleByIds")
    public BaseResponse deleteArticleByIds(@RequestBody List<Integer> articleIds) {
        return iBlogArticleService.deleteArticleByIds(articleIds) > 0 ? ResultUtils.success("ok", "删除成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "删除失败", "");
    }

    /**
     * 通过用户id分页获取文章
     *
     * @param userId 用户id
     * @param name   文章名
     * @param page   第几页
     * @param size   每页条数
     */
    @GetMapping("articleByUserIdAndName")
    public BaseResponse articleByUserIdAndName(@RequestParam("userId") Long userId,
                                               @RequestParam(value = "limiter", defaultValue = "0") Integer limiter,
                                               @RequestParam("isPrivate") Integer isPrivate,
                                               @RequestParam(value = "articlesName", required = false) String name,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) {
        PageInfo<BlogArticle> blogArticles = iBlogArticleService.articleByUserIdAndName(userId, isPrivate, limiter, name, page, size);
        return ResultUtils.success(blogArticles);
    }

    /**
     * 通过文章名分页获取文章
     * @param name    文章名
     * @param label   文章标签id
     * @param page    第几页
     * @param size    每页条数
     * @param limiter 文章类型，0文章，1话题
     * @param privacy 0公开，1私密
     */
    @GetMapping("articlesByNameAndLabel")
    public BaseResponse articlesByNameAndLabel(@RequestParam("page") Integer page,
                                               @RequestParam("limiter") Integer limiter,
                                               @RequestParam("label") Integer label,
                                               @RequestParam("privacy") Integer privacy,
                                               @RequestParam("size") Integer size,
                                               @RequestParam(value = "articlesName", required = false) String name) {
        System.out.println(name + "-" + limiter + "-" + label + "-" + privacy);
        PageInfo<BlogArticle> blogArticles = iBlogArticleService.articlesByNameAndLabel(name, limiter, label, privacy, page, size);
        return ResultUtils.success(blogArticles);
    }

    /**
     * 获取我关注的人的文章
     *
     * @param name    文章名
     * @param label   文章标签id
     * @param page    第几页
     * @param size    每页条数
     * @param limiter 文章类型，0文章，1话题
     * @param privacy 0公开，1私密
     */
    @GetMapping("articlesByNameAndLabelAndUserId")
    public BaseResponse articlesByNameAndLabelAndUserId(@RequestParam("page") Integer page,
                                                        @RequestParam("limiter") Integer limiter,
                                                        @RequestParam("userId") Long userId,
                                                        @RequestParam("label") Integer label,
                                                        @RequestParam("privacy") Integer privacy,
                                                        @RequestParam("size") Integer size,
                                                        @RequestParam(value = "articlesName", required = false) String name) {
        PageInfo<BlogArticle> blogArticles = iBlogArticleService.articlesByNameAndLabelAndUserId(name, limiter, userId, label, privacy, page, size);
        return ResultUtils.success(blogArticles);
    }

    @GetMapping("getArticlesByUserIdAndArticlesId/{userId}/{articlesId}")
    public BaseResponse getArticlesByUserIdAndArticlesId(@PathVariable("userId") Long userId,
                                                         @PathVariable("articlesId") Long articlesId) throws EmptyArticleException {
        BlogArticle articles = iBlogArticleService.getArticlesByUserIdAndArticlesId(userId, articlesId);
        return ResultUtils.success(articles);
    }
}

