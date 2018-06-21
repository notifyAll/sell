package com.imooc.sell.VO;

import lombok.Data;

/**
 * http 请求返回的最外层对象
 * @param <T>
 */
@Data
public class ResultVO<T> {

    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private  String msg;

    /** 数据 */
    private T data;
}
