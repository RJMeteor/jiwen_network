package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticleLables;
import com.renjia.blog.pojo.BlogBackMessages;
import com.renjia.blog.mapper.BlogBackMessagesMapper;
import com.renjia.blog.service.IBlogBackMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台消息通知 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogBackMessagesServiceImpl extends ServiceImpl<BlogBackMessagesMapper, BlogBackMessages> implements IBlogBackMessagesService {

    /**
     * 添加公告
     * @param backContent 公告内容
     * @return
     */
    @Override
    public Integer addBack(String backContent) {
        BlogBackMessages blogBackMessages = new BlogBackMessages();
        blogBackMessages.setBackContent(backContent);
        boolean update = this.save(blogBackMessages);
        return update ? 1 : 0;
    }

    /**
     * 编辑公告
     * @param backId 公告ID
     * @param backName 公告名字
     * @return
     */
    @Override
    public Integer editBack(Long backId, String backName) {
        LambdaUpdateWrapper<BlogBackMessages> logBackMessagesLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        logBackMessagesLambdaUpdateWrapper.eq(BlogBackMessages::getId, backId).set(BlogBackMessages::getBackContent, backName);
        boolean update = this.update(logBackMessagesLambdaUpdateWrapper);
        return update ? 1 : 0;
    }

    @Override
    public PageInfo<BlogBackMessages> backList(String backName, String startTime, String endTime, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        LambdaQueryWrapper<BlogBackMessages> logBackLambdaQueryWrapper = new LambdaQueryWrapper<>();
        logBackLambdaQueryWrapper.likeRight(BlogBackMessages::getBackContent, backName);
        if (!ObjectUtils.isEmpty(startTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                logBackLambdaQueryWrapper.ge(BlogBackMessages::getCreateTime, simpleDateFormat.parse(startTime))
                        .lt(BlogBackMessages::getCreateTime, simpleDateFormat.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<BlogBackMessages> blogArticleLables = this.getBaseMapper().selectList(logBackLambdaQueryWrapper);
        PageInfo<BlogBackMessages> pageInfo = new PageInfo<>(blogArticleLables);
        return pageInfo;
    }

    /**
     * 管理员删除公告
     *
     * @param backId 公告Id
     * @return
     */
    @Override
    public Integer deleteBackId(Long backId) {
        boolean update = this.removeById(backId);
        return update ? 1 : 0;
    }

    /**
     * 管理员批量删除公告
     *
     * @param backIds 公告列表id
     * @return
     */
    @Override
    public Integer disabledBackIds(List<Long> backIds) {
        int update = this.getBaseMapper().deleteBatchIds(backIds);
        return update;
    }
}
