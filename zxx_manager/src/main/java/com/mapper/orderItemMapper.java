package com.mapper;


import com.Pojo.orderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface orderItemMapper {
    //添加商品订单信息
    int insertOrderItem(orderItem orderItem);

    //通过order_id查询该总订单的商品订单所有信息
    List<orderItem> findOrderGoodsByOrderId(String order_id);

    //通过商品id判断该订单是否有数据关联
    List<orderItem> findOrderCommodity(String commodity_id);

    //通过sonAttribute_id判断该订单是否有数据关联
    List<orderItem> findOrderCommodityBySonAttribute_id(String sonAttribute_id);
}
