package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品包含类目
 */
@Data
public class ProductVO {
    /**
     * 类目名字 @JsonProperty 该注解可以在对象转换成json时 改掉该属性名称 为name
     */
    @JsonProperty("name")
    private String categoryName;
    /**
     * 类目
     */
    @JsonProperty("type")
    private Integer categoryType;
    /**
     * 商品详情列表
     */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
