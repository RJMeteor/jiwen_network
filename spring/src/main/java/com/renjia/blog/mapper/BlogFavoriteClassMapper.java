package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogFavoriteClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjia.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 收藏夹类别管理 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogFavoriteClassMapper extends BaseMapper<BlogFavoriteClass> {

    /**
     * @param favoriteClassId
     * @return
     */
    BlogFavoriteClass getFavoriteClassById(@Param("favoriteClassId")Integer favoriteClassId);
}
