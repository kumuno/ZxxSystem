package com.service.impl;

import com.Pojo.commodityAttribute;
import com.Pojo.commoditySonAttribute;
import com.Pojo.shopCart;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.commodityAttributeMapper;
import com.mapper.commoditySonAttributeMapper;
import com.mapper.orderItemMapper;
import com.mapper.shopCartMapper;
import com.service.commodityAttributeService;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("commodityAttributeServiceImpl")
public class commodityAttributeServiceImpl implements commodityAttributeService {


    @Lazy
    @Autowired
    private commodityAttributeMapper commodityAttributeMapper;

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
    public void updateCommodityAttribute(commodityAttribute commodityAttribute) {
        System.out.println("====================service业务层，商品属性更新信息========================");
        System.out.println(commodityAttribute);
        commodityAttributeMapper.updateCommodityAttribute(commodityAttribute.getAttribute_id(),commodityAttribute.getCommodity_id(),commodityAttribute.getCommodityAttribute_name());
    }

    @Override
    public List<commodityAttribute> queryCommodityAttribute(String Attribute_id) {
        System.out.println("====================service业务层，查询当前商品属性========================");
        return  commodityAttributeMapper.queryCommodityAttribute(Attribute_id);
    }

    @Override
    public commodityAttribute queryCommodityAttributeState(String Attribute_id) {
        System.out.println("====================service业务层，查询当前商品属性是否存在========================");
        return  commodityAttributeMapper.queryCommodityAttributeState(Attribute_id);
    }

    @Override
    public String addCommodityAttribute(String commodityAttribute) {
        System.out.println("===============IMPL层添加商品属性============");
        JSONObject jsonObject = JSONArray.parseObject(commodityAttribute);
        commodityAttribute commodityAttribute1 = new commodityAttribute();
        String Attribute_id = String.valueOf("A"+ (int) (Math.random() * 100000));
        while(commodityAttributeMapper.queryCommodityAttributeState(Attribute_id) != null){
            Attribute_id = String.valueOf("A"+ (int) (Math.random() * 100000));
        }
        System.out.println(commodityAttribute);
        System.out.println(Attribute_id);
        commodityAttribute1.setAttribute_id(Attribute_id);
        commodityAttribute1.setCommodity_id(String.valueOf(jsonObject.get("commodity_id")));
        commodityAttribute1.setCommodityAttribute_name(String.valueOf(jsonObject.get("commodityAttribute_name")));
        commodityAttribute1.setCommodityAttribute_state(1);
        commodityAttributeMapper.addCommodityAttribute(commodityAttribute1);
        System.out.println(commodityAttribute1);
        return Attribute_id;
    }

    @Override
    public ResponseJSONResult deleteCommodityAttribute(String Attribute_id, int returnOrDelete) {
        List<String> attributeList = commoditySonAttributeMapper.getAllSonAttribute_idByAttribute_id(Attribute_id);
        if(returnOrDelete == 0){
            System.out.println("====================service业务层，删除商品属性========================");
            List<String> list1 = new LinkedList<>();
            List<String> list2 = new LinkedList<>();
            String message;
            for (String s : attributeList) {
                if (orderItemMapper.findOrderCommodityBySonAttribute_id(s).size() == 0) {
                    commoditySonAttributeMapper.deleteByCommoditySonAttributeForeverBySonAttribute_id(s);//删除和改商品属性有关的商品子属性

                    List<shopCart> list = shopCartMapper.selectBySonAttribute_id(s);
                    System.out.println(list);
                    for (shopCart shopCart : list) {
                        System.out.println(shopCart.getShopcart_id());
                        shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
                    }
                    list1.add(s);
                } else {
                    commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
                    commoditySonAttribute.setSonAttribute_id(s);
                    commoditySonAttribute.setCommoditySonAttribute_state(0);
                    commoditySonAttributeMapper.updateCommoditySonAttribute_state(commoditySonAttribute);

                    //商品改为下架状态，同时把该商品用户的购物车也清空
                    List<shopCart> list = shopCartMapper.selectBySonAttribute_id(s);
                    System.out.println(list);
                    for (shopCart shopCart:list){
                        System.out.println(shopCart.getShopcart_id());
                        shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
                    }

                    list2.add(s);
                }
            }
            if (attributeList.size() == list1.size()){//如果全部商品子属性被删除，改商品属性也删除
                commodityAttributeMapper.deleteByCommodityAttributeByAttribute_id(Attribute_id);//最后删除商品属性
                message = "由于该商品属性的子属性都没有被顾客下单，商品属性本身（商品属性编号）:"+Attribute_id+"和它有关的子属性都被删除，包括（商品子属性编号）："+list1.toString();
            }else {
                commodityAttributeMapper.deleteCommodityAttribute(Attribute_id);//修改该商品属性为下架状态
                message = "由于该商品属性的子属性存在被顾客下单的情况，商品属性本身（商品属性编号）:"+Attribute_id+"已改为下架状态，其商品子属性更改情况包括："+'\n'+
                        list1.toString()+"等商品属性没有被顾客下单，已经把该商品子属性的信息完全清除掉（包括所有顾客的和该商品有关的购物车信息）！！"+'\n'
                        +list2.toString()+"等商品属性已经被顾客下单，已经把该商品子属性的状态改为下架状态，如果需要可点击回归重新进行上架。";
            }
            return new ResponseJSONResult(200, message);
        }
        System.out.println("====================service业务层，回归商品子属性========================");
        for (String s : attributeList) {
            commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
            commoditySonAttribute.setSonAttribute_id(s);
            commoditySonAttribute.setCommoditySonAttribute_state(1);
            commoditySonAttributeMapper.updateCommoditySonAttribute_state(commoditySonAttribute);
        }
        commodityAttributeMapper.deleteCommodityAttribute(Attribute_id);
        return  new ResponseJSONResult(201,"成功回归该商品属性（商品属性编号）："+Attribute_id+"的所有该商品属性的子属性："+attributeList.toString()+"，已改为上架状态");
    }



}
