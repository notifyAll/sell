package com.imooc.sell.dao;

import com.imooc.sell.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        result.setCategoryName("平板4");
        result.setCategoryType(5);
        repository.save(result);
    }

    @Test
    @Transient
    public void upd(){
//        ProductCategory result = repository.findOne(1); 老版本通过id查询
        ProductCategory result = repository.findById(3).get();  //java.util.NoSuchElementException: No value present


        Assert.assertNotNull(result);
        result.setCategoryName("超级本s");

        repository.save(result);

    }

    @Test
    public void  findAllByCategoryTypeIn(){
        List<Integer> list= Arrays.asList(1,2,3);
        List<ProductCategory> allByCategoryTypeIn = repository.findAllByCategoryTypeIn(list);

        for (int i = 0; i < allByCategoryTypeIn.size(); i++) {
            System.out.println(allByCategoryTypeIn.get(i));

        }

    }
}