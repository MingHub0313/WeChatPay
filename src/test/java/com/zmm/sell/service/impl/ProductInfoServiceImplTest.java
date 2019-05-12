package com.zmm.sell.service.impl;

import com.zmm.sell.domain.ProductInfo;
import com.zmm.sell.enums.ProductStatusEnum;
import com.zmm.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("1");
        Assert.assertEquals("1",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        List<ProductInfo> productInfoList = productInfoPage.getContent();
        System.out.println(productInfoList);
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("2");
        productInfo.setProductName("薯条");
        productInfo.setProductPrice(new BigDecimal(14.9));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很美味");
        productInfo.setProductIcon("http://www.薯条.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(1);
        ProductInfo save = productInfoService.save(productInfo);
        Assert.assertNotNull(null,save);
    }
}