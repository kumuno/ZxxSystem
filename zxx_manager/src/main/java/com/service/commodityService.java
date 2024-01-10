package com.service;

import com.Pojo.commodity;
import com.Pojo.search;
import com.github.pagehelper.PageInfo;
import com.util.ResponseJSONResult;

import java.util.List;
import java.util.Map;

public interface commodityService {


    //分页
    public PageInfo<commodity> findAllCommodityByPage(Integer pageNum);

    //修改状态位
    public void deleteByCommodityId(String commodity_id);

    //删除对应商品
    public ResponseJSONResult deleteByCommodityForever(String commodity_id);

    //回归被删除商品
    public ResponseJSONResult returnDeleteByCommodityForever(String commodity_id);

    //通过商品id查询该商品所有信息
    public commodity queryByCommodityId(String commodity_id);

    //更新商品信息
    public ResponseJSONResult updateCommodity(commodity commodity);

    //添加商品
    public String addCommodity(commodity commodity);

    //商品的多条件查询
    public PageInfo<commodity> conditionalQueriesCommodity(Integer pageNum, String keyword1, String keyword2);

    //商品修改/新增存储数据库照片路径
    public boolean UpdateCommodity_url(commodity commodity);
}
