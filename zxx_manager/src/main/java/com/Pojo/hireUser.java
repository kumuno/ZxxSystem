package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//雇佣人员
@Data
@AllArgsConstructor
@ToString
public class hireUser {
    private String hire_user_id;// 雇佣人员id
    private String hire_user_name;// 雇佣人员姓名
    private String hire_user_password;// 雇佣人员密码
    private String hire_user_identity;// 雇拥人员身份
    private String hire_user_phone;// 雇佣人员手机号码
    private String hire_user_identitycard;// 雇佣人员身份证
    private String hire_user_image;// 雇佣人员头像
    private double hire_user_revenue;// 雇佣人员收入
    private double hire_user_proportion;// 雇佣人员收益比例
    private Timestamp hire_user_createTime;// 雇佣人员创建时间
    private int hire_user_tradednumber;// 雇佣人员完成的订单数
    private int hire_user_is_deleted ;//注销标识字段(0-正常 1-已锁定)

    public hireUser() {
    }
}
