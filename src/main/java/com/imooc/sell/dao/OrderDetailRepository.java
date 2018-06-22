package com.imooc.sell.dao;

import com.imooc.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /**
     * 通过订单号查询订单详情
     * @param orderId 订单id
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
