package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//购物车
@Data
@AllArgsConstructor
@ToString
public class shopCart {
    private String shopcart_id;// 购物车id
    private String user_id;// 用户id
    private String commodity_id;// 商品id
    private int shopcart_amount;// 商品数量
    private int shopcart_sign;// 删除标志字段（0-未删除，1-删除）
    private int checked; //购物车选中商品标志位（0-未选中，1-选中）
    private Timestamp shopcart_createTime;// 添加商品时的时间
    private String sonAttribute_id; //商品属性
    //关联属性 一对多关系（一个商品有多个属性）
    private commodity commodity;
    //关联属性 一对多关系（一个属性有多个子属性）
    private commoditySonAttribute commoditySonAttribute;

    public shopCart() {
    }

}
