package com.dazzlzy.common.enums;

import lombok.Getter;

/**
 * boolean枚举，YES/NO
 *
 * @author dazzlzy
 * @date 2018/5/7
 */
@Getter
public enum BooleanEnum {

    /**
     * YES: 1
     */
    YES(1),

    /**
     * NO: 0
     */
    NO(0);

    private int value;

    BooleanEnum(int value) {
        this.value = value;
    }

}
