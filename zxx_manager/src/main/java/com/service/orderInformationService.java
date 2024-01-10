package com.service;

import com.Pojo.orderInformation;
import com.github.pagehelper.PageInfo;
import com.util.ResponseJSONResult;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public interface orderInformationService {
    //管理员分页查看所有订单信息
    public PageInfo<orderInformation> findAllUserOrder(Integer pageNum);

    //通过order_id查询该订单的所有信息
    orderInformation selectByOrderId(String order_id);

    //更新订单信息
    public ResponseJSONResult updateOrderInformation(String orderInformation, String shipAddress);

    //删除订单信息（把删除标志从0改为1）
    public ResponseJSONResult deleteOrderInformation(String order_id);

    //回归删除订单信息（把删除标志从1改为0）
    public ResponseJSONResult returnDeleteOrderInformation(String order_id);

    //搜索订单
    public PageInfo<orderInformation> SearchUserOrder(Integer pageNum,String order_id, String user_id);

    //统计订单状态的数量
    public ResponseJSONResult countOrderStatus();

    //统计一些重要的信息
    public ResponseJSONResult countInformation();

    //获取所有的订单总价格和收货时间
    public ResponseJSONResult getAllOrderSumPriceAndConfirmTime();

    //销售总量图表
    public ResponseJSONResult getOrderAccountAndConfirmTimeAndCommodityType();
}
