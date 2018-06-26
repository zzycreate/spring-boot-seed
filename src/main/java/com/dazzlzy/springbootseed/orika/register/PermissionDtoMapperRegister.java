package com.dazzlzy.springbootseed.orika.register;

import com.dazzlzy.common.support.IMapperRegister;
import com.dazzlzy.springbootseed.model.user.Permission;
import com.dazzlzy.springbootseed.model.user.dto.PermissionDTO;
import ma.glasnost.orika.MapperFactory;

/**
 * PermissionDTO与Permission映射注册
 *
 * @author dazzlzy
 * @date 2018/6/26
 */
public class PermissionDtoMapperRegister implements IMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(PermissionDTO.class, Permission.class)
                .byDefault()
                .register();
    }
}
