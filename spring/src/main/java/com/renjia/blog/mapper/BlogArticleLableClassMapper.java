package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogArticleLableClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 文章标签类别 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogArticleLableClassMapper extends BaseMapper<BlogArticleLableClass> {

    /**
     * 根据文章id 获取文章标签列表
     * @param articleId
     * @return
     */
    List<BlogArticleLableClass> getArticleLableClassssByArticleId(@Param("articleId")String articleId);
}
