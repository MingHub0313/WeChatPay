package com.zmm.sell.service.impl;

import com.zmm.sell.domain.ProductCategory;
import com.zmm.sell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Name ProductCategoryServiceImplTest
 * @Author 900045
 * @Created by 2019/5/11 0011
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void findOne(){
        ProductCategory productCategory=productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll(){
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list= Arrays.asList(1,2,3,4);
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void save(){
        ProductCategory productCategory=new ProductCategory("饮料专区",4);
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(null,save);
    }

    /**
     * 快捷键
     * ctrl   +  shift   + 回车           ----> 跳到 行尾
     * ctrl   +  alt     +  L             ----> 格式化代码
     * ctrl   +  E                        ----> 最近更改的代码 (alt+shift+C)
     * ctrl   +  P                        ----> 方法参数提示
     * alt    +  shift   + C              ----> 对比最近修改的代码
     * shift  +  F6                       ----> 重构-重命名
     * ctrl   + shift    + 向上键/向下键  ----> 该行上移  代码向上/下移动  Up/Down
     * ctrl   + alt      + left/right    ----->返回至上次浏览的位置
     */
}