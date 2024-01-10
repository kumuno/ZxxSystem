package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//商品属性
@Data
@AllArgsConstructor
@ToString
public class commodityAttribute {
    private String Attribute_id;// 商品属性id
    private String commodity_id;// 商品id
    private String commodityAttribute_name;// 商品属性名称
    private int commodityAttribute_state ;// 商品属性存在状态 0-不存在 1-存在

    public commodityAttribute() {
    }
}

