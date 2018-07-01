package com.imooc.sell.dto;

import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单 数据传输对象
 */
@Data
public class OrderDTO {


    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;
    /**
     * 买家电话
     */
    private String buyerPhone;
    /**
     * 买家地址
     */
    private String buyerAddress;
    /**
     *  买家微信的 openId
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmmount;

    /**
     * 订单状态 默认新下单
     */
    private Integer orderStatus;
    /**
     * 支付状态 默认0 未支付
     */
    private Integer payStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单详情列表
     */
    private List<OrderDetail> orderDetailList;
}
