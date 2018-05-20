package com.dazzlzy.springbootseed.dao.user;

import com.dazzlzy.SpringBootSeedApplicationTests;
import com.dazzlzy.springbootseed.model.user.User;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.nutz.lang.Lang;

import javax.annotation.Resource;

import java.util.List;

@Slf4j
public class UserExtraMapperTest extends SpringBootSeedApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void selectUser() {
        User user = new User();
        user.setId(1L);
        List<User> list = userMapper.selectUser(user);
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", list);
        log.info("==========================================================================");
        TestCase.assertNotNull(list);
    }

    @Test
    public void selectUserByIdOrName() {
        User user = userMapper.selectUserByIdOrName(1L, null);
//        User user = userMapper.selectUserByIdOrName(null, "admin");
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", user);
        log.info("==========================================================================");
        TestCase.assertNotNull(user);
    }

    @Test
    public void selectUserByIds() {
        List<User> list = userMapper.selectUserByIds(Lang.list(1L, 2L));
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", list);
        log.info("==========================================================================");
        TestCase.assertNotNull(list);
    }
}