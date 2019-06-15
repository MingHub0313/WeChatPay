package com.zmm.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.zmm.sell.dto.OrderDTO;

/**
 * 支付
 *
 * @Name PayService
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public interface PayService {

	/**
	 * 发起支付
	 *
	 * @param orderDTO
	 * @return
	 */
	PayResponse create(OrderDTO orderDTO);

	/**
	 * 通知
	 *
	 * @param notifyData
	 * @return
	 */
	PayResponse notify(String notifyData);

	/**
	 * 退款
	 *
	 * @param orderDTO
	 * @return
	 */
	RefundResponse refund(OrderDTO orderDTO);
}
