package com.Controller;

import com.Pojo.InternalUser;
import com.Pojo.boss;
import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.service.hireUserService;
import com.service.impl.hireUserServiceImpl;
import com.util.FileUp;
import com.util.PagerModel;
import com.util.ResponseJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/hireUserController")
public class hireUserController {
    @Autowired(required=false)
    private hireUserService hireUserService;

    //端口号
    @Value("${server.port}")
    private String port;

    //员工信息查询
    @ResponseBody
    @RequestMapping("/queryAllHireUser")
    public List<hireUser> queryAllHireUser(){
        System.out.println("========================Controller层，查找所有成员=================");
        return hireUserService.queryAllHireUser();
    }

    //    多条件查询
    @ResponseBody
    @RequestMapping("/conditionalQueriesHireUser")
    public List<hireUser> conditionalQueriesHireUser(@RequestParam(value = "keyword1",required = false) String keyword1,
                                                 @RequestParam(value = "keyword2",required = false) String keyword2){
        System.out.println("========================Controller层，多条件查询=================");
        System.out.println(keyword1);
        System.out.println(keyword2);
        return  hireUserService.conditionalQueriesHireUser(keyword1,keyword2);
    }

    //员工搜索
    @RequestMapping("/SearchHireUser")
    @ResponseBody
    public PageInfo<hireUser> SearchHireUser(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                                      @RequestParam(value = "hire_user_id") String hire_user_id,
                                                      @RequestParam(value = "hire_user_name") String hire_user_name) throws IOException {
        PageInfo<hireUser> hireUser = hireUserService.SearchHireUser(pageNum,hire_user_id,hire_user_name);
        return hireUser;
    }

    //    删除对应员工(修改员工状态)
    @ResponseBody
    @RequestMapping("/deleteHireUserById")
    public void deleteHireUserById(@RequestParam(value = "hire_user_id",required = false) String hireUser_id){
        System.out.println("========================Controller层，删除对应员工=======================");
        System.out.println(hireUser_id);
        hireUserService.deleteHireUserById(hireUser_id);
    }

    // 管理者添加员工
    //单图片上传
    @ResponseBody
    @PostMapping(value = "/addHireUserById")
    public ResponseJSONResult addHireUserById(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "hireImgfile") String hireImgfile, @RequestPart("hireUser") hireUser hireUser) throws UnsupportedEncodingException, UnknownHostException {
        //获得存储到static的路径
//        String path = this.getClass().getClassLoader().getResource("").getPath();
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString()+"/photo/";

        ResponseJSONResult responseJSONResult= FileUp.PhotoUpload(file, hireImgfile, path ,port);
        String url= (String)  responseJSONResult.getData();
        hireUser.setHire_user_image(url);
        System.out.println(url);
        System.out.println(hireUser);
        hireUserService.addHireUserById(hireUser);
        return  responseJSONResult;
    }

//    @ResponseBody
//    @RequestMapping("/addHireUserById")
//    public void addHireUserById(@RequestBody(required=false) hireUser hireUser){
//        System.out.println("========================Controller层，添加员工=======================");
//        System.out.println(hireUser);
//        hireUserService.addHireUserById(hireUser);
//    }

    // 管理者改变员工状态
    @ResponseBody
    @RequestMapping("/modifyHireUserStatus")
    public void modifyHireUserStatus(@RequestParam(value = "hireUser_id",required = false) String hireUser_id,
                                         @RequestParam(value = "hireUser_is_deleted",required = false) String hireUser_is_deleted){
        System.out.println("========================Controller层，改变员工状态=======================");
        System.out.println(hireUser_id);
        System.out.println(hireUser_is_deleted);
        hireUserService.modifyHireUserStatus(hireUser_id,hireUser_is_deleted);
    }

    //管理员查看所有员工
    @RequestMapping("/findAllHireUser")
    @ResponseBody
    public PageInfo<hireUser> findAllHireUser(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) throws IOException {
        System.out.println("分页");
        PageInfo<hireUser> allHireUser = hireUserService.findAllHireUser(pageNum);
        System.out.println(allHireUser);
        return allHireUser;
    }

    //    更新员工信息
    @ResponseBody
    @RequestMapping("/updateHireUser")
    public void updateHireUser(@RequestBody(required=false) hireUser hireUser){
        System.out.println("========================Controller层，用户更新信息=================");
        hireUserService.updateHireUser(hireUser);
    }

    //    查找员工信息
    @ResponseBody
    @RequestMapping("/queryHireUserById")
    public hireUser queryHireUserById(String hire_user_id){
        System.out.println("========================Controller层，查找用户=================");
        hireUser hireUser = hireUserService.queryHireUserById(hire_user_id);
        return hireUser;
    }

}
