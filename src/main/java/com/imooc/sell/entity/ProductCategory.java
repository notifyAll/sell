package com.imooc.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目表
 */
@Entity
@DynamicInsert  /*动态插入  insert into product_category (category_name, category_type) values (?, ?)  自动忽略掉两个 时间 null*/
@DynamicUpdate  /* 动态更新注解 数据库中updatetime是自动更新的 如果不用该注解  每次保存都会覆盖  都修改数据没变化时不会update数据*/
@Data /*lombok 的  可以自动生成get set  甚至tostring*/
public class ProductCategory {
//    类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  /*自增类型*/
    private  Integer categoryId;
//    类目名字
    private  String categoryName;
//    类目编号
    private Integer categoryType;

    private Date createTime;

    private  Date updateTime;
}
