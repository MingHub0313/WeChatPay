package com.zmm.sell.service.impl;

import com.zmm.sell.converter.OrderMaster2OrderDTOConverter;
import com.zmm.sell.dao.OrderDetailRepository;
import com.zmm.sell.dao.OrderMasterRepository;
import com.zmm.sell.domain.OrderDetail;
import com.zmm.sell.domain.OrderMaster;
import com.zmm.sell.domain.ProductInfo;
import com.zmm.sell.dto.CartDTO;
import com.zmm.sell.dto.OrderDTO;
import com.zmm.sell.enums.OpenStatusEnum;
import com.zmm.sell.enums.PayStatusEnum;
import com.zmm.sell.enums.ResultEnum;
import com.zmm.sell.exception.SellException;
import com.zmm.sell.service.OrderMasterService;
import com.zmm.sell.service.PayService;
import com.zmm.sell.service.ProductInfoService;
import com.zmm.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name OrderMasterServiceImpl @Author 900045 @Created by 2019/5/25 0025
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

	@Resource
	private OrderMasterRepository orderMasterRepository;

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private PayService payService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderDTO create(OrderDTO orderDTO) {

		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		// 随机生成 orderId
		String orderId = KeyUtil.genUniqueKey();

		// 1.查询商品(数量、价格)
		List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
		for (OrderDetail orderDetail : orderDetailList) {
			ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
			// 判断是否为空
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			// 2.计算订单总价

			// 问题 1 : 使用 orderDetail对象中的 价格 --->改为 productInfo对象
			orderAmount =
					productInfo
							.getProductPrice()
							.multiply(new BigDecimal(orderDetail.getProductQuantity()))
							.add(orderAmount);

			// 订单详情入库
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
      /*orderDetail.setProductName(productInfo.getProductName());
      orderDetail.setProductIcon(productInfo.getProductIcon());*/

			// 对象copy 从第一个复制到-->第二个
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailRepository.save(orderDetail);
		}

		// 3.写入订单数据库(OrderMaster 和 OrderDetail)

		OrderMaster orderMaster = new OrderMaster();
		// 先copy 再赋值属性

		// 问题 3 访问接口 orderId返回前台为空
		orderMaster.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		// 原 设置 setOrderId
		orderMaster.setOrderAmount(orderAmount);
		// 问题3 最先 OrderMaster 对象中的有些属性 已经为null 要重新赋值
		orderMaster.setOrderStatus(OpenStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		// 修改问题2 先对属性赋值 再对象copy 属性的值 为 null 也会被copy
		orderMasterRepository.save(orderMaster);
		// 4.扣库存
		List<CartDTO> cartDTOList =
				orderDetailList.stream()
						.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
						.collect(Collectors.toList());
		productInfoService.decreaseStock(cartDTOList);
		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
		if (orderMaster == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		List<OrderDTO> convert = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		return new PageImpl<>(convert, pageable, orderMasterPage.getTotalElements());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		// 问题 5 之前在此处进行 copyProperties(orderDTO, orderMaster);
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OpenStatusEnum.NEW.getCode())) {
			log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 修改订单状态
		orderDTO.setOrderStatus(OpenStatusEnum.CANCEL.getCode());
		// todo 在此处进行对象 copy
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		// 返还库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList =
				orderDTO.getOrderDetailList().stream()
						.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
						.collect(Collectors.toList());
		productInfoService.increaseStock(cartDTOList);

		// 如果已支付, 需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			// todo
			payService.refund(orderDTO);
		}

		return orderDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderDTO finish(OrderDTO orderDTO) {
		// 1. 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OpenStatusEnum.NEW.getCode())) {
			log.error(
					"【完结订单】订单状态不正确, orderId={}, orderStatus={}",
					orderDTO.getOrderId(),
					orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 2 修改订单状态
		orderDTO.setOrderStatus(OpenStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		// 推送微信模版消息
		// pushMessageService.orderStatus(orderDTO);
		return orderDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderDTO pay(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OpenStatusEnum.NEW.getCode())) {
			log.error(
					"【订单支付完成】订单状态不正确, orderId={}, orderStatus={}",
					orderDTO.getOrderId(),
					orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		// 修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}
}
