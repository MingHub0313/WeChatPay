package com.zmm.sell.service.impl;

import com.zmm.sell.dao.OrderDetailRepository;
import com.zmm.sell.dao.OrderMasterRepository;
import com.zmm.sell.dto.OrderDTO;
import com.zmm.sell.service.OrderService;
import com.zmm.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Name OrderServiceImpl
 * @Author 900045
 * @Created by 2019/6/14 0014
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderMasterRepository orderMasterRepository;

	@Autowired
	private PayService payService;

	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		return null;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		return null;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		return null;
	}

	@Override
	public OrderDTO cancel(OrderDTO orderDTO) {
		return null;
	}

	@Override
	public OrderDTO finish(OrderDTO orderDTO) {
		return null;
	}

	@Override
	public OrderDTO paid(OrderDTO orderDTO) {
		return null;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		return null;
	}
}
