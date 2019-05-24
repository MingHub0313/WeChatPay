package com.zmm.sell.enums;

import lombok.Getter;

/**
 * @Name OpenStatusEnum
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
@Getter
public enum OpenStatusEnum {

    /**
     * 订单相关的状态
     */
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    private Integer code;

    private String message;

    OpenStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
