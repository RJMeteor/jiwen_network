package com.renjia.blog.config.cache;

import com.renjia.blog.pojo.BlogArticleLables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheAnnotation {
    //    涉及到的缓存MapId映射表
    enum Type {
        BlogArticle("BlogArticle"),
//        BlogChattingRecords("BlogChattingRecords"),
        BlogArticleLables("BlogArticleLables"),
        BlogComment("BlogComment");
        String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    String[] key();   //缓存key spring sqel解析


    long expireTime() default 1000 * 60; //缓存过期时间 毫秒

    String conditions() default "1>0";  //条件满足才缓存

}
