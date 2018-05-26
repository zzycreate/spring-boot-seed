package com.dazzlzy.springbootseed.service.impl;

import com.dazzlzy.springbootseed.dao.user.UserMapper;
import com.dazzlzy.springbootseed.model.user.User;
import com.dazzlzy.springbootseed.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Service
 *
 * @author zhaozhenyao
 * @date 2018/5/9
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryByIdOrName(Long userId, String userName) {
        return this.userMapper.selectUserByIdOrName(userId, userName);
    }

    @Override
    public User queryUserById(Long userId) {
        return this.userMapper.selectUserByIdOrName(userId, null);
    }

    @Override
    public List<User> queryUserById(List<Long> userIds) {
        return this.userMapper.selectUserByIds(userIds);
    }


}
