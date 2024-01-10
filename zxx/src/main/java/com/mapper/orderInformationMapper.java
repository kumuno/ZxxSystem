package com.mapper;

import com.Pojo.orderInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface orderInformationMapper {
    //添加订单
    int insertOrderInformation(orderInformation orderInformation);

    //修改总订单的状态
    int updateOrderStatus(orderInformation orderInformation);

    //用户查询所有订单
    List<orderInformation> findAllOrder(String user_id,String order_status);

    //统计各类型的订单数量
    int orderByTypeNumber(String user_id,String order_status);

    //通过order_id查询该订单的所有信息
    orderInformation selectByOrderId(String order_id);

    //用户取消或者删除订单
    int orderCancel(String order_id,String user_id);

    //修改总订单的备注信息
    int updateOrderRemark(String order_id,String user_id,String order_remark);

    //用户申请退款
    int updateApplyOrderRefund(orderInformation orderInformation);

    //查找有没有关于地址id的订单
    List<orderInformation> findOrderByAddress_id(String address_id);

    //获取订单的总价
    Double findOrderSumPriceByOrder_id(String order_id);


}
