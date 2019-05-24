package com.zmm.sell.dao;

import com.zmm.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Name OrderMasterRepository
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {


    /**
     * 按照 买家的 openId 去查  分页
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
