package com.zmm.sell.dao;

import com.zmm.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Name ProductInfoRepository
 * @Author 900045
 * @Created by 2019/5/30 0030
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {


	/**
	 * 根据商品的状态 获取商品信息
	 *
	 * @param productStatus
	 * @return
	 */
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
