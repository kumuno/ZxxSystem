package com.service;

import com.Pojo.commoditySonAttribute;

import java.util.List;

public interface commoditySonAttributeService {
    //通过商品id查询规格
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id);
}
