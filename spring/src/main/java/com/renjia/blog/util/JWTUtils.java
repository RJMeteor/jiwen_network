package com.renjia.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static String TOKEN = "@#￥%%&*"; //构成JWT中的第三部分signature的时候要使用的签名（相当于秘钥）。

    /**
     * 生成token * * @param map //传入payload * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {    //遍历将传入的map里面的元素添加到JWT的payload中，这里也可以用withclaim一个一个的加入。
            builder.withClaim(k, v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 3);  //添加token过期时间，Calendar.DATE可以换成秒之类的。 3天
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();
    }

    /**
     * 验证token * 获取token中payload * @param token * @return
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);  //这个verify方法会自动验证token，如果错了会抛出异常，同时也可以用这个来获取JWT中的payload中的内容。具体应该是getclaim就可以获取里面的元素。
    }
}
