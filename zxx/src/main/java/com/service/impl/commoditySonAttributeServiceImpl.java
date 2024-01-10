package com.service.impl;

import com.Pojo.commoditySonAttribute;
import com.mapper.commoditySonAttributeMapper;
import com.service.commoditySonAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commoditySonAttributeServiceImpl")
public class commoditySonAttributeServiceImpl implements commoditySonAttributeService {

    @Lazy
    @Autowired
    private commoditySonAttributeMapper commoditySonAttributeMapper;

    @Override
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id) {
        return commoditySonAttributeMapper.findAllCommoditySonAttributeByid(commodity_id);
    }
}
