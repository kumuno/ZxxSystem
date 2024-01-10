package com.Controller;

import com.Pojo.boss;
import com.service.bossService;
import com.util.ResponseJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/BossController")
public class BossController {

    @Autowired
    private bossService bossService;

    //发送验证码
    @PostMapping("/sendEmail")
    @ResponseBody
    public ResponseJSONResult sendEmail(String email){
        return bossService.sendMimeMail(email);
    }

    //进行注册
    @PostMapping("/register")
    @ResponseBody
    public ResponseJSONResult register(@RequestParam(value = "email") String email,@RequestParam(value = "code") String code,@RequestParam(value = "phone") String phone,@RequestParam(value = "password") String password){
        return bossService.registered(email,code,phone,password);
    }

    //邮箱注册若已经存在，不可再次注册

    //登录
    @PostMapping("/enroll")
    @ResponseBody
    public ResponseJSONResult enroll(@RequestParam(value = "email")String email, @RequestParam(value = "password")String password,HttpSession session){
        return bossService.loginIn(email, password, session);
    }

    //内部成员个人信息
    @RequestMapping("/queryById")
    @ResponseBody
    public boss queryById(HttpServletRequest request){
        HttpSession session = request.getSession();
        String boss_id = (String) session.getAttribute("boss_id");
        return bossService.queryById(boss_id);
    }







}
