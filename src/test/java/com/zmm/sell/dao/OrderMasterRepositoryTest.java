package com.zmm.sell.dao;

import com.zmm.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name OrderMasterRepositoryTest
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private static final String OPRNID="110110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("196457");
        orderMaster.setBuyerName("王五");
        orderMaster.setBuyerAddress("余杭");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(29.45));
        orderMaster.setBuyerPhone("19140502458");
        OrderMaster save = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByBuyerOpenid(){
        PageRequest request=new PageRequest(0,1);
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(OPRNID, request);
        //总数
        long count = orderMasterPage.getTotalElements();
        Assert.assertNotEquals(0,count);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        System.out.println(orderMasterList);
    }

}