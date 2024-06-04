package com.renjia.blog.util;


import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;

import java.util.List;

/**
 * 返回类型工具类
 *
 * @author zeng
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, null);
    }

    /**
     * 成功
     *
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse success(String message, String description) {
        return new BaseResponse<>(0, null, message, description);
    }


    /**
     * 成功
     *
     * @param data
     * @param message
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse success(T data, String message, String description) {
        return new BaseResponse<>(0, data, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param data
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String data, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), data, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }


    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static <T> BaseResponse error(ErrorCode errorCode,T data) {
        return new BaseResponse<T>(errorCode.getCode(), data, errorCode.getMessage());
    }
}