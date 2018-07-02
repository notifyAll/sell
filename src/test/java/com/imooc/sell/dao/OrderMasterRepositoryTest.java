package com.imooc.sell.dao;

import com.imooc.sell.entity.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {

    @Resource
    private OrderMasterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster =new OrderMaster();
        orderMaster.setOrderId("10007");
        orderMaster.setBuyerName("老张");
        orderMaster.setBuyerPhone("13456006965");
        orderMaster.setBuyerAddress("地狱101");
        orderMaster.setBuyerOpenid("11111113");
        orderMaster.setOrderAmount(new BigDecimal(310));

        OrderMaster save = repository.save(orderMaster);
        Assert.assertNotNull(save);
    }
    @Test
    public void findByBuyerOpenid() {

//        PageRequest request =new PageRequest(0,2);
        QPageRequest request=new QPageRequest(0,2);
        Page<OrderMaster> page = repository.findByBuyerOpenid("11111113", request);
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        System.out.println(page.getNumberOfElements());
        log.info("page : {}",page.getContent());
    }
}