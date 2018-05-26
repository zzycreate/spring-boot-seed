package com.dazzlzy.springbootseed.service;

import com.dazzlzy.SpringBootSeedApplicationTests;
import com.dazzlzy.springbootseed.model.user.User;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.nutz.lang.Lang;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * IUserService单元测试
 */
@Slf4j
public class IUserServiceTest extends SpringBootSeedApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void queryByIdOrName() {
        User user = userService.queryByIdOrName(1L, null);
//        User user = userService.queryByIdOrName(null, "admin");
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", user);
        log.info("==========================================================================");
        TestCase.assertNotNull(user);
    }

    @Test
    public void queryUserById() {
        User user = userService.queryUserById(1L);
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", user);
        log.info("==========================================================================");
        TestCase.assertNotNull(user);
    }

    @Test
    public void queryUserByIds() {
        List<User> list = userService.queryUserByIds(Lang.list(1L, 2L));
        log.info("==========================================================================");
        log.info("查询结果 ==> {}", list);
        log.info("==========================================================================");
        TestCase.assertNotNull(list);
    }
}