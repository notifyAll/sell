package com.imooc.sell.service;

import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品表
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo>  findUpAll();

    List<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

//    加库存
    
//    减库存
}
