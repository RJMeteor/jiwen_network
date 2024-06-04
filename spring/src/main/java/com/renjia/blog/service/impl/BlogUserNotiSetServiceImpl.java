package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.renjia.blog.pojo.BlogUserNotiSet;
import com.renjia.blog.mapper.BlogUserNotiSetMapper;
import com.renjia.blog.service.IBlogUserNotiSetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户消息推送状态 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogUserNotiSetServiceImpl extends ServiceImpl<BlogUserNotiSetMapper, BlogUserNotiSet> implements IBlogUserNotiSetService {
    @Override
    public BlogUserNotiSet notisetByIdToSelect(String userId) {
        LambdaQueryWrapper<BlogUserNotiSet> eq = new LambdaQueryWrapper<BlogUserNotiSet>()
                .eq(BlogUserNotiSet::getUserId, userId);
        BlogUserNotiSet blogUserNotiSet = this.baseMapper.selectOne(eq);
        return blogUserNotiSet;
    }

    @Override
    public Integer notisetByIdToUpdate(BlogUserNotiSet blogUserNotiSet, String userId) {
        blogUserNotiSet.setUserId(Long.valueOf(userId));
        LambdaUpdateWrapper<BlogUserNotiSet> qu = new LambdaUpdateWrapper<BlogUserNotiSet>()
                .eq(BlogUserNotiSet::getUserId, Long.valueOf(userId));
        LambdaUpdateWrapper<BlogUserNotiSet> eq = new LambdaUpdateWrapper<BlogUserNotiSet>()
                .eq(BlogUserNotiSet::getUserId, Long.valueOf(userId));
        int update = 0;
        if (ObjectUtils.isEmpty(this.baseMapper.selectOne(qu))) {
            blogUserNotiSet.setUserId(Long.valueOf(userId));
            update = this.save(blogUserNotiSet) ? 1 : 0;

        } else {
            update = this.baseMapper.update(blogUserNotiSet, eq);
        }
        return update;
    }
}
