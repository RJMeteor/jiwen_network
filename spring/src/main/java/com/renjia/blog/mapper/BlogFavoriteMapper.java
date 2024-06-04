package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogFavorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 收藏夹 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogFavoriteMapper extends BaseMapper<BlogFavorite> {

    List<BlogFavorite> getFavoritesByPersonId(@Param("personId") Long personId);

    /**
     * 根据文章id 获取文章收藏数信息
     *
     * @param articleId
     * @return
     */
    List<Long> getFavoriteCountByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章id 获取是否已收藏
     *
     * @param articleId
     * @param userId
     * @return
     */
    Integer getIsFavoriteByArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    List<BlogFavorite> getFavoriteArticleByUserId(@Param("userId") Long userId, @Param("articleName") String articleName, @Param("favoriteClassId") Long favoriteClassId, @Param("isPrivate") Integer isPrivate);

    Integer updateFavoritesActive(List<Long> ids);


    Integer addFavorites(BlogFavorite blogFavorite);

    BlogFavorite getFavoritesById(@Param("favoritesId") Long favoritesId);
}
