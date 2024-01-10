package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//发货地址
@Data
@AllArgsConstructor
@ToString
public class shipAddress {
    private String address_id;// 发货地址id
    private String user_id;// 用户id
    private String regionAddress;// 地区信息（省-市-区）
    private String detailAddress;// 详细地址信息
    private String address_contact;// 联系人
    private String address_phone;// 联系电话
    private int address_default;// 1表示为用户的默认地址，0表示不为用户的默认地址
    private int address_state;//  1显示客户的地址，0表示不显示
    public shipAddress() {
    }

}