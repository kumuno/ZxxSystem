package com.service.impl;

import com.mapper.couponsMapper;
import com.service.couponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("couponsServiceImpl")
public class couponsServiceImpl implements couponsService {

    @Lazy
    @Autowired
    private couponsMapper couponsMapper;
}
