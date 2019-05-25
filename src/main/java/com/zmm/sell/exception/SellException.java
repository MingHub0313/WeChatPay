package com.zmm.sell.exception;

import com.zmm.sell.enums.ResultEnum;

/**
 * @Name SellException
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
