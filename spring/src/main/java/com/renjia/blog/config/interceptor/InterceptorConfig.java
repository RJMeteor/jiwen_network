package com.renjia.blog.config.interceptor;

import com.renjia.blog.config.interceptor.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Component
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = Arrays.asList(
                "/blogUser/getUserById/**",
                "/blogArticleLables/getLables",
                "/blogArticle/articlesByNameAndLabel",
                "/blogArticle/articleByUserIdAndName",
                "/blogArticle/getArticlesByUserIdAndArticlesId/**",
                "/blogLikeBrowse/addLikeByArticleIdAndUserIdAndpersonId/1",
                "/blogComment/getCommentByArticleId",
                "/blogFavorite/getFavoriteArticleByUserId",
                "/blogLikeBrowse/getOhtArticle",
                "/blogLikeBrowse/getOhtTopic",
                "/blogAttention/getOhtUser",
                "/blogUserNotiSet/notisetById/**",
                "/blogArticle/articleByUserIdAndName/**",
                "/blogUser/uploadImg",
                "/blogArticle/uploadimg"
        );
        registry.addInterceptor(new JwtTokenInterceptor()).
                excludePathPatterns(excludePaths).
                excludePathPatterns("/blog/**");  //代表/blog/**目录下的接口不用token验证


    }
}
