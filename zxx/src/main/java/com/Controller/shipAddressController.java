package com.Controller;

import com.Pojo.orderInformation;
import com.Pojo.shipAddress;
import com.mapper.orderInformationMapper;
import com.mapper.shipAddressMapper;
import com.service.impl.commodityServiceImpl;
import com.service.impl.shipAddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shipAddressController")
public class shipAddressController {
    @Resource
    commodityServiceImpl commodityService;

    @Resource
    shipAddressServiceImpl shipAddressService;

    @Autowired
    orderInformationMapper orderInformationMapper;

    @Autowired
    shipAddressMapper shipAddressMapper;

    //通过商品id查询商家的地址
//    @RequestMapping("/findShipAdderssByaddress_id/{id}")
//    @ResponseBody
//    public shipAddress findShipAdderssByaddress_id(@PathVariable String id) throws IOException {
//        shipAddress shipAddress = new shipAddress();
//
//        System.out.println(id);
//        String address_id = commodityService.findAddress_idByCommodity_id(id);
//
//        System.out.println(address_id);
//        shipAddress = shipAddressService.findShipAdderssByaddress_id(address_id);
//
//        System.out.println(shipAddress);
//        return shipAddress;
//    }

    //通过地址id查询地址信息
    @RequestMapping("/findShipAdderssByaddress_idAlone/{id}")
    @ResponseBody
    public shipAddress findShipAdderssByaddress_idAlone(@PathVariable String id) throws IOException {
        shipAddress shipAddress = new shipAddress();
        shipAddress = shipAddressService.findShipAdderssByaddress_id(id);
        System.out.println(shipAddress);
        return shipAddress;
    }


    @RequestMapping("/insertshipAddress")
    @ResponseBody
    //添加地址
    public int insertshipAddress(String address_id,String user_id,String regionAddress,
                                 String detailAddress,String address_contact,
                                 String address_phone,int address_default){
        shipAddress shipAddress = new shipAddress(address_id,user_id,regionAddress,detailAddress,address_contact,address_phone,address_default,1);
        if (address_default==1){
            shipAddress shipAddressDefault = shipAddressService.findShipAdderssByaddress_default(user_id);
            if (shipAddressDefault!=null) {
                shipAddressDefault.setAddress_default(0);
                shipAddressService.updateshipAddressByaddress_id(shipAddressDefault);
            }
        }
        return shipAddressService.insertshipAddress(shipAddress);
    }


    //通过用户id查询地址
    @RequestMapping("/findShipAdderssByuser_id/{user_id}")
    @ResponseBody
    public List<shipAddress> findShipAdderssByuser_id(@PathVariable String user_id){
        List<shipAddress> list = new ArrayList<>();
        list = shipAddressService.findShipAdderssByuser_id(user_id);
        return list;
    }

    //修改地址通过地址id
    @RequestMapping("/updateshipAddressByaddress_id")
    @ResponseBody
    public int updateshipAddressByaddress_id(String user_id,String address_id,String regionAddress,
                                             String detailAddress,String address_contact,
                                             String address_phone,int address_default){
        shipAddress shipAddress = new shipAddress();
        shipAddress.setAddress_id(address_id);
        shipAddress.setAddress_contact(address_contact);
        shipAddress.setAddress_phone(address_phone);
        shipAddress.setDetailAddress(detailAddress);
        shipAddress.setRegionAddress(regionAddress);
        shipAddress.setAddress_default(address_default);
        //先判断订单表里面有没有我的这个地址id，有的话就将地址状态改为0
        List<orderInformation> list = orderInformationMapper.findOrderByAddress_id(address_id);
        if (list == null){
            if (address_default==1){
                shipAddress shipAddressDefault = shipAddressService.findShipAdderssByaddress_default(user_id);
                if (shipAddressDefault!=null) {
                    shipAddressDefault.setAddress_default(0);
                    shipAddressService.updateshipAddressByaddress_id(shipAddressDefault);
                }
            }
            return shipAddressService.updateshipAddressByaddress_id(shipAddress);
        }else{
            shipAddressMapper.updateAddressByaddress_state(address_id);
            shipAddress shipAddressnew = new shipAddress();
            shipAddressnew = shipAddress;
            String newAddressId = "address"+ (int) (Math.random() * 1000000000);
            shipAddressnew.setAddress_id(newAddressId);
            shipAddressnew.setUser_id(user_id);
            return shipAddressService.insertshipAddress(shipAddressnew);
        }
    }


    @RequestMapping("/deleteshipAddressByaddress_id")
    @ResponseBody
        //通过id删除地址
    int deleteshipAddressByaddress_id(String id){
        return shipAddressService.deleteshipAddressByaddress_id(id);
    }



}