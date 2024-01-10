package com.Controller;

import com.Pojo.user;
import com.service.impl.userServiceImpl;
import com.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ResourcesController {


    @Autowired
    userServiceImpl userService = new userServiceImpl();

    @ResponseBody
    @RequestMapping("/addImage")
    public Map<String,Object> addImage(@RequestParam("image_file") MultipartFile file,
                                       String openid, HttpServletRequest request) throws IOException {
        //获取上传的文件流
        InputStream inputStream = file.getInputStream();
        //获取上传的文件名
        String fileName = file.getOriginalFilename();
        //截取后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //使用UUID拼接后缀，定义一个不重复的文件名
        String finalName = openid+suffix;
        System.out.println("finalName"+finalName);

        //使用自定义的FTP工具类上传文件

        String ImageUrl = FtpUtil.uploadFile(finalName,inputStream,openid,request);
        Map<String,Object> map = new HashMap<String, Object>(  );
        if (ImageUrl != "false"){
            user user = new user();
            user.setUser_id(openid);
            user.setUser_image(ImageUrl);
            int k=userService.updateByUserIdSelective(user);
            if (k==1){
                user user1 = userService.selectByUserId(openid);
                map.put("userInfo",user1);
                System.out.println(user1);
            }else {
                map.put("userInfo",null);
            }
        }else {
            map.put("userInfo",null);
        }
        return map;
    }

}
