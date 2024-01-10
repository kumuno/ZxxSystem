package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//商品子属性
@Data
@AllArgsConstructor
@ToString
public class commoditySonAttribute {
    private String sonAttribute_id;// 商品子属性id
    private String Attribute_id;// 商品属性id
    private String Attribute_name;// 商品子属性名称
    private double commodity_price;// 商品价格
    private int commodity_number;// 商品库存量
    private int commoditySonAttribute_state ;// 商品子属性存在状态 0-不存在 1-存在
    public commoditySonAttribute() {
    }
}
