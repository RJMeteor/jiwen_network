package com.renjia.blog.config.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteRedisCacheAnnotation {
    RedisCacheAnnotation.Type cacheName();
    String key();   //缓存key spring sqel解析
    boolean isDeleteAll() default  false;
}
