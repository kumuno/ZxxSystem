package com.mapper;

import com.Pojo.shopCart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface shopCartMapper {
    // 通过商品Id查询购物车ID
    public List<shopCart> selectByCommodityId(String commodity_id);

    // 通过sonAttribute_id查询购物车ID
    public List<shopCart> selectBySonAttribute_id(String sonAttribute_id);

    //删除对应的购物车
    public int deleteShopCart(String shopcart_id);

}
