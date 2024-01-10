package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

//商品
@Data
@AllArgsConstructor
@ToString
public class commodity {

    private String commodity_id;// 商品id
//    private String address_id;// 发货地址id
    private String commodity_name;// 商品名称
    private String commodity_introduce;// 商品介绍
    private String commodity_img;// 商品主图片
    private String commodity_carousel_Img;// 商品轮播图
    private int commodity_sales;// 商品销售量
    private String commodity_type;// 商品类型
    private String commodity_tag;// 商品标签
    private int commodity_state ;// 商品上架状态 0-下架 1-上架
    private String sendPhone;// 发货地址电话
    private String sendAddress;// 发货地址
    private String sendPeopleName;// 发货人姓名
    private String commodity_flag;// 备用
    //关联属性 一对多关系（一个商品有多个属性）
    private List<commodityAttribute> commodityAttribute;
    //关联属性 一对多关系（一个属性有多个子属性）
    private List<commoditySonAttribute> commoditySonAttribute;
    //多字段动态排序
    private Map<String,String> sortMap;

    public commodity() {
    }
}
