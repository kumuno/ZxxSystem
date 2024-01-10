package com.Controller;

import com.Pojo.*;

import com.github.pagehelper.PageInfo;
import com.service.InternalUserService;
import com.util.FileUp;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

@Controller
@RequestMapping("/internalUserController")
public class internalUserController {
    @Autowired(required=false)
    private InternalUserService internalUserService;

    //端口号
    @Value("${server.port}")
    private String port;

    @ResponseBody
    @RequestMapping("/queryAllInternalUser")
    public List<InternalUser> queryAllInternalUser(){
        System.out.println("========================Controller层，查找所有成员=================");
        return internalUserService.queryAllInternalUser();
    }

    //    多条件查询
    @ResponseBody
    @RequestMapping("/conditionalQueries")
    public List<InternalUser> conditionalQueries(@RequestParam(value = "keyword1",required = false) String keyword1,
                                                 @RequestParam(value = "keyword2",required = false) String keyword2){
        System.out.println("========================Controller层，多条件查询=================");
        System.out.println(keyword1);
        System.out.println(keyword2);
        return  internalUserService.conditionalQueries(keyword1,keyword2);
    }

    //分页
    @RequestMapping("/findAllInternalUser")
    @ResponseBody
    public PageInfo<InternalUser> findAllInternalUser(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) throws IOException {
        System.out.println("分页");
        PageInfo<InternalUser> InternalUser = internalUserService.findAllInternalUser(pageNum);
        System.out.println(InternalUser);
        return InternalUser;
    }

    //管理员搜索订单
    @RequestMapping("/SearchInternalUser")
    @ResponseBody
    public PageInfo<InternalUser> SearchInternalUser(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                                      @RequestParam(value = "InternalUser_id") String InternalUser_id,
                                                      @RequestParam(value = "InternalUser_name") String InternalUser_name) throws IOException {
        PageInfo<InternalUser> InternalUser = internalUserService.SearchInternalUser(pageNum,InternalUser_id,InternalUser_name);
        return InternalUser;
    }

    //    删除对应内部成员(修改内部成员状态)
    @ResponseBody
    @RequestMapping("/deleteInternalUser")
    public void deleteInternalUser(@RequestParam(value = "InternalUser_id",required = false) String InternalUser_id){
        System.out.println("========================Controller层，删除对应员工=======================");
        System.out.println(InternalUser_id);
        internalUserService.deleteInternalUser(InternalUser_id);
    }

    // 添加内部成员
    //单图片上传
    @ResponseBody
    @PostMapping(value = "/addInternalUser")
    public ResponseJSONResult addInternalUser(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "InterImgfile") String InterImgfile, @RequestPart("internalUser") InternalUser internalUser) throws UnsupportedEncodingException, UnknownHostException {
        //获得存储到static的路径
//        String path = this.getClass().getClassLoader().getResource("").getPath();
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString()+"/photo/";
        ResponseJSONResult responseJSONResult= FileUp.PhotoUpload(file, InterImgfile, path ,port);
        String url= (String)  responseJSONResult.getData();
        internalUser.setInternalUser_image(url);
        System.out.println(url);
        System.out.println(internalUser);
        internalUserService.addInternalUser(internalUser);
        return  responseJSONResult;
    }

//    @ResponseBody
//    @RequestMapping("/addInternalUser")
//    public void addInternalUser(@RequestBody(required=false) InternalUser internalUser){
//        System.out.println("========================Controller层，添加内部成员=======================");
//        System.out.println(internalUser);
//        internalUserService.addInternalUser(internalUser);
//    }


    //    更新内部成员信息
    @ResponseBody
    @RequestMapping("/updateInternalUser")
    public void updateInternalUser(@RequestBody(required=false) InternalUser internalUser){
        System.out.println("========================Controller层，用户更新信息=================");
        internalUserService.updateInternalUser(internalUser);
    }

    //    查找内部成员信息
    @ResponseBody
    @RequestMapping("/queryInternalUser")
    public InternalUser queryInternalUser(String InternalUser_id){
        System.out.println("========================Controller层，查找用户=================");
        InternalUser internalUser = internalUserService.queryInternalUser(InternalUser_id);
        return internalUser;
    }

}
