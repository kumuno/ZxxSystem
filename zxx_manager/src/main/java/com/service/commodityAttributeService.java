package com.service;

import com.Pojo.boss;
import com.Pojo.commodityAttribute;
import com.util.ResponseJSONResult;

import java.util.List;


public interface commodityAttributeService {

    public void updateCommodityAttribute(commodityAttribute commodityAttribute);

    //查找当前商品属性
    public List<commodityAttribute> queryCommodityAttribute(String Attribute_id);

    //查找当前商品属性是否存在
    public commodityAttribute queryCommodityAttributeState(String Attribute_id);

    //添加商品属性
    public String addCommodityAttribute(String commodityAttribute);

    //删除对应商品子属性
    public ResponseJSONResult deleteCommodityAttribute(String Attribute_id, int returnOrDelete);

}
