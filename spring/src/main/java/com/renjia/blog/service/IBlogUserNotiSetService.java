package com.renjia.blog.service;

import com.renjia.blog.pojo.BlogUserNotiSet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 用户消息推送状态 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogUserNotiSetService extends IService<BlogUserNotiSet> {
    /**
     * 获取用户的通知权限设置
     *
     * @param userId
     * @return
     */
    BlogUserNotiSet notisetByIdToSelect(String userId);

    /**
     * 修改用户的通知权限设置
     * @param blogUserNotiSet
     * @param userId
     * @return
     */
    Integer notisetByIdToUpdate(BlogUserNotiSet blogUserNotiSet,String userId);
}
