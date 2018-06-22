package com.imooc.sell.dao;

import com.imooc.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailRepositoryTest {

    @Resource
    OrderDetailRepository repository;


    @Test
    public void saveTest(){
        OrderDetail orderDetail =new OrderDetail();
        orderDetail.setDetailId("1001");
        orderDetail.setOrderId("10003");
        orderDetail.setProductId("1000");
        orderDetail.setProductIcon("http://www.xxx.com/a.jpg");
        orderDetail.setProductPrice(new BigDecimal(888.88));
        orderDetail.setProductName("亚丝娜手办");
        orderDetail.setProductQuantity(1);

        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotNull(save);


    }
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("10003");
        log.info("orderDetailList : {}",orderDetailList);
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}