package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
//        1查询所有上架商品
        List<ProductInfo> upAll = productService.findUpAll();

//        2查询类目（一次性查询）
//        List<Integer> categoryTypeList=new ArrayList<>();
//        for赋值
//        for (ProductInfo productInfo:upAll){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
//        java8 lambda
        List<Integer> categoryTypeList= upAll.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> allByCategoryTypeIn = categoryService.findAllByCategoryTypeIn(categoryTypeList);
//        3拼装返回值

        return new ResultVO();
    }
}
