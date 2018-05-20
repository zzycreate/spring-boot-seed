package com.dazzlzy.common.utils;

import java.util.UUID;

/**
 * 随机数工具类
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
public class RandomUtil {

    /**
     * 获取UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带 `-` 的UUID
     *
     * @return UUID
     */
    public static String getShortUUID() {
        return getUUID().replaceAll("-", "");
    }

}
