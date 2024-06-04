package com.renjia.blog.config.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renjia.blog.util.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");  //从request的header里面获取token信息
        Map<String, Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);  //验证token信息
            return true;
        } catch (TokenExpiredException e) {
            map.put("code", 2001);
            map.put("message", "Token已经过期!!!");
        } catch (SignatureVerificationException e) {
            map.put("code", 2001);
            map.put("message", "签名错误!!!");
        } catch (AlgorithmMismatchException e) {
            map.put("code", 2001);
            map.put("message", "加密算法不匹配!!!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 2001);
            map.put("message", "无效token~~");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}