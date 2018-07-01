package com.imooc.sell.service.impl;

import com.imooc.sell.dao.OrderDetailRepository;
import com.imooc.sell.dao.OrderMasterRepository;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单事物层
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private ProductService productService;

    @Resource
    private OrderDetailRepository orderDetailRepository;

    @Resource
    protected OrderMasterRepository orderMasterRepository;
    /**
     * 创建订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId=KeyUtil.getUniqueKey();  //订单id
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);  // 商品总价
//        1查询商品 （查商品数价格数量）
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo==null){ //判断商品是否存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
//            /*库存是否足够*/
//            if (productInfo.getProductStock()<orderDetail.getProductQuantity()){
//                throw new SellException()
//            }

            //        2计算订单总价
           orderAmount = orderDetail.getProductPrice()
                   .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                   .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail); //属性拷贝
            orderDetailRepository.save(orderDetail); //详情写入数据库

        }
//        3写入订单数据库 orderMaster 和orderDetil
        OrderMaster orderMaster =new OrderMaster();
        orderMaster.setOrderAmmount(orderAmount);
        orderMaster.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);
        //        4扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    /**
     * 查询单个订单
     *
     * @param orderId
     */
    @Override
    public OrderDTO findOne(String orderId) {

        return null;
    }

    /**
     * 查询订单列表 通过买家微信的 openid
     *
     * @param buyerOpenid
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
