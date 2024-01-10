package com.Controller;

import com.Pojo.user;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.service.impl.userServiceImpl;
import com.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/UserController")
public class UserController {


    @Autowired
    userServiceImpl userService = new userServiceImpl();

    @ResponseBody
    @RequestMapping("/WXphone")
    public Map<String,Object> doGetPhone(@RequestParam(value = "code",required = false) String code,
                                         @RequestParam(value = "openId",required = false) String openId
    ){

        Map<String,Object> map = new HashMap<String, Object>(  );
        //判断数据库是否存在该用户
        user user = userService.selectByUserId(openId);
        if(user==null){
            //手机号码
            System.out.println("phone-code:"+code);
            JSONObject token = getToken();
            System.out.println("token:"+token);
            String phone = getPhone(token,code);
            System.out.println("phone:"+phone);
            //时间
            Date date = new Date();//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date);//时间存储为字符串
            //
            user user1 = new user();
            user1.setUser_id(openId);//将openid作为用户id
            user1.setUser_name(null);
            user1.setUser_image(null);
            user1.setUser_password("0");
            user1.setUser_phone(phone);//用户手机号即登录账号
            user1.setUser_createTime(Timestamp.valueOf(str));
            userService.userEnroll( user1 );
            map.put("userInfo",user1);
            System.out.println(map);
        }else {
            map.put("userInfo",user);
            log.info( "用户openid已存在,不需要插入" );
        }
        return map;

    }

    //保存用户修改的个人信息（头像、名称、手机号码）
    @ResponseBody
    @RequestMapping("/reserveUser")
    public Map<String,Object> doGetPhone(@RequestParam(value = "tx",required = false) String tx,
                          @RequestParam(value = "name",required = false) String name,
                          @RequestParam(value = "mobile",required = false) String mobile,
                          @RequestParam(value = "openid",required = false) String openid

    ){
            Map<String,Object> map = new HashMap<String, Object>(  );
            user user = new user();
            user.setUser_id(openid);
            user.setUser_name(name);
            user.setUser_image(tx);
            user.setUser_phone(mobile);
            int k=userService.updateByUserIdSelective(user);
            if(k==1){
                user user1 = userService.selectByUserId(openid);
                map.put("userInfo",user1);
            }else{
                map.put("userInfo",null);
            }
            return map;
    }


    public static JSONObject getToken(){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,String> requestUrlParam = new HashMap<String, String>(  );
        requestUrlParam.put( "appid","wxd8747581989400b2" );//小程序appId
        requestUrlParam.put( "secret","462826b205d28d7450d66a9d7f085be8" );
        requestUrlParam.put( "grant_type","client_credential" );//默认参数
        //发送post请求读取调用微信接口获取token
        JSONObject token = JSON.parseObject(HttpClientUtil.doPost( requestUrl,requestUrlParam ));
        return token;
    }

    public static String getPhone(JSONObject token, String code){
        String url1 = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
        String url2 = token.getString("access_token");
        String url3 = url1+url2;
        Map<String, Object> params = new HashMap<>();
        params.put("code",code);

        JSONObject json = new JSONObject(params);
        String respPhone = HttpClientUtil.doPost2(url3,json);
        System.out.println("respPhone:"+respPhone);

        JSONObject jsonObject =  JSON.parseObject(respPhone);
        String obj1 = jsonObject.getString("phone_info");
        JSONObject jsonObject2 =  JSON.parseObject(obj1);
        String obj2 = jsonObject2.getString("phoneNumber");

        return obj2;
    }





}
