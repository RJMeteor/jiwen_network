package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.pojo.BlogFavoriteClass;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 收藏夹 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogFavoriteService extends IService<BlogFavorite> {



    /**
     * 获取用户收藏夹的收藏内容
     *
     * @param userId 用户id
     * @param articleName 文章名
     * @param favoriteClassId 收藏夹ID
     * @param isPrivate 是否私有
     * @param page 第几页
     * @param size 每页大小
     * @return
     */
    PageInfo<BlogFavorite> getFavoriteArticleByUserId(Long userId,
                                                      String articleName,
                                                      Long favoriteClassId,
                                                      Integer isPrivate,
                                                      Integer page,
                                                      Integer size);

    /**
     * 删除用户收藏的文章
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return
     */
    Integer deleteFavoriteByUserIdAndArticleId(Long userId, Long articleId);

    /**
     * 用户收藏的文章
     * @param userId 收藏用户ID
     * @param personId 被收藏用户ID
     * @param favoriteClassId 收藏夹ID
     * @param articleId 文章ID
     * @return
     */
    Integer addFavoriteByUserIdAndArticleIdAndFavoriteId(Long userId, Long personId, Long favoriteClassId, Long articleId);

    /**
     * 批量删除收藏
     *
     * @param ids 收藏夹id
     * @return
     */
    Integer deleteFavoriteArticleById(List<Long> ids);

    /**
     * 获取博主自己收藏内容
     * @param personId
     * @param page
     * @param size
     * @return
     */
    PageInfo<BlogFavorite> getFavoritesByPersonId(Long personId, Integer page, Integer size);

    /**
     * 删除其他人收藏我的文章的推送列表消息，active=1
     * @param ids
     */
    Integer updateFavoritesActive(List<Long> ids);


}
