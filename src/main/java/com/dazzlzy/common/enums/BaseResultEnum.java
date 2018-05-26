package com.dazzlzy.common.enums;

import lombok.Getter;

/**
 * BaseResult的code编码枚举
 *
 * @author dazzlzy
 * @date 2018/3/21
 */
@Getter
public enum BaseResultEnum {
    /**
     * SUCCESS: 200 成功
     * FAIL: 400 失败
     * NOT_FOUND： 404 不存在
     * SERVER_ERROR: 500 网络服务异常
     */
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    NOT_FOUND(404, "不存在"),
    SERVER_ERROR(500, "服务异常");

    private int code;

    private String message;

    BaseResultEnum(int code, String message) {
        this.code = code;
    }

}
