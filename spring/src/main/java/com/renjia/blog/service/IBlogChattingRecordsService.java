package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogChattingRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.pojo.BlogComment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 聊天记录 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogChattingRecordsService extends IService<BlogChattingRecords> {
   /**
    *
    * @param blogChattingRecords 聊天实体
    * @return
    */
   BlogChattingRecords addChat(BlogChattingRecords blogChattingRecords);

   /**
    * 获取用户的聊天内容
    * @param personUserId 接受方用户ID
    * @param sendUserId 发送方用户ID
    * @param page 第几页
    * @param size 每页大小
    * @return
    */
   PageInfo<BlogChattingRecords> getChat(Long personUserId,Long sendUserId,Integer page,Integer size);
   /**
    * 除了博主自己以外的私信
    *
    * @param personId 接受方用户ID
    * @return
    */
   PageInfo<BlogChattingRecords> getChatByOtherPersonId(Long personId, Integer page, Integer size);


   /**
    * 批量删除推送列表的聊天数据
    *
    * @param ids
    * @return
    */
   Integer deleteChats(List<Long> ids);

   /**
    * 更新聊天已读状态
    *
    * @param personId 被评论的用户ID
    * @param userId 评论的用户ID
    * @return
    */
   Integer updataChatAvtiveByPersonIdAndUserId(Long personId, Long userId);
}
