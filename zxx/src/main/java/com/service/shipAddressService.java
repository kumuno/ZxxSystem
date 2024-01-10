package com.service;

import com.Pojo.shipAddress;

import java.util.List;

public interface shipAddressService {
    //通过地址id获取地址信息
    shipAddress findShipAdderssByaddress_id(String address_id);

    //添加地址
    int insertshipAddress(shipAddress shipAddress);

    //通过用户id获取地址
    List<shipAddress> findShipAdderssByuser_id(String user_id);

    //修改地址通过地址id
    int updateshipAddressByaddress_id(shipAddress shipAddress);

    //查看默认地址的位置
    shipAddress findShipAdderssByaddress_default(String user_id);

    //通过id删除地址
    int deleteshipAddressByaddress_id(String address_id);
}
