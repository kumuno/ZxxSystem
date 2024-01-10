package com.mapper;

import com.Pojo.shopCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface shopCartMapper {
    //新建购物车
    public int shopCartEnroll(shopCart shopchar);

    // 通过用户Id查询购物车ID
    public String selectByShopCartID(String sonAttribute_id);

    //通过购物车ID查询购物车信息
    public shopCart selectByshopCartID(String shopCart_id);

    //添加商品进入购物车（商品详情页）
    public int addCommodity(shopCart shopchar);

    //通过购物车ID更新信息
    public int updateBySonAttributeId(double number2,String sonAttribute_id);

    //购物车添加对应商品信息
    public int shopCartAddCommodity(String shopCart_id);

    //计算购物车总数量
    public Integer sum_shopcart_amount(String user_id);

    //通过openId查询购物车中所有子属性id
    public List<shopCart> allAttribute(String user_id);

    //通过购物车ID更新checked
    public int updateChecked(int checked,String sonAttribute_id);

    //通过shopcart_id查找对应用户购物车中选中的信息
    public List<shopCart> selectCheckedByshopCartID(String user_id);

    //通过购物车ID更新购物车商品数量
    public int updateShopCartAmount(int shopcart_amount,String shopcart_id);

    //删除对应的购物车
    public int deleteShopCart(String shopcart_id);

    //选中所有商品
    public int CartCheckedAll();

    //取消选中所有商品
    public int CancelCartCheckedAll();
}
