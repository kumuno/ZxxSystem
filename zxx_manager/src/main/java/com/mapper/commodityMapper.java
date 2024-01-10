package com.mapper;

import com.Pojo.boss;
import com.Pojo.commodity;
import com.Pojo.hireUser;
import com.Pojo.search;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface commodityMapper {

        //分页
        public List<commodity> findAllCommodityByPage();

        //修改状态位
        public void deleteByCommodityId(String commodity_id);

        //删除对应商品
        public void deleteByCommodityForever(String commodity_id);

        //通过商品id查询该商品所有信息
        public commodity queryByCommodityId(String commodity_id);

        public int updateCommodityState(String commodity_id,int commodity_state);

        //更新商品信息
        public void updateCommodity(commodity commodity);

        //添加商品
        public boolean addCommodity(commodity commodity);

        //商品的多条件查询
        public List<commodity> conditionalQueriesCommodity(String keyword1, String keyword2);

        //通过商品id查询该商品信息(不包括商品属性，子属性的信息)
        public commodity getCommodityByCommodity_id(String commodity_id);

        public boolean updateCommodity_url(commodity commodity);
}
