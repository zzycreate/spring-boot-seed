package com.dazzlzy.springbootseed.service;

import com.dazzlzy.springbootseed.model.user.User;

import java.util.List;

/**
 * 用户 Service
 *
 * @author zhaozhenyao
 * @date 2018/5/8
 */
public interface IUserService {

    /**
     * 根据用户ID或者用户名查询用户
     *
     * @param userId   用户Id
     * @param userName 用户名
     * @return
     */
    User queryByIdOrName(Long userId, String userName);

    /**
     * 据用户ID查询用户信息（包括权限角色）
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User queryUserById(Long userId);

    /**
     * 根据用户ID集合查询用户信息（包括权限角色）
     *
     * @param userIds 用户ID集合
     * @return 用户信息
     */
    List<User> queryUserByIds(List<Long> userIds);

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    void addUser(User user);

}
