package com.dazzlzy.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 随机数工具类
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Slf4j
public class RandomUtil {

    /**
     * 获取默认位数（8位）的盐
     *
     * @return 8位的随机盐
     */
    public static String getSalt() {
        return getSalt(12);
    }

    /**
     * 获取随机盐
     * 使用SecureRandom安全的伪随机数
     *
     * @param size 盐的位数
     * @return 随机盐
     */
    public static String getSalt(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

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
