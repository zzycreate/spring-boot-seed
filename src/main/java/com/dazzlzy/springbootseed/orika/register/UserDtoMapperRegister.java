package com.dazzlzy.springbootseed.orika.register;

import com.dazzlzy.common.support.IMapperRegister;
import com.dazzlzy.springbootseed.model.user.User;
import com.dazzlzy.springbootseed.model.user.dto.UserDTO;
import ma.glasnost.orika.MapperFactory;

/**
 * UserDTO与User映射注册
 *
 * @author dazzlzy
 * @date 2018/6/26
 */
public class UserDtoMapperRegister implements IMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(UserDTO.class, User.class)
                .byDefault()
                .register();
    }
}
