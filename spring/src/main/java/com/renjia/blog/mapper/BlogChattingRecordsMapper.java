package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogChattingRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjia.blog.pojo.BlogComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 聊天记录 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogChattingRecordsMapper extends BaseMapper<BlogChattingRecords> {

    BlogChattingRecords getChat(@Param("chatId") Long chatId);

    Integer addChat(BlogChattingRecords blogChattingRecords);

    /**
     * 获取用户的全部私信
     *
     * @param acceptorUserId
     * @param senderUserId
     * @return
     */
    List<BlogChattingRecords> getChatNofit(@Param("acceptorUserId") Long acceptorUserId, @Param("senderUserId") Long senderUserId);

    /**
     * 除了博主自己以外的私信
     *
     * @param acceptorUserId
     * @return
     */
    List<BlogChattingRecords> getChatByOtherPersonId(@Param("acceptorUserId") Long acceptorUserId);

    /**
     * 删除列表的私信 set  active =1
     *
     * @param ids
     * @return
     */
    Integer updateChatsActive(List<Long> ids);

}
