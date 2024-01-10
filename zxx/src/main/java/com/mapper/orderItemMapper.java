package com.mapper;


import com.Pojo.orderInformation;
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


}
