package com.dazzlzy.springbootseed.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Druid 加解密
 *
 * @author dazzlzy
 * @date 2018/4/4
 */
@Slf4j
public class DruidPasswordUtil {

    public static final String MAP_PUBLIC_KEY = "publicKey";
    public static final String MAP_PRIVATE_KEY = "privateKey";
    public static final String MAP_PASSWORD = "password";

    /**
     * 加密
     *
     * @param password 明文密码
     * @return 加密后的公私钥及密文密码
     */
    public static Map<String, String> encrypt(String password) {
        Map<String, String> encryptResult = new HashMap<>(3);
        String[] arr;
        try {
            arr = ConfigTools.genKeyPair(512);
            encryptResult.put(MAP_PRIVATE_KEY, arr[0]);
            encryptResult.put(MAP_PUBLIC_KEY, arr[1]);
            encryptResult.put(MAP_PASSWORD, ConfigTools.encrypt(arr[0], password));
            return encryptResult;
        } catch (Exception e) {
            log.error("druid encrypt error !", e);
        }
        return null;
    }

    public static String decrypt(String publicKey, String encryptPassword) {
        try {
            return ConfigTools.decrypt(publicKey, encryptPassword);
        } catch (Exception e) {
            log.error("druid decrypt error !", e);
        }
        return null;
    }

}
