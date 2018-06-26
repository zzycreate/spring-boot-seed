package com.dazzlzy.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码工具类
 *
 * @author zhaozhenyao
 * @date 2018/5/13
 */
public class PasswordUtil {

    /**
     * 加密密码
     *
     * @param password 明文密码
     * @param salt     盐
     * @return 密文密码
     */
    public static String encrypt(String password, String salt) {
        return new Md5Hash(password, salt, 4).toBase64();
    }

}
