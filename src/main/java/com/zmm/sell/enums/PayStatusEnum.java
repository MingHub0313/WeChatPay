package com.zmm.sell.enums;

import lombok.Getter;

/**
 * @Name PayStatusEnum
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
@Getter
public enum PayStatusEnum {

    /**
     * 支付相关的状态
     */
    WAIT(0,"待支付"),
    SUCCESS(1,"支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
