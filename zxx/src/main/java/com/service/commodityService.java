package com.service;

import com.Pojo.commodity;
import com.Pojo.search;
import com.Pojo.user;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface commodityService {

    //查询所有商品信息
    List<commodity> findAllCommodity();

    //按类型所有商品
    List<commodity> findByTypeCommodity(String commodity_type);

    //通过商品名字模糊搜索查询商品
    List<commodity> findCommodityBySearch(commodity commodity);

    //查找所有商品类型
    List<Map<String,String>> findAllCommodityType();

    //添加搜索记录
    int insertSearch(search search);

    //按客户获取历史记录
    List<search> findByUserSearch(String user_id);

    //查询该用户有没有搜索过该关键词
    search findSearchWordByUser_idAndSearch_content(String user_id, String search_content);

    //通过客户ID获取全部客户信息
    user selectByUserId(String user_id);

    //客户清除个人搜索记录
    int deleteByUserSearch(String user_id);

    //通过商品id查询商品信息
    commodity findByTypeCommodityByid(String commodity_id);



    //直接购买方式：订单通过商品id、商品属性id和商品子属性id精准查询商品信息
    commodity oderFindOneCommodity(String commodity_id , String Attribute_id , String sonAttribute_id);
}
