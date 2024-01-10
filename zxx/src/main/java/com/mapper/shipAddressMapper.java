package com.mapper;

import com.Pojo.commodity;
import com.Pojo.shipAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface shipAddressMapper {
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

    //修改地址的状态
    int updateAddressByaddress_state(String address_id);

}

