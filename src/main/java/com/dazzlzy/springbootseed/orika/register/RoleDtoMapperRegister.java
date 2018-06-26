package com.dazzlzy.springbootseed.orika.register;

import com.dazzlzy.common.support.IMapperRegister;
import com.dazzlzy.springbootseed.model.user.Role;
import com.dazzlzy.springbootseed.model.user.dto.RoleDTO;
import ma.glasnost.orika.MapperFactory;

/**
 * RoleDTO与Role映射注册
 *
 * @author dazzlzy
 * @date 2018/6/26
 */
public class RoleDtoMapperRegister implements IMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(RoleDTO.class, Role.class)
                .byDefault()
                .register();
    }
}
