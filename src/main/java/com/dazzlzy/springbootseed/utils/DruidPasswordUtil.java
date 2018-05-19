package com.dazzlzy.springbootseed.utils;

import com.alibaba.druid.filter.config.ConfigTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dazzlzy
 * @date 2018/4/4
 */
public class DruidPasswordUtil {

    public static final String MAP_PUBLIC_KEY = "publicKey";
    public static final String MAP_PRIVATE_KEY = "privateKey";
    public static final String MAP_PASSWORD = "password";

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
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String publicKey, String encryptPassword) {
        try {
            return ConfigTools.decrypt(publicKey, encryptPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
