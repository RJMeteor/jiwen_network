package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogFavoriteClass;
import com.renjia.blog.mapper.BlogFavoriteClassMapper;
import com.renjia.blog.service.IBlogFavoriteClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏夹类别管理 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogFavoriteClassServiceImpl extends ServiceImpl<BlogFavoriteClassMapper, BlogFavoriteClass> implements IBlogFavoriteClassService {

    @Override
    public PageInfo<BlogFavoriteClass> getFavoriteClassByUserid(Long userId, Integer isPrivate,String favoriteName, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        LambdaQueryWrapper<BlogFavoriteClass> eq = new LambdaQueryWrapper<BlogFavoriteClass>().
                eq(BlogFavoriteClass::getUserId, userId)
                .likeRight(BlogFavoriteClass::getFavoriteClassName,favoriteName)
                .eq(BlogFavoriteClass::getDeleted, 0)
                .orderByDesc(BlogFavoriteClass::getUpdateTime);
        if (isPrivate != null && isPrivate.equals(1)) {
            eq.eq(BlogFavoriteClass::getFavoriteClassPrivacy, 0);
        }
        List<BlogFavoriteClass> list = this.list(eq);
        PageInfo<BlogFavoriteClass> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer addFavoriteByUserId(Long userId, String favoriteName, Integer isPrivate) {
        BlogFavoriteClass blogFavoriteClass = new BlogFavoriteClass();
        blogFavoriteClass.setFavoriteClassName(favoriteName);
        blogFavoriteClass.setUserId(userId);
        blogFavoriteClass.setFavoriteClassPrivacy(isPrivate);
        int insert = this.getBaseMapper().insert(blogFavoriteClass);
        return insert;
    }

    @Override
    public Integer deleteFavoriteClassById(List<Long> ids) {
        int i = this.getBaseMapper().deleteBatchIds(ids);
        return i;
    }

}
