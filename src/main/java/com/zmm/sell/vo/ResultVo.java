package com.zmm.sell.vo;

import lombok.Data;

/**
 * 返回封装数据
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 * @param <T>
 */
@Data
public class ResultVo<T> {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg="";

    /**
     * 具体内容
     */
    private T Date;

}
