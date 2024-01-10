package com.service.impl;

import com.mapper.incomeMapper;
import com.service.incomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("incomeServiceImpl")
public class incomeServiceImpl implements incomeService {

    @Lazy
    @Autowired
    private incomeMapper incomeMapper;
}
