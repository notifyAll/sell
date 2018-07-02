package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;

public class SellException extends RuntimeException {
    private Integer code;


    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());  // RuntimeException 中有message属性 直接构造方法赋值过去
        this.code = resultEnum.getCode();

    }
}