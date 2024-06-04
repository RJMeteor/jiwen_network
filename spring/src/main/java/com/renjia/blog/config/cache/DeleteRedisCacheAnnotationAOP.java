package com.renjia.blog.config.cache;

import com.renjia.blog.config.cache.DeleteRedisCacheAnnotation;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Aspect
public class DeleteRedisCacheAnnotationAOP {
    @Autowired
    private RedisTemplate redisTemplate;

    //使用SPEL进行key的解析
    ExpressionParser parser = new SpelExpressionParser();

    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(com.renjia.blog.config.cache.DeleteRedisCacheAnnotation)")
    public void PointCut() {
    }

    @Around("PointCut()")
    public Object aroundAdviceMethod(ProceedingJoinPoint joinPoint) {
        Boolean isCache = false;
        Object result = null;
        //得到被切面修饰的方法的参数列表
        Object[] args = joinPoint.getArgs();
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DeleteRedisCacheAnnotation anno = method.getAnnotation(DeleteRedisCacheAnnotation.class);
        RedisCacheAnnotation.Type type = anno.cacheName();
        String spel = anno.key();

        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(spel);
        Optional.ofNullable(anno)
                .filter(ex -> ex.isDeleteAll())
                .map(ex -> {
                    redisTemplate.delete(expression.getValue(context, String.class));
                    return ex;
                }).orElseGet(() -> {
                    if (args.length == 1) {
                        if (args[0] instanceof List) {
                            List<Long> value = expression.getValue(context, List.class);
                            HashMap<String, List<Long>> o = (HashMap<String, List<Long>>) redisTemplate.opsForValue().get(type.getType());
                            for (Long aLongs : value) {
                                q:
                                for (String s : o.keySet()) {
                                    for (Long aLong : o.get(s)) {
                                        if (aLong.equals(aLongs)) {
                                            redisTemplate.delete(s);
                                            break q;
                                        }
                                    }
                                }
                            }
                        } else {
                            Long value = expression.getValue(context, Long.class);
                            HashMap<String, List<Long>> o = (HashMap<String, List<Long>>) redisTemplate.opsForValue().get(type.getType());
                            q:
                            for (String s : o.keySet()) {
                                for (Long aLong : o.get(s)) {
                                    if (aLong.equals(value)) {
                                        redisTemplate.delete(s);
                                        break q;
                                    }
                                }
                            }
                        }
                    }
                    return null;
                });
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            return result;
        }
    }


}