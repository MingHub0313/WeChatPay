package com.zmm.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.zmm.sell.dto.OrderDTO;

/**
 * @Name PayService
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);
}
