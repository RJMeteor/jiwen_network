package com.renjia.blog.config;


import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.exceptions.EmptyArticleException;
import com.renjia.blog.util.exceptions.OtherException;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionConfig {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> list = new ArrayList<>();
        result.getFieldErrors().forEach(error -> {
            String msg = error.getDefaultMessage();
            list.add(msg);
        });
        BaseResponse error = ResultUtils.error(ErrorCode.NO_PARAM, list);
        return new ResponseEntity<BaseResponse>(error, HttpStatus.OK);
    }

    @ExceptionHandler({EmptyArticleException.class})
    public ResponseEntity<BaseResponse> loginException(EmptyArticleException e) {
        BaseResponse error = ResultUtils.error(ErrorCode.NO_ARTICLE, ErrorCode.NO_ARTICLE.getMessage(), "");
        return new ResponseEntity<BaseResponse>(error, HttpStatus.OK);
    }
    @ExceptionHandler({OtherException.class})
    public ResponseEntity<BaseResponse> loginException(OtherException e) {
        BaseResponse error = ResultUtils.error(ErrorCode.NO_OTHER, e.getMessage(), "");
        return new ResponseEntity<BaseResponse>(error, HttpStatus.OK);
    }



}
