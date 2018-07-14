package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * 买家 业务层
 */

public interface BuyerService {

    /**
     * 查询单个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne(String openid,String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid,String orderId);

}
