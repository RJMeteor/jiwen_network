package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogArticleContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 文章内容映射 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogArticleContentMapper extends BaseMapper<BlogArticleContent> {


    Integer inserArticleContent(BlogArticleContent blogArticleContent);


    BlogArticleContent getArticleContentById(@Param("articleContentId")String articleContentId);

}
