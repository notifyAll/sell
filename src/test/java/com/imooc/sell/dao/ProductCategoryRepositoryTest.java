package com.imooc.sell.dao;

import com.imooc.sell.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {

    @Resource
    private ProductCategoryRepository repository;


    @Test
    public void  save(){
        ProductCategory result=new ProductCategory();
        result.setCategoryName("手机");
        result.setCategoryType(2);
        repository.save(result);

    }
}