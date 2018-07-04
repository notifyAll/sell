package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dao.OrderDetailRepository;
import com.imooc.sell.dao.OrderMasterRepository;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单事物层
 */
@Service
@Slf4j
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

            //        2计算订单总价  商品单价 * multiply  订单中的商品数量 再累加总金额
           orderAmount = productInfo.getProductPrice()
                   .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                   .add(orderAmount);

            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail); //属性拷贝 先属性拷贝 再单个赋值  因为null 值也会被拷贝进去
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);


            orderDetailRepository.save(orderDetail); //详情写入数据库

        }
//        3写入订单数据库 orderMaster 和orderDetil
        OrderMaster orderMaster =new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderId(orderId);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());


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

// 订单主表
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();

        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
//订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList==null||orderDetailList.size()<=0){
            throw new  SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

//        返回值
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单列表 通过买家微信的 openid
     *
     * @param buyerOpenid
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());


        Page<OrderDTO> orderDTOPage =  new PageImpl<OrderDTO>(orderDTOList,orderMasterPage.getPageable(),orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确 orderId={},orderStatus={} ",orderDTO.getOrderId(),orderDTO.getOrderStatus() );
            throw   new  SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
//        修改订单状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);


        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("[取消订单] 订单修改失败 orderId={},orderStatus={} ",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
//        返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单商品详情不存在 orderId={},orderStatus={} ",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);


//        如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
//            TODO
        }
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
//        判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态异常 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

//        修改状态
        OrderMaster orderMaster =new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster save = orderMasterRepository.save(orderMaster);

        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
//        判断订单状态 及 支付状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态异常 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT)){
            log.error("【支付订单】 订单支付状态异常 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

// 修改支付状态
        OrderMaster orderMaster =new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster save = orderMasterRepository.save(orderMaster);
        return orderDTO;

    }
}
