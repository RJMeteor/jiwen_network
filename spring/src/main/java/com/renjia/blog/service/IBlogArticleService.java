package com.renjia.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.cache.DeleteRedisCacheAnnotation;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogArticleLableClass;
import com.renjia.blog.util.exceptions.EmptyArticleException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogArticleService extends IService<BlogArticle> {


    public String uploadImg(MultipartFile file) throws Exception;
    /**
     * 查询用户信息
     *
     * @param userName     用户名
     * @param articleTitle 文章名
     * @param lableId      文章标签id
     * @param page         第几页
     * @param size         每页大小
     * @return
     */
    public PageInfo<BlogArticle> articleListByNameAndTitleAndLableId(String userName,
                                                                     String articleTitle,
                                                                     Long lableId,
                                                                     Integer page,
                                                                     Integer size);


    /**
     * 管理员禁用文章（拉黑）
     *
     * @param articleId 文章Id
     * @return
     */
    public Integer disabledArticle(Long articleId);


    /**
     * 管理员批量禁用文章（拉黑）
     *
     * @param articleIds 文章列表id
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticle, key = "#articleIds")
    public Integer disabledArticleIds(List<Long> articleIds);

    /**
     * 管理员取消禁用文章（拉黑）
     *
     * @param articleId 文章Id
     * @return
     */
    public Integer cancelDisabledArticle(Long articleId);

    /**
     * 管理员批量取消禁用用户（拉黑）
     *
     * @param articleIds 文章Id列表
     * @return
     */
    public Integer cancelDisabledArticleIds(List<Long> articleIds);

    /**
     * 更新文章
     *
     * @param blogArticle
     * @param blogArticleLableClass
     * @return
     */
    Integer updateArticle(BlogArticle blogArticle, ArrayList<BlogArticleLableClass> blogArticleLableClass);

    /**
     * 添加文章
     *
     * @param blogArticle           文章实体类
     * @param blogArticleLableClass 文章标签Id列表
     * @return
     */
    public Integer inserArticle(BlogArticle blogArticle, ArrayList<BlogArticleLableClass> blogArticleLableClass);

    /*
     * 根据文章id集合批量 删除文章
     * */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticle, key = "#articleIds")
    public Integer deleteArticleByIds(List<Integer> articleIds);

    /**
     * 通过用户id分页获取文章
     *
     * @param userId 用户id
     * @param name   文章名
     * @param page   第几页
     * @param size   每页条数
     * @return
     */
    public PageInfo<BlogArticle> articleByUserIdAndName(Long userId,
                                                        Integer isPrivate,
                                                        Integer limiter,
                                                        String name,
                                                        Integer page,
                                                        Integer size);

    /**
     * 通过文章名分页获取文章
     *
     * @param name    文章名
     * @param label   标签ID
     * @param limiter 文章类别
     * @param privacy 文章私有
     * @param page    第几页
     * @param size    每页条数
     * @return
     */
    public PageInfo<BlogArticle> articlesByNameAndLabel(String name,
                                                        Integer limiter,
                                                        Integer label,
                                                        Integer privacy,
                                                        Integer page, Integer size) throws IOException;

    /**
     * 获取我关注的人的文章
     *
     * @param name    文章名
     * @param label   标签ID
     * @param userId   用户ID
     * @param limiter 文章类别
     * @param privacy 文章私有
     * @param page    第几页
     * @param size    每页条数
     * @return
     */
    public PageInfo<BlogArticle> articlesByNameAndLabelAndUserId(String name,
                                                        Integer limiter,
                                                        Long userId,
                                                        Integer label,
                                                        Integer privacy,
                                                        Integer page, Integer size) throws IOException;


    /**
     * 获取文章详细信息
     *
     * @param userId
     * @param articlesId
     * @return
     */
    public BlogArticle getArticlesByUserIdAndArticlesId(Long userId, Long articlesId) throws EmptyArticleException;

}
