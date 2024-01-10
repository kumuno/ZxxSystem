package com.mapper;

import com.Pojo.orderInformation;
import com.Pojo.shipAddress;
import com.Pojo.user;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.util.ResponseJSONResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Vector;

@Mapper
@Repository
public interface orderInformationMapper extends BaseMapper<orderInformation> {
    //管理员查看所有订单信息
    public List<orderInformation> findAllUserOrder();

    //通过order_id查询该订单的所有信息
    orderInformation selectByOrderId(String order_id);

    //删除订单信息（把删除标志从0改为1）
    public int deleteOrderInformation(String order_id);

    //回归删除订单信息（把删除标志从1改为0）
    public int returnDeleteOrderInformation(String order_id);

    //搜索订单
    public List<orderInformation> SearchUserOrder(String order_id, String user_id);

    //替换订单信息的用户收货地址ID为最新的
    public int updateOrderInformationAddressId(String order_id, String address_id);

    //统计订单状态的数量
    public List<Map<String,Integer>> countOrderStatus();

    //统计总营业额（已收货订单的总金额）
    Double countTotalSales();

    //统计总订单数
    Integer countTotalOrderNumber();

    //统计销售量（已收货订单的商品总数量）
    Integer countTotalSellNumber();

    //统计顾客总数
    Integer countTotalUserNumber();

    //获取所有的订单总价格和收货时间
    public List<Map<String,Double>> getAllOrderSumPriceAndConfirmTime();

    //销售总量图表
    public List<Map<String,Object>> getOrderAccountAndConfirmTimeAndCommodityType();

    //获取订单的总价
    Double findOrderSumPriceByOrder_id(String order_id);

    //获取该用户当前的积分
    public int getUserIntegral(@Param("user_id") String user_id);

    //更新用户当前积分
    public int updateUserIntegral(user user);
}
