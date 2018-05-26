package com.dazzlzy.common.enums;

import lombok.Getter;

/**
 * Cookie枚举
 *
 * @author dazzlzy
 * @date 2018/5/26
 */
@Getter
public enum CookieEnum {
    /**
     * REMEMBER_ME： Cookie中存储的REMEMBER_ME
     */
    REMEMBER_ME("rememberMe"),;

    private String value;

    CookieEnum(String value) {
        this.value = value;
    }
}
