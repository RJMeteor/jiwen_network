package com.renjia.blog.util.other;

/**
 * 返回码
 *
 * @author zeng
 */
public enum ErrorCode {

    SUCCESS(0, "成功", ""),
    ERROR(400, "", ""),
    NO_LOGIN(403, "未登录", ""),
    NO_AUTH(404, "暂无权限访问", ""),
    NO_PARAM(500, "参数错误", ""),
    NO_ARTICLE(4004, "文章无法访问", ""),
    NO_OTHER(4005, "错误", "");
    //返回码
    private final int code;
    //操作响应信息
    private final String message;
    //响应信息的详细描述
    private final String description;

    //构造函数
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    //get方法
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
