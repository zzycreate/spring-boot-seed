package com.dazzlzy.common.base;

import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 自定义mapper接口，继承tk.mybatis.common.Mapper
 *
 * @author dazzlzy
 * @date 2018/4/1
 */
@Mapper
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>,
        ExampleMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
