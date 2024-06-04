package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogArticleLables;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 文章标签 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogArticleLablesMapper extends BaseMapper<BlogArticleLables> {

    /**
     * 根据文章标签id 获取文章标签信息
     * @param lablesId
     * @return
     */
    BlogArticleLables getArticleLablesByLablesId(@Param("lablesId")String lablesId);
}
