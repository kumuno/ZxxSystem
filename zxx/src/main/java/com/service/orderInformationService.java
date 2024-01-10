package com.service;

import com.Pojo.orderInformation;
import com.Pojo.shipAddress;
import com.common.ResultInfo;

import java.util.Map;

public interface orderInformationService {
    //订单查找用户地址
    public shipAddress orderFindAddress(String addressId , String openId);

    //订单查找已经选择购买的商品信息
    public Map<String,Object> orderFindCart(String openId);

    //查询直接购买商品信息
    public Map<String,Object> orderFindDirectGood(String commodity_id, String attribute_id, String sonAttribute_id, int number);

    //用户提交订单
    public ResultInfo<Object> orderSubmit(String checkedGoodsList,String openId, String addressId, String postscript, Double freightPrice,
                                          Double goodsTotalPrice, Double actualPrice, Integer goodsCount,Integer addType);

    //更新微信支付的信息
    public ResultInfo<Object> orderUpdatePayInformation(String openId, String orderId);

    //用户查询所有订单
    public ResultInfo<Object> findAllOrder(String openId,Integer showType);

    //订单查找用户地址
    public ResultInfo<Object> orderByTypeNumber(String openId);

    //通过order_id查询该订单的所有信息
    public ResultInfo<Object> selectByOrderId(String order_id);

    //通过order_id查询该总订单的商品订单所有信息
    public ResultInfo<Object> findOrderGoodsByOrderId(String order_id);

    //用户取消或者删除订单
    public ResultInfo<Object> orderCancel(String order_id,String user_id);

    //修改总订单的备注信息
    public ResultInfo<Object> updateOrderRemark(String order_id,String user_id,String order_remark);

    //用户申请退款
    public ResultInfo<Object> updateApplyOrderRefund(String order_id,String user_id,String order_refund_reason,String order_refund_instructions,String order_refund_image);

    //修改订单状态为已收货
    public ResultInfo<Object> updateOrderStatusToArrive(String order_id,String user_id);
}
