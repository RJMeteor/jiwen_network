package com.renjia.blog.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renjia.blog.pojo.BlogArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjia.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 查询用户信息
     *
     * @param userName     用户名
     * @param articleTitle 文章名
     * @param lableId      文章标签id
     **/
    List<BlogArticle> articleListByNameAndTitleAndLableId(@Param("userName") String userName,
                                                          @Param("articleTitle") String articleTitle,
                                                          @Param("lableId") Long lableId);

    /**
     * 添加文章数据
     *
     * @param blogArticle
     * @return
     */
    Integer inserArticle(BlogArticle blogArticle);

    /*
     *用户发布的文章列表
     * */
    List<BlogArticle> articleByUserIdAndName(@Param("userId") Long userId, @Param("isPrivate") Integer isPrivate, @Param("limiter") Integer limiter, @Param("articleName") String articleName);

    List<BlogArticle> articlesByNameAndLabelAndUserId(@Param("articlesName") String articlesName,
                                             @Param("label") Integer label,
                                             @Param("userId") Long userId,
                                             @Param("limiter") Integer limiter,
                                             @Param("privacy") Integer privacy);

    List<BlogArticle> articlesByNameAndLabel(@Param("articlesName") String articlesName,
                                             @Param("label") Integer label,
                                             @Param("limiter") Integer limiter,
                                             @Param("privacy") Integer privacy);

    /**
     * 根据文章内容id得到文章id
     *
     * @param articlesContentId
     * @return
     */
    Long getArticleId(@Param("articlesContentId") String articlesContentId);


//    @Cacheable(value = "aa",key = "'dd-'+#a0+'-'+#a1")
    BlogArticle getArticlesByUserIdAndArticlesId(@Param("userId") Long userId, @Param("articlesId") Long articlesId);

    /**
     * @param articleId
     * @return
     */
    BlogArticle getArticleById(@Param("articleId") Integer articleId);
}
