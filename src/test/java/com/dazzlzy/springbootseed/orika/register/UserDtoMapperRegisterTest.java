package com.dazzlzy.springbootseed.orika.register;

import com.dazzlzy.SpringBootSeedApplicationTests;
import com.dazzlzy.common.enums.BooleanEnum;
import com.dazzlzy.springbootseed.model.user.User;
import com.dazzlzy.springbootseed.model.user.dto.PermissionDTO;
import com.dazzlzy.springbootseed.model.user.dto.RoleDTO;
import com.dazzlzy.springbootseed.model.user.dto.UserDTO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.nutz.lang.Lang;

import javax.annotation.Resource;

/**
 * @author dazzlzy
 * @date 2018/6/26
 */
@Slf4j
public class UserDtoMapperRegisterTest extends SpringBootSeedApplicationTests {

    @Resource
    private MapperFacade mapperFacade;

    @Test
    public void register() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("admin");
        userDTO.setEmail("123@abc.com");
        userDTO.setPassword("123456");
        userDTO.setSalt("abcd");
        userDTO.setStateCode(BooleanEnum.YES.getValue());

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("超级管理员");
        roleDTO.setRoleCode("superadmin");
        userDTO.setRoles(Lang.list(roleDTO));

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setPermissionName("系统管理权限");
        permissionDTO.setPermissionCode("system:admin");
        userDTO.setPermissions(Lang.list(permissionDTO));

        User user = mapperFacade.map(userDTO, User.class);
        log.info("user:{}", user);
        TestCase.assertEquals(1L, user.getId().longValue());
        TestCase.assertEquals("admin", user.getUserName());
        TestCase.assertEquals("123@abc.com", user.getUserName());
        TestCase.assertNotNull(user.getRoles());
        TestCase.assertEquals("超级管理员", user.getRoles().get(0).getRoleName());
        TestCase.assertEquals("superadmin", user.getRoles().get(0).getRoleCode());
        TestCase.assertNotNull(user.getPermissions());
        TestCase.assertEquals("系统管理权限", user.getPermissions().get(0).getPermissionName());
        TestCase.assertEquals("system:admin", user.getPermissions().get(0).getPermissionCode());

    }
}