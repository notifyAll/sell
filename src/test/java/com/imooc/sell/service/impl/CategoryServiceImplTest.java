package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {

    @Resource
    CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory one = categoryService.findOne(2);

        log.info(one.toString());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();

        log.info("{}",all);
    }

    @Test
    public void findAllByCategoryTypeIn() {
        List<ProductCategory> allByCategoryTypeIn = categoryService.findAllByCategoryTypeIn(Arrays.asList(2, 3));

        log.info("allByCategoryTypeIn: {}",allByCategoryTypeIn);
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("手办");
        category.setCategoryType(5);
        ProductCategory save = categoryService.save(category);

        log.info("save : {}",save);
    }
    @Test
    public void upd() {
        ProductCategory category = categoryService.findOne(5);
        category.setCategoryName("高级手办");
        category.setCategoryType(5);
        ProductCategory save = categoryService.save(category);

        log.info("save : {}",save);
    }

}