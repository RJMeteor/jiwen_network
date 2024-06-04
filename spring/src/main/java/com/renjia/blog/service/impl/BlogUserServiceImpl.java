package com.renjia.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.other.AdditionalConfig;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.mapper.BlogUserMapper;
import com.renjia.blog.pojo.BlogUserNotiSet;
import com.renjia.blog.service.IBlogUserNotiSetService;
import com.renjia.blog.service.IBlogUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjia.blog.util.OSSCloudClient;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements IBlogUserService {
    @Resource
    private BlogUserMapper blogUserMapper;
    @Resource
    private IBlogUserNotiSetService iBlogUserNotiSetService;

    /**
     * 管理员批量禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    @Override
    public Integer disabledUserIds(List<Long> userIds) {
        LambdaUpdateWrapper<BlogUser> blogUserUpdate = new LambdaUpdateWrapper<>();
        blogUserUpdate.in(BlogUser::getId, userIds).
                set(BlogUser::getDeleted, 1);
        boolean update = this.update(blogUserUpdate);
        return update ? 1 : 0;
    }
    /**
     * 管理员批量取消禁用用户（拉黑）
     *
     * @param userIds 用户Id列表
     * @return
     */
    @Override
    public Integer cancelDisabledUserIds(List<Long> userIds) {
        LambdaUpdateWrapper<BlogUser> blogUserUpdate = new LambdaUpdateWrapper<>();
        blogUserUpdate.in(BlogUser::getId, userIds).
                set(BlogUser::getDeleted, 0);
        boolean update = this.update(blogUserUpdate);
        return update ? 1 : 0;
    }

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param userEmail 用户邮箱
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    @Override
    public PageInfo<BlogUser> userlistByNameAndEmail(String userName, String userEmail, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogUser> blogUsers = blogUserMapper.userlistByNameAndEmail(userName, userEmail);
        PageInfo<BlogUser> pageInfo = new PageInfo<>(blogUsers);
        return pageInfo;
    }

    /**
     * 管理员禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    @Override
    public Integer disabledUser(Long userId) {
        LambdaUpdateWrapper<BlogUser> userUpdate = new LambdaUpdateWrapper<>();
        userUpdate.eq(BlogUser::getId, userId).set(BlogUser::getDeleted, 1);
        boolean update = this.update(userUpdate);
        return update ? 1 : 0;
    }

    /**
     * 管理员取消禁用用户（拉黑）
     *
     * @param userId 用户Id
     * @return
     */
    @Override
    public Integer cancelDisabledUser(Long userId) {
        LambdaUpdateWrapper<BlogUser> userUpdate = new LambdaUpdateWrapper<>();
        userUpdate.eq(BlogUser::getId, userId).set(BlogUser::getDeleted, 0);
        boolean update = this.update(userUpdate);
        return update ? 1 : 0;
    }
    /**
     * 根据用户id获取单个用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public BlogUser userByIdToSelect(Integer userId) {
        BlogUser blogUser = blogUserMapper.getUserById(userId);
        return blogUser;
    }

    @Override
    public BlogUser userByNameEmailAndPassToSelect(BlogUser user) {
        BlogUser blogUser = blogUserMapper.userByNameEmailAndPassToSelect(user);
        return blogUser;
    }

    @Override
    public Integer userByInsert(BlogUser user) {
        user.setUserImage(AdditionalConfig.avatar);
        int insert = blogUserMapper.userByInsert(user);
        if (insert > 0) {
            BlogUserNotiSet blogUserNotiSet = new BlogUserNotiSet();
            blogUserNotiSet.setUserId(user.getId());
            blogUserNotiSet.setCollectActive(0);
            blogUserNotiSet.setCommentActive(0);
            blogUserNotiSet.setFollowActive(0);
            blogUserNotiSet.setLikeActive(0);
            blogUserNotiSet.setPrivateLetterActive(0);
            iBlogUserNotiSetService.save(blogUserNotiSet);
        }
        return insert;
    }

    @Override
    public Integer userByEmailToPassUpdate(BlogUser user) {
        LambdaUpdateWrapper<BlogUser> set = new LambdaUpdateWrapper<BlogUser>()
                .eq(BlogUser::getUserEmail, user.getUserEmail())
                .set(BlogUser::getUserPass, user.getUserPass());
        boolean update = this.update(set);
        return update?1:0;
    }

    @Override
    public Integer userByIdToAllUpdate(BlogUser user) {
        int i = this.baseMapper.updateById(user);
        return i;
    }

    @Override
    public String uploadImg(MultipartFile file, Integer userId, String imageUrl) throws Exception {
        OSSCloudClient client = OSSCloudClient.getInstance();
        String filePath = client.uploadFile(file.getOriginalFilename(), userId + "", file.getInputStream());
        if (!ObjectUtils.isEmpty(filePath)) {
            LambdaUpdateWrapper<BlogUser> set = new LambdaUpdateWrapper<BlogUser>()
                    .eq(BlogUser::getId, userId)
                    .set(BlogUser::getUserImage, filePath);
            boolean update = this.update(set);
            if (filePath != null && !filePath.equals(AdditionalConfig.avatar)) {
                client.deleteFile(imageUrl);
            }
            if (update) {
                return filePath;
            }
        }
        return null;
    }

    @Override
    public Integer updateUser(BlogUser user) {
        int i = this.getBaseMapper().updateById(user);
        return i;
    }
}
