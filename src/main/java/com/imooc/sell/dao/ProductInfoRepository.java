package com.imooc.sell.dao;


import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**
     * 通过商品状态查询
     * @param productStatus 商品状态
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
