package com.zmm.sell.dao;

import com.zmm.sell.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Name OrderDetailRepository
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {


    /**
     * 根据 orderId 查询详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
