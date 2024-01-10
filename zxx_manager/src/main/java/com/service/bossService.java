package com.service;

import com.Pojo.boss;

import com.util.ResponseJSONResult;
import org.apache.ibatis.annotations.Param;
import javax.servlet.http.HttpSession;


public interface bossService {

    //给前端输入的邮箱发送验证码
    ResponseJSONResult sendMimeMail(String email);

    //随机生成6位熟的验证码
    String randomCode();

    //验证验证码进行注册
    ResponseJSONResult registered(String email,String code,String phone,String password);

    //通过输入email查询password，比较密码，如果一样，进行登录
    ResponseJSONResult loginIn(String email,String password,HttpSession session);

    //个显示人信息boss
    boss queryById(String boss_id);



}
