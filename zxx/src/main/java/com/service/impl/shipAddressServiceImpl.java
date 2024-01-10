package com.service.impl;

import com.Pojo.orderInformation;
import com.Pojo.shipAddress;
import com.mapper.orderInformationMapper;
import com.mapper.shipAddressMapper;
import com.service.shipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shipAddressServiceImpl")
public class shipAddressServiceImpl implements shipAddressService {
    @Lazy
    @Autowired
    private shipAddressMapper shipAddressMapper;

    @Lazy
    @Autowired
    private com.mapper.orderInformationMapper orderInformationMapper;


    @Override
    public shipAddress findShipAdderssByaddress_id(String address_id) {
        return shipAddressMapper.findShipAdderssByaddress_id(address_id);
    }

    @Override
    public int insertshipAddress(shipAddress shipAddress) {
        return shipAddressMapper.insertshipAddress(shipAddress);
    }

    @Override
    public List<shipAddress> findShipAdderssByuser_id(String user_id) {
        return shipAddressMapper.findShipAdderssByuser_id(user_id);
    }

    @Override
    public int updateshipAddressByaddress_id(shipAddress shipAddress) {
        return shipAddressMapper.updateshipAddressByaddress_id(shipAddress);
    }

    @Override
    public shipAddress findShipAdderssByaddress_default(String user_id) {
        return shipAddressMapper.findShipAdderssByaddress_default(user_id);
    }

    @Override
    public int deleteshipAddressByaddress_id(String address_id) {
        //先判断订单表里面有没有我的这个地址id，有的话就将地址状态改为0
        List<orderInformation> list = orderInformationMapper.findOrderByAddress_id(address_id);
        if (list == null){
            return shipAddressMapper.deleteshipAddressByaddress_id(address_id);
        }else{
            return shipAddressMapper.updateAddressByaddress_state(address_id);
        }
    }
}
