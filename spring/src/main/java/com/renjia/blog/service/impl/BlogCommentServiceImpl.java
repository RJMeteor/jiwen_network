package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.cache.DeleteRedisCacheAnnotation;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
import com.renjia.blog.mapper.BlogCommentContentMapper;
import com.renjia.blog.mapper.BlogCommentMapper;
import com.renjia.blog.mq.producer.ProductionStrategy;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.pojo.BlogComment;
import com.renjia.blog.pojo.BlogCommentContent;
import com.renjia.blog.service.IBlogCommentService;
import com.renjia.blog.util.exceptions.OtherException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {
    @Resource
    private BlogCommentMapper blogCommentMapper;
    @Resource
    private BlogCommentContentMapper blogCommentContentMapper;


    @Autowired
    private ProductionStrategy productionStrategy;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @RedisCacheAnnotation(key = "'comment-'+#articleId+'-'+#commentKeyId+'-'+#page+'-'+#size")
    @Override
    public PageInfo<BlogComment> getCommentByArticleId(Long articleId, Long commentKeyId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogComment> commentsByArticleId = blogCommentMapper.getCommentsByArticleId(articleId, commentKeyId);
        PageInfo<BlogComment> pageInfo = new PageInfo<>(commentsByArticleId);
        return pageInfo;
    }


    @Override
    public BlogComment addComment(BlogComment blogComment) {
        BlogCommentContent blogCommentContent = blogComment.getCommentContent();
        BlogComment execute = transactionTemplate.execute((transactionStatus) ->
                Optional.ofNullable(blogComment).flatMap(comment -> {
                    blogCommentContentMapper.addCommentContent(blogCommentContent);
                   comment.setCommentContentId(blogCommentContent.getId());
                    return Optional.ofNullable(comment).filter(comment1 -> !ObjectUtils.isEmpty(blogCommentContent.getId()));
                }).flatMap(comment -> {
                    blogCommentMapper.addComment(comment);
                    return Optional.ofNullable(comment).filter(comment1 -> !ObjectUtils.isEmpty(comment.getId()));
                }).flatMap(comment -> {
                    BlogComment commentById = blogCommentMapper.getCommentById(blogComment.getId());
                    if (blogComment.getPerson().getId() != 0) {
                        MessageConsume message = new MessageConsume(MessageConsume.MessageType.COMMENT.getType(), JSON.toJSONString(blogComment.getId()));
                        productionStrategy.sendMessage(message.getType(), message);
                    }
                    return Optional.ofNullable(commentById);
                }).orElseThrow(() -> new OtherException("评论失败")));
        return execute;
    }

    @Override
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogComment, key = "#commentId")
    public Integer deleteComment(Long commentId) {
        int i = this.getBaseMapper().deleteById(commentId);
        return i;
    }

    @Override
    public Integer deleteComments(List<Long> ids) {
        int i = blogCommentMapper.updateCommentsActive(ids);
        return i;
    }

    @Override
    public PageInfo<BlogComment> getCommentNofit(Long personId, Long userId, Long articleId, Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<BlogComment> commentsByArticleId = blogCommentMapper.getCommentNofit(personId, userId, articleId);
        PageInfo<BlogComment> pageInfo = new PageInfo<>(commentsByArticleId);
        return pageInfo;
    }

    @Override
    public PageInfo<BlogComment> getCommentByOtherPersonId(Long personId, Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<BlogComment> commentsByArticleId = blogCommentMapper.getCommentByOtherPersonId(personId);
        PageInfo<BlogComment> pageInfo = new PageInfo<>(commentsByArticleId);
        return pageInfo;
    }

    @Override
    public PageInfo<BlogComment> getCommentByOtherPersonIdAndUserId(Long personId, Long userId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogComment> commentsByArticleId = blogCommentMapper.getCommentByOtherPersonIdAndUserId(personId, userId);
        PageInfo<BlogComment> pageInfo = new PageInfo<>(commentsByArticleId);
        return pageInfo;
    }


    @Override
    public Integer updataCommentAvtiveByPersonIdAndUserId(Long personId, Long userId) {
        LambdaUpdateWrapper<BlogComment> blogCommentLambdaQueryWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<BlogComment> set = blogCommentLambdaQueryWrapper.eq(BlogComment::getPersonId, personId).eq(BlogComment::getUserId, userId).set(BlogComment::getStated, 1);
        boolean update = this.update(blogCommentLambdaQueryWrapper);

        return update ? 1 : 0;
    }
}
