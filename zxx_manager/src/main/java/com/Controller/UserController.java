package com.Controller;


import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.Pojo.user;

import com.github.pagehelper.PageInfo;

import com.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/UserController")
public class UserController {


    @Autowired(required=false)
    private userService userService;


    //    多条件查询
    @ResponseBody
    @RequestMapping("/conditionalQueriesUser")
    public List<user> conditionalQueriesUser(@RequestParam(value = "keyword1",required = false) String keyword1,
                                                     @RequestParam(value = "keyword2",required = false) String keyword2){
        System.out.println("========================Controller层，多条件查询=================");
        System.out.println(keyword1);
        System.out.println(keyword2);
        return  userService.conditionalQueriesUser(keyword1,keyword2);
    }

    //分页
    @RequestMapping("/findAllUser")
    @ResponseBody
    public PageInfo<user> findAllUser(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) throws IOException {
        System.out.println("分页");
        PageInfo<user> allUser = userService.findAllUser(pageNum);
        System.out.println(allUser);
        return allUser;
    }

    //用户搜索管理
    @RequestMapping("/SearchUser")
    @ResponseBody
    public PageInfo<user> SearchUser(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                                      @RequestParam(value = "user_id") String user_id,
                                                      @RequestParam(value = "user_name") String user_name) throws IOException {
        PageInfo<user> user = userService.SearchUser(pageNum,user_id,user_name);
        return user;
    }
    //    删除用户(修改用户状态)
    @ResponseBody
    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestParam(value = "user_id",required = false) String user_id){
        System.out.println("========================Controller层，删除对应员工=======================");
        System.out.println(user_id);
        userService.deleteUser(user_id);
    }

    // 添加客户
    @ResponseBody
    @RequestMapping("/addUser")
    public void addUser(@RequestBody(required=false) user user){
        System.out.println("========================Controller层，添加内部成员=======================");
        System.out.println(user);
        userService.addUser(user);
    }

    //    更新个人信息
    @ResponseBody
    @RequestMapping("/updateUser")
    public void updateUser(@RequestBody(required=false) user user,
                           HttpSession session, HttpServletRequest request){
        System.out.println("========================Controller层，用户更新信息=================");
        userService.updateUser(user);
    }

    //    查找客户信息
    @ResponseBody
    @RequestMapping("/queryUserById")
    public user queryUserById(String user_id){
        System.out.println("========================Controller层，查找用户=================");
        user user = userService.queryUserById(user_id);
        return user;
    }

}
