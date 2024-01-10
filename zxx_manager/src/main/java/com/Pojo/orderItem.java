package com.Pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

//订单商品信息
@Data
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("orderItem")
public class orderItem implements Serializable {
    @TableId(type = IdType.AUTO)// 指定哪个属性是主键
    private String orderItem_id;// 订单商品信息id
    @TableField("order_id")
    private String order_id;// 总订单id
    @TableField("commodity_id")
    private String commodity_id;// 商品id
    @TableField("sonAttribute_id")
    private String sonAttribute_id;//商品子属性id
    @TableField("orderItem_amount")
    private double orderItem_amount;// 下单时的商品数量
    @TableField("orderItem_goodname")
    private String orderItem_goodname;// 商品名称
    @TableField("Attribute_name")
    private String Attribute_name;//商品子属性名称
    @TableField("orderItem_goodimage")
    private String orderItem_goodimage;// 商品主图
    @TableField("orderItem_goodprice")
    private double orderItem_goodprice;// 下单时的商品价格
    @TableField("orderItem_createTime")
    private Timestamp orderItem_createTime;// 下单时间
    @TableField("orderItem_user_delete")
    private int orderItem_user_delete;//用户删除订单标字（0-正常。1-已删除）
    private commodity commodity;
    private commoditySonAttribute commoditySonAttribute;
    public orderItem() {
    }
}
