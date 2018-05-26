package com.dazzlzy.common.enums;

import lombok.Getter;

/**
 * 运行环境组枚举
 *
 * @author dazzlzy
 * @date 2018/5/26
 */
@Getter
public enum EnvironmentGroupEnum {

    /**
     * RUNTIME运行环境组：
     * 1. DEV(开发环境)
     * 2. PROD(生产环境)
     */
    RUNTIME(new EnvironmentEnum[]{EnvironmentEnum.DEV, EnvironmentEnum.PROD}),;

    /**
     * 运行环境
     */
    private EnvironmentEnum[] environments;

    EnvironmentGroupEnum(EnvironmentEnum[] environments) {
        this.environments = environments;
    }

    /**
     * 是否是runtime运行环境组
     *
     * @param s 环境名
     * @return boolean
     */
    public static boolean isRuntime(String s) {
        EnvironmentEnum[] environmentEnums = RUNTIME.getEnvironments();
        for (EnvironmentEnum environmentEnum : environmentEnums) {
            if (environmentEnum.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
