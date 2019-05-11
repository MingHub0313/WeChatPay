package com.zmm.sell.dao;

import com.zmm.sell.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Name ProductCategoryRepository
 * @Author 900045
 * @Created by 2019/5/11 0011
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {


    /**
     * 根据 categoryType 的集合查询所有的 类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findAllByCategoryTypeIn(List<Integer> categoryTypeList);
}
