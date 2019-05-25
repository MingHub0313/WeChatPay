package com.zmm.sell.dto;

import lombok.Data;

/**
 * @Name CartDTO
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
@Data
public class CartDTO {

    /**
     * 数量
     */
    private Integer productQuantity;
    /**
     * 商品Id
     */
    private String productId;


    public CartDTO( String productId,Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
