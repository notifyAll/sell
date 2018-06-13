package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ProductServiceImplTest {

    @Resource
    ProductServiceImpl productService;
    @Test
    public void findOne() {
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void findAll() {
        QPageRequest pageable=new QPageRequest(0,3);

        Page<ProductInfo> all = productService.findAll(pageable);

        System.out.println("all : "+all.toString());
        System.out.println(all.getTotalElements());
        System.out.println(all.getTotalPages());
        List<ProductInfo> content = all.getContent();
        for (int i = 0; i < content.size(); i++) {
            System.out.println(content.get(i));
        }
    }

    @Test
    public void save() {
    }
}