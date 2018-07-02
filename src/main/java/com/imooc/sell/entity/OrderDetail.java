package com.imooc.sell.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * 订单详情
 */
@Entity
@Data
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    private String detailId;
    /**
     * 订单id
     */
    private String  orderId;
    /**
     * 商品Id
     */
    private String productId;
    /**
     * 商品名字
     */
    private String productName;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    private Integer productQuantity;
    /**
     * 商品小图
     */
    private String productIcon;

}
