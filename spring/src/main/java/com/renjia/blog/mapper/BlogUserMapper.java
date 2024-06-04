package com.renjia.blog.mapper;

import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.pojo.BlogUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
public interface BlogUserMapper extends BaseMapper<BlogUser> {


    BlogUser userByNameEmailAndPassToSelect(BlogUser user);

    Integer userByInsert(BlogUser user);

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param userEmail 用户邮箱
     **/
    ArrayList<BlogUser> userlistByNameAndEmail(@Param("userName") String userName, @Param("userEmail") String userEmail);

    /**
     * 根据用户id用户数据
     *
     * @param userId
     * @return
     */
    BlogUser getUserById(@Param("userId") Integer userId);

    Long getUserId(@Param("userId") Long userId);

}
