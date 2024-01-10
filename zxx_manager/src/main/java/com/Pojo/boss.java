package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

//客户
@Data
@AllArgsConstructor
@ToString
public class boss {

    private String boss_id;// 用户id
    private String boss_name;// 用户姓名
    private String boss_password;// 用户密码
    private String boss_image;// 用户头像
    private String boss_phone;// 用户手机号码
    private String boss_email;// 邮箱
    private Timestamp boss_createTime;// 用户创建时间

    public boss() {
    }
}
