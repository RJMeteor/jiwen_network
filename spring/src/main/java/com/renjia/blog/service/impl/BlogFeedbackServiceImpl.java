package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.mapper.BlogArticleContentMapper;
import com.renjia.blog.mapper.BlogArticleMapper;
import com.renjia.blog.mapper.BlogFeedbackContentMapper;
import com.renjia.blog.pojo.*;
import com.renjia.blog.mapper.BlogFeedbackMapper;
import com.renjia.blog.service.IBlogArticleLableClassService;
import com.renjia.blog.service.IBlogFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 反馈 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogFeedbackServiceImpl extends ServiceImpl<BlogFeedbackMapper, BlogFeedback> implements IBlogFeedbackService {

    @Resource
    private BlogFeedbackMapper blogFeedbackMapper;
    @Resource
    private BlogFeedbackContentMapper blogFeedbackContentMapper;

    @Override
    public Integer addFeedback(Long userId, String feedbackContent) {
        BlogFeedbackContent blogFeedbackContent = new BlogFeedbackContent();
        blogFeedbackContent.setFeedbackContent(feedbackContent);
        Integer integer = blogFeedbackContentMapper.inserFeedbackContent(blogFeedbackContent);
        BlogFeedback blogFeedback = new BlogFeedback();
        blogFeedback.setUserId(userId);
        if (integer > 0) {
            blogFeedback.setFeedbackContentId(blogFeedbackContent.getId());
            boolean integer1 = this.save(blogFeedback);
            if (integer1) {
                return 1;
            }
        }
        return 0;
    }


    @Override
    public List<BlogFeedback> feedbackListAndUserId(Long userId) {

        return blogFeedbackMapper.getFeedbackAndUserId(userId);
    }

    @Override
    public PageInfo<BlogFeedback> feedbackListAndUserName(String userName, String startTime, String endTime, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        if (!ObjectUtils.isEmpty(startTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                List<BlogFeedback> feedbackAndUserNameAndTime = blogFeedbackMapper.getFeedbackAndUserNameAndTime(userName, simpleDateFormat.parse(startTime), simpleDateFormat.parse(endTime));
                PageInfo<BlogFeedback> pageInfo = new PageInfo<>(feedbackAndUserNameAndTime);
                return pageInfo;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            List<BlogFeedback> feedbackAndUserNameAndTime = blogFeedbackMapper.getFeedbackAndUserNameAndTime(userName, null, null);
            PageInfo<BlogFeedback> pageInfo = new PageInfo<>(feedbackAndUserNameAndTime);
            return pageInfo;
        }
        return null;
    }

    @Override
    public Integer deleteFeedbackId(Long feedbackId) {
        int i = this.getBaseMapper().deleteById(feedbackId);
        return i;
    }

    @Override
    public Integer deleteFeedbackIds(List<Long> feedbackIds) {
        int i = this.getBaseMapper().deleteBatchIds(feedbackIds);
        return i;
    }

    @Override
    public Integer activeFeedbackId(Long feedbackId) {
        LambdaUpdateWrapper<BlogFeedback> blogFeedbackLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        blogFeedbackLambdaUpdateWrapper.eq(BlogFeedback::getId, feedbackId).set(BlogFeedback::getActive, 1);
        boolean update = this.update(blogFeedbackLambdaUpdateWrapper);
        return update ? 1 : 0;
    }

    @Override
    public Integer activeFeedbackIds(List<Long> feedbackIds) {
        LambdaUpdateWrapper<BlogFeedback> blogFeedbackLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        blogFeedbackLambdaUpdateWrapper.in(BlogFeedback::getId, feedbackIds).set(BlogFeedback::getActive, 1);
        boolean update = this.update(blogFeedbackLambdaUpdateWrapper);
        return update ? 1 : 0;
    }
}
