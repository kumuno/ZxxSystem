package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//客户
@Data
@AllArgsConstructor
@ToString
public class user {

    private String user_id;// 用户id
    private String user_name;// 用户姓名
    private String user_password;// 用户密码
    private String user_image;// 用户头像
    private String user_phone;// 用户手机号码
    private Timestamp user_createTime;// 用户创建时间
    private int user_Integral;// 用户积分
    private int user_is_deleted; // 注销标识字段(0-正常 1-已注销)

    public user() {
    }
}
