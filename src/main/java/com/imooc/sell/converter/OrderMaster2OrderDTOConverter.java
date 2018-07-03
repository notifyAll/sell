package com.imooc.sell.converter;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 * OrderMaster ——》 OrderDTO
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderDTO> collect = orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return collect;
    }
}
