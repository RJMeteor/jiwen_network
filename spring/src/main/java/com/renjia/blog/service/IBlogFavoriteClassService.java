package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavoriteClass;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 收藏夹类别管理 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogFavoriteClassService extends IService<BlogFavoriteClass> {
    /**
     * 获取收藏夹
     *
     * @param userId
     * @param page
     * @param size
     * @param isPrivate
     * @return
     */
    PageInfo<BlogFavoriteClass> getFavoriteClassByUserid(Long userId,Integer isPrivate,String favoriteName, Integer page, Integer size);

    /**
     * 添加收藏夹
     *
     * @param userId       用户id
     * @param favoriteName 文件夹名
     * @return
     */
    Integer addFavoriteByUserId(Long userId, String favoriteName,Integer isPrivate);

    /**
     * 批量删除收藏夹
     *
     * @param ids
     * @return
     */
    Integer deleteFavoriteClassById(List<Long> ids);
}
