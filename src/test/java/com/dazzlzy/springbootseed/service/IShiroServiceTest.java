package com.dazzlzy.springbootseed.service;

import com.dazzlzy.SpringBootSeedApplicationTests;
import com.dazzlzy.springbootseed.model.user.User;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * IShiroService 单元测试
 *
 * @author dazzlzy
 * @date 2018/5/27
 */
@Slf4j
public class IShiroServiceTest extends SpringBootSeedApplicationTests {

    @Autowired
    private IShiroService shiroService;

    @Test
    public void login() {
        User user = shiroService.login(null, "admin", "123456");
        TestCase.assertNotNull(user);
        log.info("----------------------------------------------------------------------------");
        log.info("User: {}", user);
        log.info("----------------------------------------------------------------------------");
    }
}