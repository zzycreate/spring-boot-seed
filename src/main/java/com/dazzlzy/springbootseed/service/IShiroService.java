package com.dazzlzy.springbootseed.service;

import com.dazzlzy.springbootseed.model.user.User;

/**
 * Shiro认证Service
 *
 * @author zhaozhenyao
 * @date 2018/5/8
 */
public interface IShiroService {

    /**
     * shiro认证登陆，进行密码及合法性校验
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @param password 密码
     * @return 若返回为null或者抛出异常，则认证失败，若返回有值，则认证成功
     */
    User login(Long userId, String userName, String password);

}
