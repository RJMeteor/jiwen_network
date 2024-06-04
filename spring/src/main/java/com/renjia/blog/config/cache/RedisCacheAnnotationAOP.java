package com.renjia.blog.config.cache;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
import com.renjia.blog.pojo.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RedisCacheAnnotationAOP {
    @Autowired
    private RedisTemplate redisTemplate;

    //使用SPEL进行key的解析
    ExpressionParser parser = new SpelExpressionParser();

    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(com.renjia.blog.config.cache.RedisCacheAnnotation)")
    public void PointCut() {
    }

    @Around("PointCut()")
    public Object aroundAdviceMethod(ProceedingJoinPoint joinPoint) {
        final Object[] result = new Object[1];
        //得到被切面修饰的方法的参数列表
        Object[] args = joinPoint.getArgs();
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedisCacheAnnotation anno = method.getAnnotation(RedisCacheAnnotation.class);
        String spel = anno.conditions();
        String[] params = discoverer.getParameterNames(method);
        Boolean isCache = parseKey(spel, method, args, Boolean.class);
        if (isCache) {
            String[] keys = anno.key();
            for (int i = 0; i < keys.length; i++) {
                if (StringUtils.isBlank(keys[i])) {
                    continue;
                }
                final String realkey = parseKey(keys[i], method, args, String.class);
                //取出缓存中的数据
                result[0] = redisTemplate.opsForValue().get(realkey);
                //缓存是空的，则需要重新查询数据库
                Optional.ofNullable(result)
                        .filter((target) -> ObjectUtils.isEmpty(target[0]))
                        .map((target) -> {
                            try {
                                Object proceed = joinPoint.proceed();
                                target[0] = proceed;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                            return target;
                        })
                        .filter((target) -> !ObjectUtils.isEmpty(target[0]))
                        .map((target) -> {
                            if (target[0] instanceof PageInfo) {
                                putMapId(realkey, ((PageInfo) target[0]).getList(), anno.expireTime());
                            } else if (target[0] instanceof List) {
                                putMapId(realkey, target[0], anno.expireTime());
                            }
                            if (anno.expireTime() == 0) {
                                redisTemplate.opsForValue().set(realkey, target[0]);
                            } else {
                                redisTemplate.opsForValue().set(realkey, target[0], anno.expireTime(), TimeUnit.MILLISECONDS);
                            }
                            return target;
                        });
            }
        }
        try {
            if (ObjectUtils.isEmpty(result[0])) {
                result[0] = joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            return result[0];
        }
    }


    /**
     * 添加缓存映射ID,方便后面细化清理缓存
     *
     * @return
     */
    private void putMapId(String realkey, Object result, Long expireTime) {
        Class<?> resultClass = result.getClass();
        if (List.class.isAssignableFrom(resultClass)) {
            List<?> resultList = (List<?>) result;
            if (!resultList.isEmpty()) {
                isType(resultList, realkey, expireTime);
            }
        }
    }

    private void isType(List<?> resultList, String realkey, Long expireTime) {
        Object o1 = resultList.get(0);
        Map<String, List<Long>> mapId = new HashMap<>();
        String cacheName = null;
        String typeName = o1.getClass().getSimpleName();
        List<Long> listId = new ArrayList<>();
        if (RedisCacheAnnotation.Type.BlogArticle.getType().equals(typeName)) {
            cacheName = RedisCacheAnnotation.Type.BlogArticle.getType();
            for (BlogArticle o : (List<BlogArticle>) resultList) {
                listId.add(o.getArticleContent().getArticleId());
            }
        } else if (RedisCacheAnnotation.Type.BlogComment.getType().equals(typeName)) {
            cacheName = RedisCacheAnnotation.Type.BlogComment.getType();
            for (BlogComment o : (List<BlogComment>) resultList) {
                listId.add(o.getCommentContent().getCommentId());
            }
        } else if (RedisCacheAnnotation.Type.BlogArticleLables.getType().equals(typeName)) {
            cacheName = RedisCacheAnnotation.Type.BlogArticleLables.getType();
            for (BlogArticleLables o : (List<BlogArticleLables>) resultList) {
                listId.add(o.getId());
            }
        }
//        else if (RedisCacheAnnotation.Type.BlogChattingRecords.getType().equals(typeName)) {
//            cacheName = RedisCacheAnnotation.Type.BlogChattingRecords.getType();
//            for (BlogChattingRecords o : (List<BlogChattingRecords>) resultList) {
//                listId.add(o.getId());
//            }
//        }
        mapId.put(realkey, listId);
        doPutMapId(cacheName, mapId, expireTime);
    }

    private void doPutMapId(String realkey, Object result, Long expireTime) {
        redisTemplate.opsForList().leftPush(realkey, result);
        if (expireTime != 0) {
            redisTemplate.expire(realkey, expireTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    private <T> T parseKey(String key, Method method, Object[] args, Class<T> classs) {
        if (StringUtils.isEmpty(key.trim())) return null;

        //获取被拦截方法参数名列表(使用Spring支持类库)
        String[] paraNameArr = discoverer.getParameterNames(method);

        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("renjia",1);
//        if (args.length == 0) return (T) key;
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        System.out.println(key);
        return parser.parseExpression(key).getValue(context, classs);
    }
}