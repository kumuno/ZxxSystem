package com.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class Boss_Controller {

    //Boss退出登录
    @RequestMapping("/signIn")
    public String signIn(HttpServletRequest request){
        request.getSession().removeAttribute("boss_id");
        request.getSession().removeAttribute("boss_password");
        return "/Login";
    }

}
