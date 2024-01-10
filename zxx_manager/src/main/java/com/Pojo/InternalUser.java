package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//内部成员
@Data
@AllArgsConstructor
@ToString
public class InternalUser {
    private String  InternalUser_id;// 内部成员id
    private String  InternalUser_name;// 内部成员姓名
    private String  InternalUser_password;// 内部成员密码
    private String  InternalUser_image;// 内部成员头像
    private String  InternalUser_phone;//内部成员电话
    private Timestamp InternalUser_createTime;// 用户创建时间
    private double InternalUser_revenue;// 内部成员收入
    private String  address_id;// 内部成员发货地址
    private int InternalUser_is_deleted;// 注销标识字段(0-正常 1-已锁定)

    public InternalUser() {
    }

}
