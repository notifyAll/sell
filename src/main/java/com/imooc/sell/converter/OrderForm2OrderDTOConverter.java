package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *  OrderForm  转换成  OrderDTO
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson =new Gson(); //谷歌的json转换工具

        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setOrderId(orderForm.getOpenid());

        List<OrderDetail> orderDetailList=null;
        try {
            //第一个参数需要转换的数据 ，转换成的格式
            orderDetailList= gson.fromJson(orderForm.getIteam(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("[对象转换错误] String = {}",orderForm.getIteam());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //购物车转换麻烦点  json转换
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
