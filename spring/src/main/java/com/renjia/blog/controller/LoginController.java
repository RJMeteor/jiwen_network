package com.renjia.blog.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogUserService;
import com.renjia.blog.util.JWTUtils;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.exceptions.OtherException;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import com.renjia.blog.util.other.MailMessage;
import com.renjia.blog.util.MailUtil;
import com.renjia.blog.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/blog")
public class LoginController {
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private IBlogUserService iBlogUserService;

    @PostMapping("admin")
    public BaseResponse admin(@RequestBody BlogUser user) {
        BlogUser userDb = iBlogUserService.userByNameEmailAndPassToSelect(user);
        if (ObjectUtils.isEmpty(userDb)) {
            return ResultUtils.error(ErrorCode.ERROR, "用户不存在", "");
        }
        if (!("3029364473@qq.com".equals(userDb.getUserEmail()))) {
            return ResultUtils.error(ErrorCode.ERROR, "你不是管理员！", "");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", userDb.getUserName());
        hashMap.put("userEmail", userDb.getUserEmail());
        userDb.setToken(JWTUtils.getToken(hashMap));
        return ResultUtils.success(userDb);
    }

    /**
     * 注册
     *
     * @param user 用户实体
     * @return
     */
    @PostMapping("login")
    public BaseResponse login(@RequestBody BlogUser user) {
        BlogUser userDb = iBlogUserService.userByNameEmailAndPassToSelect(user);
        if (ObjectUtils.isEmpty(userDb)) {
            return ResultUtils.error(ErrorCode.ERROR, "用户不存在", "");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", userDb.getUserName());
        hashMap.put("userEmail", userDb.getUserEmail());
        userDb.setToken(JWTUtils.getToken(hashMap));
        return ResultUtils.success(userDb);
    }

    /**
     * 注册
     *
     * @param user 用户实体
     * @param code 验证码
     */
    @PostMapping("registry/{code}")
    public BaseResponse registry(@RequestBody BlogUser user, @PathVariable("code") String code) {
        LambdaQueryWrapper<BlogUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(BlogUser::getUserEmail, user.getUserEmail());
        if (iBlogUserService.getOne(userLambdaQueryWrapper) != null) {
            return ResultUtils.error(ErrorCode.ERROR, "该邮箱以注册，请勿重复注册", "");
        }
        Object reCode = redisUtil.get("registryCode:" + user.getUserEmail());
        if (ObjectUtils.isEmpty(reCode) || !reCode.equals(code))
            return ResultUtils.error(ErrorCode.ERROR, "验证码不正确", "");

        return iBlogUserService.userByInsert(user) > 0 ? ResultUtils.success("注册成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "注册失败", "");

    }

    /**
     * 注册验证码发送
     *
     * @param user 用户实体
     */
    @PostMapping("registryCode")
    public BaseResponse registryCode(@RequestBody BlogUser user) throws OtherException {
        LambdaQueryWrapper<BlogUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(BlogUser::getUserEmail, user.getUserEmail());
        if (iBlogUserService.getOne(userLambdaQueryWrapper) != null) {
            return ResultUtils.error(ErrorCode.ERROR, "该邮箱以注册，请勿重复注册", "");
        }
        String code = RandomStringUtils.randomNumeric(4);
        MailMessage message = new MailMessage();
        message.setCode(code);
        message.setContent("注册");
        message.setTime("5");
        redisUtil.set("registryCode:" + user.getUserEmail(), code, 60 * 5);
        MailUtil.sendMessageMail(message, "验证码消息通知", user.getUserEmail(), "email.ft");
        return ResultUtils.success("发送验证码成功", "");
    }

    /**
     * 重置密码
     *
     * @param user 用户实体
     * @param code 验证码
     */
    @PostMapping("reset/{code}")
    public BaseResponse reset(@RequestBody BlogUser user, @PathVariable("code") String code) {
        LambdaQueryWrapper<BlogUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(BlogUser::getUserEmail, user.getUserEmail());
        if (iBlogUserService.getOne(userLambdaQueryWrapper) == null) {
            return ResultUtils.error(ErrorCode.ERROR, "未注册该邮箱的账号", "");
        }
        Object reCode = redisUtil.get("resetCode:" + user.getUserEmail());
        System.out.println(reCode);
        if (ObjectUtils.isEmpty(reCode) || !reCode.equals(code))
            return ResultUtils.error(ErrorCode.ERROR, "验证码不正确", "");
        return iBlogUserService.userByEmailToPassUpdate(user) > 0 ? ResultUtils.success("密码修改成功", "")
                : ResultUtils.error(ErrorCode.ERROR, "未重置成功", "");
    }

    /**
     * 重置密码验证码发送
     *
     * @param user 用户实体
     */
    @PostMapping("resetCode")
    public BaseResponse resetCode(@RequestBody BlogUser user) throws OtherException {
        String code = RandomStringUtils.randomNumeric(4);
        MailMessage message = new MailMessage();
        message.setCode(code);
        message.setContent("重置密码");
        message.setTime("5");
        redisUtil.set("resetCode:" + user.getUserEmail(), code, 60 * 5);
        MailUtil.sendMessageMail(message, "验证码消息通知", user.getUserEmail(), "email.ft");
        return ResultUtils.success("发送验证码成功", "");
    }


}
