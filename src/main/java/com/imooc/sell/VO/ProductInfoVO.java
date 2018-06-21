package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详细
 */
@Data
public class ProductInfoVO {
    /**商品id*/
    @JsonProperty("id")
    private String productId;
    /**商品名字*/
    @JsonProperty("name")
    private String productName;
    /**单价*/
    @JsonProperty("price")
    private BigDecimal productPrice;
    /**描述*/
    @JsonProperty("description")
    private String productDescription;
    /**小图*/
    @JsonProperty("icon")
    private String productIcon;

}
