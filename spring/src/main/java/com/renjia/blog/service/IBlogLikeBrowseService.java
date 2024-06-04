package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogFavorite;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 点赞/浏览 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface
IBlogLikeBrowseService extends IService<BlogLikeBrowse> {
   Integer addLikeByArticleId(Long articlesId,Long userId, Long  personId,Integer isLike);
   Integer deleteLikeByArticleIdAndUserIdAndpersonId(Long articlesId,Long userId);

   /**
    * 获取排除自身的点赞，查看别人点赞了你那些内容
    * @param personId
    * @param page
    * @param size
    * @return
    */
   PageInfo<BlogLikeBrowse> getLikeByPersonId(Long personId, Integer page, Integer size);

   /**
    * 从消息列表中提出，active=1
    * @param ids
    */
   Integer updateLikeActive(List<Long> ids);


   //获取热门文章
   List<BlogLikeBrowse> getOhtArticle();


   //获取热门话题
   List<BlogLikeBrowse> getOhtTopic();
}
