package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.mapper.BlogChattingRecordsContentMapper;
import com.renjia.blog.mapper.BlogChattingRecordsMapper;
import com.renjia.blog.mq.producer.ProductionStrategy;
import com.renjia.blog.netty.MessageConsume;
import com.renjia.blog.netty.WebSocketHandler;
import com.renjia.blog.pojo.BlogChattingRecords;
import com.renjia.blog.pojo.BlogChattingRecordsContent;
import com.renjia.blog.service.IBlogChattingRecordsService;
import com.renjia.blog.util.TrieSearcherUtil;
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
 * 聊天记录 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogChattingRecordsServiceImpl extends ServiceImpl<BlogChattingRecordsMapper, BlogChattingRecords> implements IBlogChattingRecordsService {
    @Resource
    private BlogChattingRecordsMapper blogChattingRecordsMapper;
    @Resource
    private BlogChattingRecordsContentMapper blogChattingRecordsContentMapper;

    @Autowired
    private ProductionStrategy productionStrategy;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TrieSearcherUtil trieSearcherUtil;

    @Override
    public BlogChattingRecords addChat(BlogChattingRecords blogChattingRecords) {
        String replace = trieSearcherUtil.replace(blogChattingRecords.getChattingRecordsContent().getChattingRecordsContent(), "****");
        blogChattingRecords.getChattingRecordsContent().setChattingRecordsContent(replace);

        BlogChattingRecords chat1 = transactionTemplate.execute((transactionStatus) ->
                Optional.ofNullable(blogChattingRecords).flatMap(blog -> {
                    BlogChattingRecordsContent chattingRecordsContent = blog.getChattingRecordsContent();
                    blogChattingRecordsContentMapper.addContent(chattingRecordsContent);
                    blog.setChattingRecordsContentId(chattingRecordsContent.getId());
                    return Optional.ofNullable(blog).filter(blog1 -> !ObjectUtils.isEmpty(blog1.getChattingRecordsContent().getId()));
                }).flatMap(blog -> {
                    blogChattingRecordsMapper.addChat(blog);
                    return Optional.ofNullable(blog.getId());
                }).flatMap(id -> {
                    BlogChattingRecords chat = blogChattingRecordsMapper.getChat(id);
                    if (chat.getAcceptorUser().getId() != null) {
                        MessageConsume message = new MessageConsume(MessageConsume.MessageType.CHATTINGRECORDS.getType(), JSON.toJSONString(id));
                        productionStrategy.sendMessage(message.getType(), message);
                    }
                    return Optional.ofNullable(chat);
                }).orElseThrow(() -> new OtherException("评论添加失败")));
        return chat1;
    }

    @Override
    public PageInfo<BlogChattingRecords> getChat(Long personUserId, Long sendUserId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogChattingRecords> chatNofit = blogChattingRecordsMapper.getChatNofit(personUserId, sendUserId);
        PageInfo<BlogChattingRecords> pageInfo = new PageInfo<>(chatNofit);
        return pageInfo;
    }

    @Override
    public PageInfo<BlogChattingRecords> getChatByOtherPersonId(Long personId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogChattingRecords> commentsByArticleId = blogChattingRecordsMapper.getChatByOtherPersonId(personId);
        PageInfo<BlogChattingRecords> pageInfo = new PageInfo<>(commentsByArticleId);
        return pageInfo;
    }

    @Override
    public Integer deleteChats(List<Long> ids) {
        int i = blogChattingRecordsMapper.updateChatsActive(ids);
        return i;
    }

    @Override
    public Integer updataChatAvtiveByPersonIdAndUserId(Long personId, Long userId) {
        LambdaUpdateWrapper<BlogChattingRecords> blogChatLambdaQueryWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<BlogChattingRecords> set = blogChatLambdaQueryWrapper.eq(BlogChattingRecords::getAcceptorUserId, personId).eq(BlogChattingRecords::getSenderUserId, userId).set(BlogChattingRecords::getReadStatus, 1);
        boolean update = this.update(blogChatLambdaQueryWrapper);
        return update ? 1 : 0;
    }
}
