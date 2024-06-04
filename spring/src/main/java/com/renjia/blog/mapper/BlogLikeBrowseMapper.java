package com.renjia.blog.mapper;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavorite;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 点赞/浏览 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogLikeBrowseMapper extends BaseMapper<BlogLikeBrowse> {



    /**
     * 根据文章id获取文章点赞数
     *
     * @param articleId
     * @return
     */
    List<Long> getLikeCountByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章id获取文章浏览数
     *
     * @param articleId
     * @return
     */
    Long getBrowseCountByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章id 获取是否已点赞
     *
     * @param articleId
     * @param userId
     * @return
     */
    Integer getIsLikeByArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);


    /**
     * 获取排除自身的收藏，查看别人收藏了你那些内容
     *
     * @param personId
     */
    List<BlogLikeBrowse> getLikeByPersonId(@Param("personId") Long personId);

    /**
     * 删除其他人收藏我的文章，active=1
     *
     * @param ids
     */
    Integer updateLikeActive(List<Long> ids);


    BlogLikeBrowse getLikeById(@Param("likeId") Long likeId);

    Integer addLike(BlogLikeBrowse blogLikeBrowse);

    //获取热门文章
    List<BlogLikeBrowse> getOhtArticle();


    //获取热门话题
    List<BlogLikeBrowse> getOhtTopic();
}
