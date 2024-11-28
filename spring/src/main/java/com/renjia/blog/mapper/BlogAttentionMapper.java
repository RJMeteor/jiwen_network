package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogAttention;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 关注 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogAttentionMapper extends BaseMapper<BlogAttention> {

    /**
     * 获取排除自身的收藏，查看别人收藏了你那些内容
     *
     * @param personId
     */
    List<BlogAttention> getAttentionByPersonId(@Param("personId") Long personId);


    /**
     * 获取自己关注
     *
     * @param childrenId
     */
    List<BlogAttention> getAttentions(@Param("childrenId") Long childrenId);

    /**
     * 获取自己粉丝
     *
     * @param personId
     */
    List<BlogAttention> getFens(@Param("personId") Long personId);


    /**
     * 获取自己关注UserID
     *
     * @param childrenId
     */
    List<Long> getAttentionUserIds(@Param("childrenId") Long childrenId);

    /**
     * 获取自己粉丝UserID
     *
     * @param personId
     */
    List<Long> getFenUserIds(@Param("personId") Long personId);

    /**
     * active=1
     *
     * @param ids
     */
    Integer updateAttentionActive(List<Long> ids);

    BlogAttention getAttentionsById(@Param("attentionsId") Long attentionsId);

    Integer addAttentions(BlogAttention blogAttention);

    //获取热门用户
    List<BlogAttention> getOhtUser(List<Integer> list);
}
