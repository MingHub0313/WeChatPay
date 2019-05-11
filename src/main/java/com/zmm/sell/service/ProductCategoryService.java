package com.zmm.sell.service;

import com.zmm.sell.domain.ProductCategory;

import java.util.List;

/**
 * @Name ProductCategoryService
 * @Author 900045
 * @Created by 2019/5/11 0011
 */
public interface ProductCategoryService {

    /**
     * 根据 categoryId 查询 类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);


    /**
     * 查询所有类目
     * @return
     */
    List<ProductCategory> findAll();


    /**
     * 根据 categoryType 的集合查询所有的 类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 保存 ProductCategory
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
