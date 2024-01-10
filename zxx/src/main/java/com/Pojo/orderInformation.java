package com.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

//订单信息
@Data
@AllArgsConstructor
@ToString
public class orderInformation {
    private String order_id;// 总订单id
    private int order_account;// 商品总数量
    private String user_id;// 用户id
    private double good_sumprice;//商品总价
    private double order_sumPrice;// 订单总价（包括了运费、优惠卷的金额）
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_paymentTime;// 支付时间
    private String order_status;// 订单状态（待付款、待发货、待发货、已收货、待退款、已退款）
    private String order_way;// 支付方式（微信，支付宝）
    private String order_remark;// 订单备注
    private String address_id;// 收货地址等信息
    private int order_user_delete ;// 用户删除标字（0-未删除。1-已删除）
    //这个不需要换成年月日，直接用它原来单位为ms的数字就可以，因为要用它来算取消待付款订单的时间
    //@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private String order_refund_reason;//退款原因
    private String order_refund_instructions;//退款说明
    private String order_refund_image;//退款图片
    private Timestamp order_createTime;// 总订单创建时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_newCreateTime;// 总订单最新修改时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_shipping_time;// 发货时间（后台设置）
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_confirm_time;// 收货时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_apply_refundTime;// 申请退款时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_refund_time;// 退款时间（后台设置）
    //这个也一样不需要换成年月日，直接用它原来单位为ms的数字就可以，因为要用它来算预计到货的时间
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp order_predict_predictTime;// 预计到货时间（后台设置）
    //关联属性 一对多关系（一个总订单有多个订单商品信息）
    private List<orderItem> orderItem;
    //关联属性 一对一关系（一个总订单有一个收货地址）
    private shipAddress shipAddress;
    public orderInformation() {
    }
}
