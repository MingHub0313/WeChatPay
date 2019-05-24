package com.zmm.sell.dao;

import com.zmm.sell.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name OrderDetailRepositoryTest
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("126756");
        orderDetail.setOrderId("124459");
        orderDetail.setProductIcon("http://www.2.jpg");
        orderDetail.setProductId("1869");
        orderDetail.setProductName("拌面");
        orderDetail.setProductPrice(new BigDecimal(4.00));
        orderDetail.setProductQuantity(1);
        OrderDetail save = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOrderId(){
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("196457");
        Assert.assertNotEquals(0,orderDetailList.size());
    }

}