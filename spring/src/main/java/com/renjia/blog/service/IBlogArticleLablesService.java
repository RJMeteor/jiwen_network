package com.renjia.blog.service;

import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticleLables;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 文章标签 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogArticleLablesService extends IService<BlogArticleLables> {
    //获取标签列表
    public List<BlogArticleLables> getLables();

    /**
     * 编辑标签
     * @param lableId 标签ID
     * @param lableName 标签名
     * @return
     */
    public Integer editLable(Long lableId,String lableName);

    /**
     * 添加标签
     * @param lables 标签名
     * @return
     */
    public Integer addLables(List<String> lables) ;
    /**
     * 查询标签信息
     *
     * @param lableName 标签名
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    public PageInfo<BlogArticleLables> lableListByName( String lableName,Integer page, Integer size) ;


    /**
     * 管理员禁用标签（拉黑）
     *
     * @param lableId 标签Id
     * @return
     */
    public Integer disabledLable(Long lableId) ;

    /**
     * 管理员批量禁用标签（拉黑）
     *
     * @param lableIds 标签列表id
     * @return
     */
    public Integer disabledLableIds(List<Long> lableIds);


    /**
     * 管理员取消禁用标签（拉黑）
     *
     * @param lableId 标签Id
     * @return
     */
    public Integer cancelDisabledLable(Long lableId);

    /**
     * 管理员批量取消禁用标签（拉黑）
     *
     * @param lableIds 标签Id列表
     * @return
     */
    public Integer cancelDisabledLableIds(List<Long> lableIds);
}
