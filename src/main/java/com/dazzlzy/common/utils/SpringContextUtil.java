package com.dazzlzy.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Spring上下文工具类
 *
 * @author dazzlzy
 * @date 2018/4/25
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    private SpringContextUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据名称获取bean
     *
     * @param beanName bean的名字
     * @return bean对象
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据bean名称获取指定类型bean
     *
     * @param beanName bean名称
     * @param clazz    返回的bean类型,若类型不匹配,将抛出异常
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz bean类型
     * @return 指定类型的bean
     */
    public static <T> T getBean(Class<T> clazz) {
        T t = null;
        Map<String, T> map = context.getBeansOfType(clazz);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            t = entry.getValue();
        }
        return t;
    }

    /**
     * 是否包含bean
     *
     * @param beanName bean名字
     * @return boolean，是否包含指定名字的bean
     */
    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    /**
     * 是否是单例
     *
     * @param beanName bean名字
     * @return boolean， 是否是单例
     */
    public static boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }

    /**
     * bean的类型
     *
     * @param beanName bean名字
     * @return bean的类型
     */
    public static Class getType(String beanName) {
        return context.getType(beanName);
    }

}
