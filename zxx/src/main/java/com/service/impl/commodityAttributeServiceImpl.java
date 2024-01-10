package com.service.impl;

import com.mapper.commodityAttributeMapper;
import com.service.commodityAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("commodityAttributeServiceImpl")
public class commodityAttributeServiceImpl implements commodityAttributeService {

    @Lazy
    @Autowired
    private commodityAttributeMapper commodityAttributeMapper;
}
