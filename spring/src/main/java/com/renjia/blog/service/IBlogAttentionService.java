package com.renjia.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogAttention;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 关注 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogAttentionService extends IService<BlogAttention> {


     Integer  addAttention(Long personId, Long userId);


    Integer deleteAttention(Long personId, Long userId);

    /**
     * 获取除自己外关注在消息列表里的
     *
     * @param personId
     * @param page
     * @param size
     * @return
     */
    PageInfo<BlogAttention> getAttentionByPersonId(Long personId, Integer page, Integer size);

    /**
     * 获取自己关注
     *
     * @param childrenId
     */
    PageInfo<BlogAttention> getAttentions(Long childrenId, Integer page, Integer size);

    /**
     * 获取自己粉丝
     *
     * @param personId
     */
    PageInfo<BlogAttention> getFens(Long personId, Integer page, Integer size);

    /**
     * 从消息列表中提出，active=1
     *
     * @param ids
     */
    Integer updateAttentionActive(List<Long> ids);

   Integer deleteAttentions( List<Long> ids);

    //获取热门用户
    List<BlogAttention> getOhtUser();
}
