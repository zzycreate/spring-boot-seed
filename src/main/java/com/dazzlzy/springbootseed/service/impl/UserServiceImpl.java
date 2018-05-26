package com.dazzlzy.springbootseed.service.impl;

import com.dazzlzy.springbootseed.dao.user.UserMapper;
import com.dazzlzy.springbootseed.model.user.User;
import com.dazzlzy.springbootseed.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service
 *
 * @author zhaozhenyao
 * @date 2018/5/9
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    /**
     * 由于Mapper的实现类是Mybatis运行时通过Mapper.xml代理出来的类，IDE无法检测到实现类，会有代码提示
     *
     * @param userMapper 注入的UserMapper
     */
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryByIdOrName(Long userId, String userName) {
        return this.userMapper.selectUserByIdOrName(userId, userName);
    }

    @Override
    public User queryUserById(Long userId) {
        return this.userMapper.selectUserByIdOrName(userId, null);
    }

    @Override
    public List<User> queryUserByIds(List<Long> userIds) {
        return this.userMapper.selectUserByIds(userIds);
    }


}
