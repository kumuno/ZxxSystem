package com.service.impl;

import com.mapper.internalUserMapper;
import com.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("InternalUserServiceImpl")
public class InternalUserServiceImpl implements InternalUserService {

    @Autowired
    @Lazy
    private internalUserMapper internalUserMapper;
}
