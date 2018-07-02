package com.imooc.sell.service.impl;

import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {


    @Resource
    OrderServiceImpl orderService;

   String BUYER_OPENID="181828809";
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerPhone("13456006963");
        orderDTO.setBuyerName("亚丝娜");
        orderDTO.setBuyerAddress("上海松江");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

//       购物车
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
//        CartDTO cartDTO = new CartDTO("1000",3);
        OrderDetail orderDetail =new OrderDetail();
        orderDetail.setProductId("1000");
        orderDetail.setProductQuantity(1);

        OrderDetail orderDetail1 =new OrderDetail();
        orderDetail1.setProductId("1001");
        orderDetail1.setProductQuantity(2);

        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetails);

        orderService.create(orderDTO);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}