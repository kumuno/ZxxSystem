package com.mapper;

import com.Pojo.commodityAttribute;
import com.Pojo.commoditySonAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface commoditySonAttributeMapper {
    //通过商品id查询规格
    List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id);

    //更新商品子属性
    public void updateCommoditySonAttribute(String sonAttribute_id,String Attribute_id,String Attribute_name,double commodity_price,int commodity_number);

    //查找当前子商品属性
    public commoditySonAttribute queryCommoditySonAttribute(String sonAttribute_id);

    //添加商品子属性
    public void addCommoditySonAttribute(commoditySonAttribute commoditySonAttribute);

    //删除对应商品属性
    public void deleteCommoditySonAttribute(String sonAttribute_id);

    //删除对应商品属性
    public int updateCommoditySonAttribute_state(commoditySonAttribute commoditySonAttribute);

    //删除对应商品属性
    public int updateCommoditySonAttribute_stateByAttribute_id(commoditySonAttribute commoditySonAttribute);

    //查找当前子商品属性(通过商品属性ID)
    public List<commoditySonAttribute> querySonAttributeAndAttribute(String Attribute_id);

    //查找当前子商品属性是否存在(通过商品属性ID)
    public commoditySonAttribute querySonAttributeAndAttributeState(String Attribute_id);

    //真正的删除商品子属性
    public int deleteByCommoditySonAttributeForever(String Attribute_id);

    //通过sonAttribute_id真正的删除
    public int deleteByCommoditySonAttributeForeverBySonAttribute_id(String sonAttribute_id);

    //真正的删除商品子属性
    public int deleteCommoditySonAttributeByAttribute_id(String Attribute_id);

    //通过Attribute_id获取商品子属性表的所有sonAttribute_id
    public List<String> getAllSonAttribute_idByAttribute_id(String Attribute_id);

    //通过Attribute_id获取商品子属性表的所有commoditySonAttribute信息
    public List<commoditySonAttribute> getAllCommoditySonAttributeInformationByAttribute_id(String Attribute_id);
}
