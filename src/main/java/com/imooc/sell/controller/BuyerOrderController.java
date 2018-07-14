package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private BuyerService buyerService;
    /**
     * 创建订单
     *
     * @param orderForm     order表单
     * @param bindingResult 表单校验的一个对象
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {

        //判断表单校验是否有错误
        if (bindingResult.hasErrors()) {
            log.error(" 【创建订单】 参数异常 ，orderFrom={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        //判断购物车是不是空的

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】 购物车不能为空 ");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderid", createResult.getOrderId());

        ResultVO<Map<String, String>> resultVO = ResultVOUtil.success(map);
        return resultVO;
    }

    //    订单列表
    @GetMapping("/list")
    public ResultVO<OrderDTO> list(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        QPageRequest pageable = new QPageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageable);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //    订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId
    ) {

        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVOUtil.success(orderDTO);
    }

    //    取消订单
    @GetMapping("/detail")
    public ResultVO<OrderDTO> cancel(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId
    ) {

        buyerService.cancelOrder(openid,orderId);

//        OrderDTO cancel = orderService.cancel(orderDTO);

        return ResultVOUtil.success();

    }

}
