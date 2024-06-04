package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.service.IBlogArticleLablesService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文章标签 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogArticleLables")
public class BlogArticleLablesController {
    @Resource
    private IBlogArticleLablesService iBlogArticleLablesService;

    @GetMapping("getLables")
    public BaseResponse getLables() {
        List<BlogArticleLables> list = iBlogArticleLablesService.getLables();
        return ResultUtils.success(list);
    }

    @PostMapping("addLables")
    public BaseResponse addLables(@RequestBody List<String> lables) {
        Integer integer = iBlogArticleLablesService.addLables(lables);
        return integer > 0 ?
                ResultUtils.success("ok", "添加成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "添加失败", "");
    }

    @PutMapping("editLable")
    public BaseResponse editLable(@RequestParam("lableId") Long lableId,@RequestParam("lableName") String lableName) {
        Integer integer = iBlogArticleLablesService.editLable(lableId,lableName);
        return integer > 0 ?
                ResultUtils.success("ok", "修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }

    /**
     * 查询标签信息
     *
     * @param lableName 标签名
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    @GetMapping("lableList")
    public BaseResponse lableListByName(@RequestParam("lableName") String lableName,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        PageInfo<BlogArticleLables> userlist = iBlogArticleLablesService.lableListByName(lableName != null ? lableName : "", page, size);
        return ResultUtils.success(userlist);
    }

    /**
     * 管理员禁用标签（拉黑）
     *
     * @param lableId 标签Id
     * @return
     */
    @PutMapping("disabledLable")
    public BaseResponse disabledLable(@RequestParam("lableId") Long lableId) {
        Integer integer = iBlogArticleLablesService.disabledLable(lableId);
        return integer > 0 ?
                ResultUtils.success("ok", "禁用成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "禁用失败", "");
    }

    /**
     * 管理员批量禁用标签（拉黑）
     *
     * @param lableIds 标签列表id
     * @return
     */
    @PutMapping("disabledLableIds")
    public BaseResponse disabledLableIds(@RequestBody List<Long> lableIds) {
        return iBlogArticleLablesService.disabledLableIds(lableIds) > 0 ? ResultUtils.success("ok", "禁用成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "禁用失败", "");

    }

    /**
     * 管理员取消禁用标签（拉黑）
     *
     * @param lableId 标签Id
     * @return
     */
    @PutMapping("cancelDisabledLable")
    public BaseResponse cancelDisabledLable(@RequestParam("lableId") Long lableId) {
        Integer integer = iBlogArticleLablesService.cancelDisabledLable(lableId);
        return integer > 0 ?
                ResultUtils.success("ok", "取消成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }

    /**
     * 管理员批量取消禁用标签（拉黑）
     *
     * @param lableIds 标签Id列表
     * @return
     */
    @PutMapping("cancelDisabledLableIds")
    public BaseResponse cancelDisabledLableIds(@RequestBody List<Long> lableIds) {
        return iBlogArticleLablesService.cancelDisabledLableIds(lableIds) > 0 ?
                ResultUtils.success("ok", "取消成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }
}

