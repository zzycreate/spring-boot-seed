package com.dazzlzy.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源文件读取工具
 *
 * @author dazzlzy
 * @date 2018/4/4
 */
public class PropertiesFileUtil {

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<>();
    /**
     * 打开文件时间，判断超时使用
     */
    private Date loadTime;
    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle;
    /**
     * 默认资源文件名称
     */
    private static final String NAME = "config";
    /**
     * 缓存时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 私有构造方法，创建单例
     *
     * @param name properties名
     */
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    /**
     * 获取单例对象
     *
     * @return 工具单例对象
     */
    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    /**
     * 获取工具单例对象
     *
     * @param name properties名
     * @return 工具单例对象
     */
    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }


    /**
     * 根据key读取value
     *
     * @param key 属性key
     * @return 属性value
     */
    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException e) {
            return "";
        }
    }


    /**
     * 根据key读取value(整形)
     *
     * @param key 属性key
     * @return 属性value（整形）
     */
    public Integer getInteger(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(长整形)
     *
     * @param key 属性key
     * @return 属性value（长整形）
     */
    public Long getLong(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Long.parseLong(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(浮点)
     *
     * @param key 属性key
     * @return 属性value（浮点）
     */
    public Double getDouble(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Double.parseDouble(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(布尔)
     *
     * @param key 属性key
     * @return 属性value（布尔值）
     */
    public boolean getBoolean(String key) {
        try {
            String value = resourceBundle.getString(key);
            return "true".equals(value);
        } catch (MissingResourceException e) {
            return false;
        }
    }

    /**
     * 获取加载文件的时间
     *
     * @return 加载文件的时间
     */
    public Date getLoadTime() {
        return loadTime;
    }

}
