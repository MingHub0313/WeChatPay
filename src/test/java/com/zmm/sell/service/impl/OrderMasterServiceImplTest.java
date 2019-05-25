package com.zmm.sell.service.impl;

import com.zmm.sell.domain.OrderDetail;
import com.zmm.sell.dto.OrderDTO;
import com.zmm.sell.enums.OpenStatusEnum;
import com.zmm.sell.enums.PayStatusEnum;
import com.zmm.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name OrderMasterServiceImplTest
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterService orderMasterService;
    private final String BUYER_OPENID="110110";
    private final String ORDER_ID="1558766115744438751";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("盘石");
        orderDTO.setBuyerName("李四");
        orderDTO.setBuyerPhone("13648961258");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(4);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderMasterService.create(orderDTO);
        log.info("【创建订单】 result = {}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {

        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result = {}",orderDTO);
        Assert.assertNotNull(orderDTO);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OpenStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.finish(orderDTO);
        Assert.assertEquals(OpenStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.pay(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void list() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
    }
}