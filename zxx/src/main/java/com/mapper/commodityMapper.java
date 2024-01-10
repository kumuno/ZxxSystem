package com.mapper;

import com.Pojo.commodity;
import com.Pojo.search;
import com.Pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface commodityMapper {
        //添加商品
        int insertCommodity(commodity commodity);

        //通过商品序号删除商品
        int deleteByCommodityId(String commodity_id);

        //修改商品信息
        int updateCommodityById(String commodity_id);

        //查找所有商品
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

        //查找所有上架商品
        List<commodity> findAllCommoditySelling();

        //查找所有下架架商品
        List<commodity> findAllCommodityNoSelling();

        //按照类别进行查询
        List<commodity> findByCommodityCategory();

        //通过商品id查询商品
        commodity findByTypeCommodityByid(String commodity_id);



        //直接购买方式：订单通过商品id、商品属性id和商品子属性id精准查询商品信息
        commodity oderFindOneCommodity(String commodity_id , String Attribute_id , String sonAttribute_id);

        //更新商品的销售量库存量
        int updateCommodityNumber(String commodity_id ,  String sonAttribute_id, int commodity_number, int commodity_sales);

        //按照售价大小&&销量进行从大到小排列
//        List<commodity> arrangeCommodity();
}
