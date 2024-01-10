package com.service.impl;

import com.Pojo.commoditySonAttribute;
import com.Pojo.shopCart;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.commoditySonAttributeMapper;
import com.mapper.orderItemMapper;
import com.mapper.shopCartMapper;
import com.service.commoditySonAttributeService;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commoditySonAttributeServiceImpl")
public class commoditySonAttributeServiceImpl implements commoditySonAttributeService {

    @Lazy
    @Autowired
    private commoditySonAttributeMapper commoditySonAttributeMapper;

    @Lazy
    @Resource
    private orderItemMapper orderItemMapper;

    @Lazy
    @Resource
    private shopCartMapper shopCartMapper;

    @Override
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id) {
        return commoditySonAttributeMapper.findAllCommoditySonAttributeByid(commodity_id);
    }


    @Override
    public ResponseJSONResult updateCommoditySonAttribute(commoditySonAttribute commoditySonAttribute) {
        System.out.println("====================service业务层，商品子属性更新信息========================");
        if (orderItemMapper.findOrderCommodityBySonAttribute_id(commoditySonAttribute.getSonAttribute_id()).size() == 0) {
            commoditySonAttributeMapper.updateCommoditySonAttribute(commoditySonAttribute.getSonAttribute_id(),commoditySonAttribute.getAttribute_id(),commoditySonAttribute.getAttribute_name(),commoditySonAttribute.getCommodity_price(),commoditySonAttribute.getCommodity_number());
            return  new ResponseJSONResult(200,"更新商品子属性成功");
        }
        String Attribute_id = commoditySonAttribute.getSonAttribute_id();//提前存储，更新原商品信息需要用到
        commoditySonAttribute.setSonAttribute_id("SA"+ (int) (Math.random() * 100000));
        commoditySonAttributeMapper.addCommoditySonAttribute(commoditySonAttribute);

        commoditySonAttribute commoditySonAttribute1 = new commoditySonAttribute();
        commoditySonAttribute1.setSonAttribute_id(Attribute_id);
        commoditySonAttribute1.setCommoditySonAttribute_state(0);
        commoditySonAttributeMapper.updateCommoditySonAttribute_state(commoditySonAttribute1);//改为下架状态

        //商品改为下架状态，同时把该商品用户的购物车也清空
        List<shopCart> list = shopCartMapper.selectBySonAttribute_id(Attribute_id);
        System.out.println(list);
        for (shopCart shopCart:list){
            System.out.println(shopCart.getShopcart_id());
            shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
        }

        System.out.println(commoditySonAttribute);
        return  new ResponseJSONResult(200,"由于顾客已经下单了该商品，无法更改，已经生成新的订单了，旧的订单已自动改为下架状态");
    }

    @Override
    public commoditySonAttribute queryCommoditySonAttribute(String sonAttribute_id) {
        System.out.println("====================service业务层，查询当前商品属性========================");
        return  commoditySonAttributeMapper.queryCommoditySonAttribute(sonAttribute_id);
    }

    @Override
    public void addCommoditySonAttribute(String commoditySonAttribute) {
        System.out.println("===============IMPL层添加商品属性============");
        JSONObject jsonObject = JSONArray.parseObject(commoditySonAttribute);
        commoditySonAttribute commoditySonAttribute1 = new commoditySonAttribute();
        String commoditySonAttribute_id = String.valueOf("SA"+ (int) (Math.random() * 100000));
        while(commoditySonAttributeMapper.querySonAttributeAndAttributeState(commoditySonAttribute_id) != null){
            commoditySonAttribute_id = String.valueOf("SA"+ (int) (Math.random() * 100000));
        }
        commoditySonAttribute1.setAttribute_id(String.valueOf(jsonObject.get("attribute_id")));
        commoditySonAttribute1.setAttribute_name(String.valueOf(jsonObject.get("attribute_name")));
        commoditySonAttribute1.setSonAttribute_id(commoditySonAttribute_id);
        commoditySonAttribute1.setCommodity_price(Double.parseDouble(String.valueOf(jsonObject.get("commodity_price"))));
        commoditySonAttribute1.setCommodity_number(Integer.parseInt(String.valueOf(jsonObject.get("commodity_number"))));
        commoditySonAttribute1.setCommoditySonAttribute_state(1);
        commoditySonAttributeMapper.addCommoditySonAttribute(commoditySonAttribute1);
    }

    @Override
    public void addCommoditySonAttribute2(commoditySonAttribute commoditySonAttribute) {
        System.out.println("===============IMPL层添加商品属性============");
        String commoditySonAttribute_id = String.valueOf("SA"+ (int) (Math.random() * 100000));
        while(commoditySonAttributeMapper.querySonAttributeAndAttributeState(commoditySonAttribute_id) != null){
            commoditySonAttribute_id = String.valueOf("SA"+ (int) (Math.random() * 100000));
        }
        commoditySonAttribute.setAttribute_id(commoditySonAttribute.getAttribute_id());
        commoditySonAttribute.setAttribute_name(commoditySonAttribute.getAttribute_name());
        commoditySonAttribute.setSonAttribute_id(commoditySonAttribute_id);
        commoditySonAttribute.setCommodity_price(commoditySonAttribute.getCommodity_price());
        commoditySonAttribute.setCommodity_number(commoditySonAttribute.getCommodity_number());
        commoditySonAttribute.setCommoditySonAttribute_state(1);
        commoditySonAttributeMapper.addCommoditySonAttribute(commoditySonAttribute);
    }

    @Override
    public ResponseJSONResult deleteCommoditySonAttribute(String sonAttribute_id, int returnOrDelete) {
        if(returnOrDelete == 0){
            System.out.println("====================service业务层，删除商品子属性========================");
            if (orderItemMapper.findOrderCommodityBySonAttribute_id(sonAttribute_id).size() == 0) {
                commoditySonAttributeMapper.deleteByCommoditySonAttributeForeverBySonAttribute_id(sonAttribute_id);
                List<shopCart> list = shopCartMapper.selectBySonAttribute_id(sonAttribute_id);
                System.out.println(list);
                for (shopCart shopCart:list){
                    System.out.println(shopCart.getShopcart_id());
                    shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
                }
                return new  ResponseJSONResult(200,"由于该商品没有被顾客下单，已经把该商品子属性的信息完全清除掉（包括所有顾客的和该商品有关的购物车信息）！！");
            }else {
                commoditySonAttributeMapper.deleteCommoditySonAttribute(sonAttribute_id);
                //改为商品下架状态，同时把该商品用户的购物车也清空
                List<shopCart> list = shopCartMapper.selectBySonAttribute_id(sonAttribute_id);
                System.out.println(list);
                for (shopCart shopCart:list){
                    System.out.println(shopCart.getShopcart_id());
                    shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
                }
                return  new ResponseJSONResult(201,"由于该商品已经被顾客下单，已经把该商品子属性的状态改为下架状态，如果需要可点击回归重新进行上架。");
            }
        }
        System.out.println("====================service业务层，回归商品子属性========================");
        commoditySonAttributeMapper.deleteCommoditySonAttribute(sonAttribute_id);
        return  new ResponseJSONResult(201,"成功回归该商品子属性的所有信息，已改为上架状态");

    }

    @Override
    public List<commoditySonAttribute> querySonAttributeAndAttribute(String Attribute_id) {
        System.out.println("====================service业务层，查询当前商品属性========================");
        return  commoditySonAttributeMapper.querySonAttributeAndAttribute(Attribute_id);
    }

    @Override
    public commoditySonAttribute querySonAttributeAndAttributeState(String Attribute_id) {
        System.out.println("====================service业务层，查询当前商品属性是否存在========================");
        return  commoditySonAttributeMapper.querySonAttributeAndAttributeState(Attribute_id);
    }
}
