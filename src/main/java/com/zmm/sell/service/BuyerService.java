package com.zmm.sell.service;

import com.zmm.sell.dto.OrderDTO;

/**
 * @Name BuyerService
 * @Author 900045
 * @Created by 2019/5/27 0027
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid, String orderId);
}
