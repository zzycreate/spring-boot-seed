package com.dazzlzy.springbootseed.service.impl;

import com.dazzlzy.common.exception.BusinessException;
import com.dazzlzy.common.utils.PasswordUtil;
import com.dazzlzy.common.utils.RandomUtil;
import com.dazzlzy.springbootseed.dao.user.UserMapper;
import com.dazzlzy.springbootseed.model.user.User;
import com.dazzlzy.springbootseed.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户Service
 *
 * @author zhaozhenyao
 * @date 2018/5/9
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
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
    public List<User> queryUserByIds(List<Long> userIds) {
        return this.userMapper.selectUserByIds(userIds);
    }

    @Override
    public void addUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        validateUser(user);
        // 随机盐
        String salt = RandomUtil.getSalt();
        user.setSalt(salt);
        // 密码密文
        String password = PasswordUtil.encrypt(user.getPassword(), salt);
        user.setPassword(password);
        user.setCreateTime(now);
        user.setModifyTime(now);
        userMapper.insert(user);
    }

    /**
     * 检查用户信息是否正确
     *
     * @param user 用户信息
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (StringUtils.isBlank(user.getUserName())) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new BusinessException("用户密码不能为空");
        }
    }


}
