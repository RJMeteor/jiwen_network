package com.renjia.blog.service;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renjia.blog.util.MailUtil;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.MailMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface IBlogUserService extends IService<BlogUser> {

    String CACHENAME = "user";

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param userEmail 用户邮箱
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    public PageInfo<BlogUser> userlistByNameAndEmail(String userName,
                                                     String userEmail,
                                                     Integer page,
                                                     Integer size);

    /**
     * 管理员禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    public Integer disabledUser(Long userId);

    /**
     * 管理员批量禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    public Integer disabledUserIds(List<Long> userIds);

    /**
     * 管理员取消禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    public Integer cancelDisabledUser(Long userId);

    /**
     * 管理员批量取消禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    public Integer cancelDisabledUserIds(List<Long> userIds);

    /**
     * 根据用户id获取单个用户信息
     *
     * @param userId
     * @return
     */
    public BlogUser userByIdToSelect(Integer userId);


    //    @CachePut(cacheNames = CACHENAME, key = "#result.id", condition = "#result != null ")

    /**
     * 根据用户名或邮箱获取，密码用户信息
     *
     * @param user
     * @return
     */
    BlogUser userByNameEmailAndPassToSelect(BlogUser user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Integer userByInsert(BlogUser user);

    /**
     * 根据用户邮箱更新密码
     *
     * @param user
     * @return
     */
    Integer userByEmailToPassUpdate(BlogUser user);


    /**
     * 根据用户id更新用户信息
     *
     * @param user
     * @return
     */
    Integer userByIdToAllUpdate(BlogUser user);

    /**
     * 上传文件
     *
     * @param file
     * @param userId
     * @return
     * @throws Exception
     */
    String uploadImg(MultipartFile file, Integer userId, String imageUrl) throws Exception;


    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    Integer updateUser(BlogUser user);

}
