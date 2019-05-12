package com.zmm.sell.dao;

import com.zmm.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Name ProductCategoryRepositoryTest
 * @Author 900045
 * @Created by 2019/5/11 0011
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory=productCategoryRepository.findOne(1);
        System.out.println(productCategory.toString());
    }


    @Test
    public void saveTest(){

        ProductCategory findProductCategory=productCategoryRepository.findOne(1);
        findProductCategory.setCategoryType(1);
        // 如果对象中存在时间   则不会自动更新数据库时间;
        // 在实体类上 添加 @DynamicUpdate  动态更新时间
        // 自动更新的前提是: 该对象中的所有属性有任意改动 才会自动更新时间
        productCategoryRepository.save(findProductCategory);
    }

    /**
     * 添加  事务 @Transactional
     * service 的注解  ---> 这个方法抛出异常  则回滚
     * 测试 中的事物 完全不回滚
     */
    @Test
    @Transactional
    public void save(){
        ProductCategory productCategory=new ProductCategory("哈哈",4);
        ProductCategory save = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(save);
        // 不期望  实际值
        //Assert.assertNotEquals(null,save);
    }



    @Test
    public void updateTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(2);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void findAllProductCategory(){
        List<Integer> categoryTypeList=new ArrayList<>();
        //List<Integer> list= Arrays.asList(1,2,3);
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        categoryTypeList.add(3);
        List<ProductCategory> result = productCategoryRepository.findAllByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0,result);
    }

}