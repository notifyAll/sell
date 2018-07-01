package com.imooc.sell.dto;

/**
 * 购物车
 */
import lombok.Data;

@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;
    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
