package com.mapper;

import com.Pojo.commodity;
import com.Pojo.commodityAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface commodityAttributeMapper {
    public void updateCommodityAttribute(String Attribute_id,String commodity_id,String commodityAttribute_name);

    //查找当前商品属性
    public List<commodityAttribute> queryCommodityAttribute(String Attribute_id);

    //查找当前商品属性是否存在
    public commodityAttribute queryCommodityAttributeState(String Attribute_id);

    //添加商品属性
    public void addCommodityAttribute(commodityAttribute commodityAttribute);

    //删除对应商品属性
    public void deleteCommodityAttribute(String Attribute_id);

    //真正的删除对应商品属性
    public int deleteByCommodityAttributeForever(String commodity_id);

    //获取一个商品id的所有商品属性
    public List<String> getAllAttribute_id(String commodity_id);

    //通过commodity_id删除对应商品属性（改为下架状态）
    public int deleteCommodityAttributeByCommodity_id(String commodity_id);

    //通过commodity_id查找当前商品的所有属性
    public List<commodityAttribute> getCommodityAttributeByCommodity_id(String commodity_id);

    //通过Attribute_id真正的删除对应商品
    public int deleteByCommodityAttributeByAttribute_id(String Attribute_id);

    public int updateCommodityAttribute_state(String Attribute_id, int commodityAttribute_state);

}
