package com.imooc.sell.service.impl;

import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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
        OrderDTO orderDTO = orderService.findOne("1530543248720853730");
        log.info("orderDTO : {}",orderDTO);
    }

    @Test
    public void findList() {
        QPageRequest request=new QPageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,request);
        log.info("[订单查选list] orderDTOPage：{}" ,orderDTOPage);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findOne("1530542933008558247");
        OrderDTO cancel = orderService.cancel(orderDTO);
        log.info("【取消订单】cancel {}",cancel);
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}