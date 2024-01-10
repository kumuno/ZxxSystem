package com.Controller;



import com.util.FileUp;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;

@Controller
public class FileController {

    //端口号
    @Value("${server.port}")
    private String port;

    //单图片上传
    @ResponseBody
    @PostMapping(value = "/PhotoUpload")
    public ResponseJSONResult PhotoUpload(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "userid")String userid) throws UnsupportedEncodingException, UnknownHostException {
        //获得存储到static的路径
//        String path = this.getClass().getClassLoader().getResource("").getPath();
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString();
        return FileUp.PhotoUpload(file, userid, path ,port);
    }

    //多图片上传
    @ResponseBody
    @PostMapping(value = "/PhotoUploadMore")
    public ResponseJSONResult PhotoUploadMore(@RequestPart(value = "files") MultipartFile[] files, @RequestParam(value = "userid")String userid) throws UnsupportedEncodingException, UnknownHostException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        return FileUp.PhotoUploadMore(files, userid, path ,port);
    }

}
