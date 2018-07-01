package com.imooc.sell.utils;

import java.util.Random;

public class KeyUtil {



    /**
     * 生成唯一主键
     * 时间+随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random=new Random();

        long currentTimeMillis = System.currentTimeMillis();//时间
        Integer number  = random.nextInt(900000) +100000; //随机6位数

        return currentTimeMillis+String.valueOf(number);
    }
}
