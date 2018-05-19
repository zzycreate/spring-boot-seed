package com.dazzlzy.springbootseed.utils;

import java.util.UUID;

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
