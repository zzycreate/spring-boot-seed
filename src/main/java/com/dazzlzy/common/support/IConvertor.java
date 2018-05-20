package com.dazzlzy.common.support;

/**
 * 转换器接口，可以将Source转换为Target
 *
 * @author dazzlzy
 * @date 2018/5/9
 */
public interface IConvertor<Source, Target> {

    /**
     * 将Source转换为Target
     *
     * @param source 需要转换的对象
     * @return 转换后的对象
     */
    Target convert(Source source);

}