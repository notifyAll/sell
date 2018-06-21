package com.imooc.sell.controller;

import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品接口
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    /**
     * http://localhost:8080/sell/buyer/product/list
     * @return
     */
    @GetMapping("/list")
    public ResultVO list() {
//        1查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

//        2查询类目（一次性查询）
//        List<Integer> categoryTypeList=new ArrayList<>();
//        for赋值
//        for (ProductInfo productInfo:upAll){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
//        java8 lambda
        List<Integer> categoryTypeList= productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findAllByCategoryTypeIn(categoryTypeList);
//        3拼装返回值

        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){//拼装外层的商品类目
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList =new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    //拷贝数据 使用spring提供的拷贝工具类 只要里面参数名一样
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);


        }
//        ResultVO<List<ProductVO>> resultVO = new ResultVO();
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        return resultVO;
        return ResultVOUtil.success(productVOList);
    }


}
