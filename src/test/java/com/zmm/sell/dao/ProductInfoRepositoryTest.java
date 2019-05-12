package com.zmm.sell.dao;

import com.zmm.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void  saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("汉堡");
        productInfo.setProductPrice(new BigDecimal(19.9));
        productInfo.setProductStock(50);
        productInfo.setProductDescription("开胃王");
        productInfo.setProductIcon("http://www.hanbao,jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        productInfoRepository.save(productInfo);
    }
    @Test
    public void findByProductStatus(){
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
        System.out.println(productInfoList);
    }

}