package com.dazzlzy.common.support;

import ma.glasnost.orika.MapperFactory;

/**
 * 用于Orika的mapper注册
 *
 * @author dazzlzy
 * @date 2018/6/17
 */
public interface IMapperRegister {

    /**
     * 注册，向mapperFactory中注入Orika的映射规则
     *
     * @param mapperFactory 全局MapperFactory
     */
    void register(MapperFactory mapperFactory);

}
