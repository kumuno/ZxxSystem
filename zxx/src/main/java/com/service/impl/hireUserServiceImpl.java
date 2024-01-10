package com.service.impl;

import com.mapper.hireUserMapper;
import com.service.hireUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("hireUserServiceImpl")
public class hireUserServiceImpl implements hireUserService {
    @Lazy
    @Autowired
    private hireUserMapper hireUserMapper;
}
