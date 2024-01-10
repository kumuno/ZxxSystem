package com.mapper;

import com.Pojo.shipAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface shipAddressMapper {
//    添加用户收货地址
    public int addShipAddress(shipAddress shipAddress);
}
