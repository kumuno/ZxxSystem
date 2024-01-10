package com.service;

import com.Pojo.commoditySonAttribute;
import com.util.ResponseJSONResult;

import java.util.List;

public interface commoditySonAttributeService {
    //通过商品id查询规格
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id);

    //更新商品子属性
    public ResponseJSONResult updateCommoditySonAttribute(commoditySonAttribute commoditySonAttribute);

    //查找当前子商品属性
    public commoditySonAttribute queryCommoditySonAttribute(String sonAttribute_id);

    //添加商品子属性
    public void addCommoditySonAttribute(String commoditySonAttribute);

    //添加商品子属性(调用方法不同)
    public void addCommoditySonAttribute2(commoditySonAttribute commoditySonAttribute);

    //删除对应商品属性
    public ResponseJSONResult deleteCommoditySonAttribute(String sonAttribute_id, int returnOrDelete);

    //查找当前子商品属性(通过商品属性ID)
    public List<commoditySonAttribute> querySonAttributeAndAttribute(String Attribute_id);

    //查找当前子商品属性是否存在(通过商品属性ID)
    public commoditySonAttribute querySonAttributeAndAttributeState(String Attribute_id);
}
