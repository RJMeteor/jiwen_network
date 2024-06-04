package com.renjia.blog.controller;


import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogUserService;
import com.renjia.blog.util.MailUtil;
import com.renjia.blog.util.RedisUtil;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import com.renjia.blog.util.other.MailMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/blogUser")
public class BlogUserController {
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private IBlogUserService iBlogUserService;

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param userEmail 用户邮箱
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    @GetMapping("userList")
    public BaseResponse userlistByNameAndEmail(@RequestParam("userName") String userName,
                                               @RequestParam("userEmail") String userEmail,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) {
        PageInfo<BlogUser> userlist = iBlogUserService.userlistByNameAndEmail(userName != null ? userName : "", userEmail != null ? userEmail : "", page, size);
        return ResultUtils.success(userlist);
    }

    /**
     * 管理员禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    @PutMapping("disabledUser")
    public BaseResponse disabledUser(@RequestParam("userId") Long userId) {
        Integer integer = iBlogUserService.disabledUser(userId);
        return integer > 0 ?
                ResultUtils.success("ok","拉黑成功","")
                : ResultUtils.error(ErrorCode.ERROR, "拉黑失败", "");
    }

    /**
     * 管理员批量禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    @PutMapping("disabledUserIds")
    public BaseResponse disabledUserIds(@RequestBody List<Long> userIds) {
        return iBlogUserService.disabledUserIds(userIds) > 0 ? ResultUtils.success("ok", "拉黑成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "拉黑失败", "");

    }

    /**
     * 管理员取消禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    @PutMapping("cancelDisabledUser")
    public BaseResponse cancelDisabledUser(@RequestParam("userId") Long userId) {
        Integer integer = iBlogUserService.cancelDisabledUser(userId);
        return integer > 0 ?
                ResultUtils.success("ok","取消成功","")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }

    /**
     * 管理员批量取消禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    @PutMapping("cancelDisabledUserIds")
    public BaseResponse cancelDisabledUserIds(@RequestBody List<Long> userIds) {
        return iBlogUserService.cancelDisabledUserIds(userIds) > 0 ?
                ResultUtils.success("ok", "取消成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "取消失败", "");
    }
    /**
     * 根据用户id更新用户信息
     */
    @GetMapping("getUserById/{userId}")
    public BaseResponse userByIdToSelect(@PathVariable("userId") Integer userId) {
        BlogUser blogUser = iBlogUserService.userByIdToSelect(userId);
        return ResultUtils.success(blogUser);
    }

    /**
     * 根据用户id更新用户信息
     */
    @PutMapping("idToAllUpdate")
    public BaseResponse userByIdToAllUpdate(@RequestBody BlogUser user) {
        Integer integer = iBlogUserService.userByIdToAllUpdate(user);
        return integer > 0 ?
                ResultUtils.success("修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }

    /**
     * 上传文件
     *
     * @param file
     * @param userId
     * @return
     * @throws
     */
    @PostMapping("uploadImg")
    public BaseResponse uploadImg(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId, @RequestParam("imageUrl") String imageUrl) throws Exception {
        String imgUrl = iBlogUserService.uploadImg(file, userId, imageUrl);
        return !ObjectUtils.isEmpty(imgUrl)
                ? ResultUtils.<String>success(imgUrl, "上传成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "上传失败", "");
    }

    /**
     * 发送用户更新验证码
     *
     * @param user
     */
    @PostMapping("updateCode")
    public void updateCode(@RequestBody BlogUser user) {
        String code = RandomStringUtils.randomNumeric(4);
        MailMessage message = new MailMessage();
        message.setCode(code);
        message.setContent("重置密码");
        message.setTime("5");
        redisUtil.set("updateCode:" + user.getUserEmail(), code, 60 * 5);
        MailUtil.sendMessageMail(message, "验证码消息通知", user.getUserEmail(), "email.ft");
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param code 验证码
     * @return
     */
    @PutMapping("updateUser/{code}")
    public BaseResponse updateUser(@RequestBody BlogUser user, @PathVariable("code") String code) {
        Object reCode = redisUtil.get("updateCode:" + user.getUserEmail());
        if (ObjectUtils.isEmpty(reCode) || !reCode.equals(code))
            return ResultUtils.error(ErrorCode.ERROR, "验证码不正确", "");

        return iBlogUserService.updateUser(user) > 0 ? ResultUtils.success("修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "修改失败", "");
    }


}

