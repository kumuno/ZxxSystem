package com.service.impl;

import com.Pojo.shopCart;
import com.mapper.shopCartMapper;
import com.service.shopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shopCartServiceImpl")
public class shopCartServiceImpl implements shopCartService {
    @Lazy
    @Autowired
    private shopCartMapper shopCartMapper;

    public int shopCartEnroll(shopCart shopchar) {
        int flag = 0;
        flag = shopCartMapper.shopCartEnroll(shopchar);
        return flag;
    }

    public String selectByShopCartID(String sonAttribute_id) {
        String shopCartID = shopCartMapper.selectByShopCartID(sonAttribute_id);
        return shopCartID;
    }


    public shopCart selectByshopCartID(String shopCart_id) {
        shopCart shopCart = shopCartMapper.selectByshopCartID(shopCart_id);
        return  shopCart;
    }

    public int addCommodity(shopCart commodity) {
        int flag = 0;
        flag = shopCartMapper.addCommodity(commodity);
        return flag;
    }

    public int updateBySonAttributeId(double number2,String sonAttribute_id) {
        int flag = 0;
        flag = shopCartMapper.updateBySonAttributeId(number2,sonAttribute_id);
        return flag;
    }

    public int shopCartAddCommodity(String shopCart_id) {
        int flag = 0;
        flag = shopCartMapper.shopCartAddCommodity(shopCart_id);
        return flag;
    }

    @Override
    public Integer sum_shopcart_amount(String user_id) {
        return shopCartMapper.sum_shopcart_amount(user_id);
    }

    @Override
    public List<shopCart> allAttribute(String user_id) {
        return shopCartMapper.allAttribute(user_id);
    }

    @Override
    public int updateChecked(int checked, String sonAttribute_id) {
        int flag = 0;
        flag = shopCartMapper.updateChecked(checked,sonAttribute_id);
        return flag;
    }

    @Override
    public List<shopCart> selectCheckedByshopCartID(String user_id) {
        return shopCartMapper.selectCheckedByshopCartID(user_id);
    }

    @Override
    public int updateShopCartAmount(int shopcart_amount, String shopcart_id) {
        int flag = 0;
        flag = shopCartMapper.updateShopCartAmount(shopcart_amount,shopcart_id);
        return flag;
    }

    @Override
    public int deleteShopCart(String shopcart_id) {
        int flag = 0;
        flag = shopCartMapper.deleteShopCart(shopcart_id);
        return flag;
    }

    @Override
    public int CartCheckedAll() {
        int flag = 0;
        flag = shopCartMapper.CartCheckedAll();
        return flag;
    }

    @Override
    public int CancelCartCheckedAll() {
        int flag = 0;
        flag = shopCartMapper.CancelCartCheckedAll();
        return flag;
    }


}
