package com.Pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

//订单信息
@Data
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("orderInformation")
public class orderInformation implements Serializable{
    @TableId(type = IdType.AUTO)// 指定哪个属性是主键
    private String order_id;// 总订单id
    @TableField("order_account")
    private int order_account;// 商品总数量
    @TableField("user_id")
    private String user_id;// 用户id
    @TableField("good_sumprice")
    private double good_sumprice;//商品总价
    @TableField("order_sumPrice")
    private double order_sumPrice;// 订单总价（包括了运费、优惠卷的金额）
    @TableField("order_paymentTime")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_paymentTime;// 支付时间
    @TableField("order_status")
    private String order_status;// 订单状态（待发货、已发货、交易完成、已退款、未支付、已支付）
    @TableField("order_way")
    private String order_way;// 支付方式（微信，支付宝）
    @TableField("order_remark")
    private String order_remark;// 订单备注
    @TableField("address_id")
    private String address_id;// 收货地址等信息
    @TableField("order_user_delete")
    private int order_user_delete ;// 用户删除标字（0-未删除。1-已删除）
    @TableField("order_createTime")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private Timestamp order_createTime;// 总订单创建时间
    @TableField("order_newCreateTime")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_newCreateTime;// 总订单最新修改时间
    @TableField("order_refund_reason")
    private String order_refund_reason;//退款原因
    @TableField("order_refund_instructions")
    private String order_refund_instructions;//退款说明
    @TableField("order_refund_image")
    private String order_refund_image;//退款图片
    @TableField("order_shipping_time")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_shipping_time;// 发货时间（后台设置）
    @TableField("order_confirm_time")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_confirm_time;// 收货时间
    @TableField("order_apply_refundTime")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_apply_refundTime;// 申请退款时间
    @TableField("order_refund_time")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_refund_time;// 退款时间（后台设置）
    @TableField("order_predict_predictTime")@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd'T'hh:mm")
    private Timestamp order_predict_predictTime;// 预计到货时间（后台设置）
    //关联属性 一对多关系（一个总订单有多个订单商品信息）
    private List<orderItem> orderItem;
    //关联属性 一对一关系（一个总订单有一个收货地址）
    private shipAddress shipAddress;
    //关联属性 一对一关系（一个总订单有一个客户）
    private user user;
    public orderInformation() {
    }

}
