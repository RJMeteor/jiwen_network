package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.cache.DeleteRedisCacheAnnotation;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.mapper.BlogArticleLablesMapper;
import com.renjia.blog.service.IBlogArticleLablesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章标签 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogArticleLablesServiceImpl extends ServiceImpl<BlogArticleLablesMapper, BlogArticleLables> implements IBlogArticleLablesService {

    //获取标签列表
    @Override
//    @RedisCacheAnnotation(key = "BlogArticleLables-all", expireTime = 0)
    public List<BlogArticleLables> getLables() {
        LambdaQueryWrapper<BlogArticleLables> blogArticleLablesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        blogArticleLablesLambdaQueryWrapper.eq(BlogArticleLables::getDeleted, 0);
        return this.list(blogArticleLablesLambdaQueryWrapper);
    }

    /**
     * 编辑标签
     *
     * @param lableId   标签ID
     * @param lableName 标签名
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer editLable(Long lableId, String lableName) {
        LambdaUpdateWrapper<BlogArticleLables> blogArticleLablesLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        blogArticleLablesLambdaUpdateWrapper.eq(BlogArticleLables::getId, lableId).set(BlogArticleLables::getArtName, lableName);
        boolean update = this.update(blogArticleLablesLambdaUpdateWrapper);
        return update ? 1 : 0;
    }

    /**
     * 添加标签
     *
     * @param lables 标签名
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer addLables(List<String> lables) {
        ArrayList<BlogArticleLables> blogArticleLables = new ArrayList<>();
        for (String lable : lables) {
            BlogArticleLables blogArticleLables1 = new BlogArticleLables();
            blogArticleLables1.setArtName(lable);
            blogArticleLables.add(blogArticleLables1);
        }
        boolean update = this.saveBatch(blogArticleLables);
        return update ? 1 : 0;
    }

    /**
     * 查询标签信息
     *
     * @param lableName 标签名
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    @Override
    public PageInfo<BlogArticleLables> lableListByName(String lableName, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        LambdaQueryWrapper<BlogArticleLables> blogArticleLablesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        blogArticleLablesLambdaQueryWrapper.likeRight(BlogArticleLables::getArtName, lableName);
        List<BlogArticleLables> blogArticleLables = this.getBaseMapper().selectList(blogArticleLablesLambdaQueryWrapper);
        PageInfo<BlogArticleLables> pageInfo = new PageInfo<>(blogArticleLables);
        return pageInfo;
    }

    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer disabledLable(Long lableId) {
        LambdaUpdateWrapper<BlogArticleLables> lableUpdate = new LambdaUpdateWrapper<>();
        lableUpdate.eq(BlogArticleLables::getId, lableId).set(BlogArticleLables::getDeleted, 1);
        boolean update = this.update(lableUpdate);
        return update ? 1 : 0;
    }

    /**
     * 管理员批量禁用标签（拉黑）
     *
     * @param lableIds 标签列表id
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer disabledLableIds(List<Long> lableIds) {
        LambdaUpdateWrapper<BlogArticleLables> lableUpdate = new LambdaUpdateWrapper<>();
        lableUpdate.in(BlogArticleLables::getId, lableIds).set(BlogArticleLables::getDeleted, 1);
        boolean update = this.update(lableUpdate);
        return update ? 1 : 0;
    }

    /**
     * 管理员取消禁用标签（拉黑）
     *
     * @param lableId 标签Id
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer cancelDisabledLable(Long lableId) {
        LambdaUpdateWrapper<BlogArticleLables> lableUpdate = new LambdaUpdateWrapper<>();
        lableUpdate.eq(BlogArticleLables::getId, lableId).set(BlogArticleLables::getDeleted, 0);
        boolean update = this.update(lableUpdate);
        return update ? 1 : 0;
    }

    /**
     * 管理员批量取消禁用标签（拉黑）
     *
     * @param lableIds 标签Id列表
     * @return
     */
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticleLables, key = "BlogArticleLables-all", isDeleteAll = true)
    @Override
    public Integer cancelDisabledLableIds(List<Long> lableIds) {
        LambdaUpdateWrapper<BlogArticleLables> lableUpdate = new LambdaUpdateWrapper<>();
        lableUpdate.in(BlogArticleLables::getId, lableIds).set(BlogArticleLables::getDeleted, 0);
        boolean update = this.update(lableUpdate);
        return update ? 1 : 0;
    }
}
