package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//雇佣人员
@Data
@AllArgsConstructor
@ToString
public class hireUser {
    private String hireUser_id;// 雇佣人员id
    private String hireUser_name;// 雇佣人员姓名
    private String hireUser_identity;// 雇拥人员身份
    private String hireUser_phone;// 雇佣人员手机号码
    private String hireUser_identityacard;// 雇佣人员身份证
    private String hireUser_image;// 雇佣人员头像
    private double hireUser_proportion;// 雇佣人员收益比例
    private int hireUser_tradednumber;// 雇佣人员完成的订单数
    private int hireUser_is_deleted ;//注销标识字段(0-正常 1-已锁定)

    public hireUser() {
    }
}
