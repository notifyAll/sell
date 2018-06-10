package com.imooc.sell.dao;

import com.imooc.sell.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {

    @Resource
    ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("1000");
        productInfo.setProductName("亚丝娜手办");
        productInfo.setProductPrice(new BigDecimal(888.88));
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://www.xxx.com/a.jpg");
        productInfo.setProductDescription("亚丝娜手办");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(5);

        productInfoRepository.save(productInfo);
    }
}