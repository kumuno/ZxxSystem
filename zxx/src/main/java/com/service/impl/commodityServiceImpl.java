package com.service.impl;

import com.Pojo.commodity;
import com.Pojo.search;
import com.Pojo.user;
import com.mapper.commodityMapper;
import com.service.commodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("commodityServiceImpl")
public class commodityServiceImpl implements commodityService {

    @Lazy
    @Resource
    private commodityMapper commodityMapper;

    //查询所有商品信息
    public List<commodity> findAllCommodity(){
        return commodityMapper.findAllCommodity();
    }

    //按类型所有商品
    public List<commodity> findByTypeCommodity(String commodity_type){
        return commodityMapper.findByTypeCommodity(commodity_type);
    }
    //通过商品名字模糊搜索查询商品
    public List<commodity> findCommodityBySearch(commodity commodity){
        return commodityMapper.findCommodityBySearch(commodity);
    }

    //查找所有商品类型
    public List<Map<String,String>> findAllCommodityType(){
        return commodityMapper.findAllCommodityType();
    }

    //添加搜索记录
    public int insertSearch(search search){
        return commodityMapper.insertSearch(search);
    }

    //按客户获取历史记录
    public List<search> findByUserSearch(String user_id){
        return commodityMapper.findByUserSearch(user_id);
    }

    //查询该用户有没有搜索过该关键词
    public search findSearchWordByUser_idAndSearch_content(String user_id, String search_content){
        return commodityMapper.findSearchWordByUser_idAndSearch_content(user_id,search_content);
    }

    //通过客户ID获取全部客户信息
    public user selectByUserId(String user_id){
        return commodityMapper.selectByUserId(user_id);
    }

    //客户清除个人搜索记录
    public int deleteByUserSearch(String user_id){
        return commodityMapper.deleteByUserSearch(user_id);
    }

    @Override
    public commodity findByTypeCommodityByid(String commodity_id) {
        return commodityMapper.findByTypeCommodityByid(commodity_id);
    }



    //直接购买方式：订单通过商品id、商品属性id和商品子属性id精准查询商品信息
    @Override
    public commodity oderFindOneCommodity(String commodity_id , String Attribute_id , String sonAttribute_id){
        return commodityMapper.oderFindOneCommodity(commodity_id , Attribute_id , sonAttribute_id);
    }

}
