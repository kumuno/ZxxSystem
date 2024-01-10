package com.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//订单商品信息
@Data
@AllArgsConstructor
@ToString
public class orderItem {
    private String orderItem_id;// 订单商品信息id
    private String order_id;// 总订单id
    private String commodity_id;// 商品id
    private String sonAttribute_id;//商品子属性id
    private int orderItem_amount;// 下单时的商品数量
    private String orderItem_goodname;// 商品名称
    private String Attribute_name;//商品子属性名称
    private String orderItem_goodimage;// 商品主图
    private double orderItem_goodprice;// 下单时的商品价格
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp orderItem_createTime;// 下单时间
    private int orderItem_user_delete;//用户删除订单标字（0-正常。1-已删除）
    //关联属性 一对一关系（一个商品订单有一个商品信息）
    private commodity commodity;
    //关联属性 一对一关系（一个商品订单有一个商品子属性）
    private commoditySonAttribute commoditySonAttribute;
    public orderItem() {
    }
}
