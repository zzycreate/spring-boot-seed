package com.dazzlzy.springbootseed.service;

import com.dazzlzy.SpringBootSeedApplicationTests;
import com.dazzlzy.common.enums.BooleanEnum;
import com.dazzlzy.springbootseed.model.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IRoleService的单元测试
 *
 * @author dazzlzy
 * @date 2018/5/27
 */
@Slf4j
public class IRoleServiceTest extends SpringBootSeedApplicationTests {

    @Autowired
    private IRoleService roleService;

    @Test
    public void addRole() {
        Role role = new Role();
        role.setRoleName("系统管理员");
        role.setRoleCode("systemAdmin");
        role.setDescription("系统管理员，拥有系统超级管理权限");
        role.setStateCode(BooleanEnum.YES.getValue());
        roleService.addRole(role);
    }
}