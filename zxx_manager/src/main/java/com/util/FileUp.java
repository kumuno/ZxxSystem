package com.util;



import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

public class FileUp {

    //单照片上传
    public static ResponseJSONResult PhotoUpload(MultipartFile file, String userid,String path,String port) throws UnsupportedEncodingException, UnknownHostException {
        if (file.isEmpty()) {
            return new ResponseJSONResult(211,"文件为空");
        }
        if (file.getSize()>=2621440){
            return new ResponseJSONResult(212,"图片过大，图片不能大于2.5M");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        try{
            boolean ifImg = VERIFY(suffixName);
            if (!ifImg){
                return null;
            }
        }catch (Exception e){
            return null;
        }
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String resource = path +userid+"/";
        //要是文件目录
        String filePath = URLDecoder.decode(resource, "UTF-8");
        System.out.println(filePath);
        String allName=filePath+fileName;
        //保存文件
//        File dest = new File(allName);
        boolean state = saveImage(file, allName);
        if(state) {
            //图片保存成功
            //设置图片到对象
            //保存对象，把url保存到对应的对象中
//            int row = informationService.addUrl(paInformation);
            int row =1;
            if(row>0) {
//                InetAddress local = InetAddress.getLocalHost();
//                String ipAddress = local.getHostAddress();
                ClientUtils clientUtils = new ClientUtils();
                String ipAddress;
                String ip = clientUtils.getLocalIpAddress();
                if (ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.16.") ||
                    ip.startsWith("172.17.") || ip.startsWith("172.18.") || ip.startsWith("172.19.") ||
                    ip.startsWith("172.20.") || ip.startsWith("172.21.") || ip.startsWith("172.22.") ||
                    ip.startsWith("172.23.") || ip.startsWith("172.24.") || ip.startsWith("172.25.") ||
                    ip.startsWith("172.26.") || ip.startsWith("172.27.") || ip.startsWith("172.28.") ||
                    ip.startsWith("172.29.") || ip.startsWith("172.30.") || ip.startsWith("172.31.") ||
                    ip.startsWith("169.254.")) {
                    ipAddress = clientUtils.getLocalIpAddress();
                } else {
                    ipAddress = clientUtils.getServerIpAddress();
                }
                System.out.println("ip地址："+ipAddress);
                String imageurl ="http://"+ipAddress+":"+port+"/photo/"+userid+"/"+fileName;
                System.out.println(imageurl);
                return new ResponseJSONResult(200,"上传成功",imageurl);

            }else {
                return new ResponseJSONResult(210,"上传失败");
            }
        }else {
            return new ResponseJSONResult(210,"上传失败");
        }
    }

    //多照片上传
    public static ResponseJSONResult PhotoUploadMore(MultipartFile[] files, String userid,String path,String port) throws UnsupportedEncodingException, UnknownHostException {
        if (files==null) {
            return new ResponseJSONResult(211, "文件为空");
        }
        for(MultipartFile mFile : files){
            if (mFile.getSize() >= 2621440) {
                return new ResponseJSONResult(212, "图片过大，图片不能大于2.5M");
            }
        }
        List<String> list=new ArrayList<>();
        for(MultipartFile mFile : files) {
            String fileName = mFile.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            try {
                boolean ifImg = VERIFY(suffixName);
                if (!ifImg) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            String resource = path  + userid + "/";
            //要是文件目录
            String filePath = URLDecoder.decode(resource, "UTF-8");
            System.out.println(filePath);
            String allName = filePath + fileName;
            //保存文件
//            File dest = new File(allName);
            boolean state = saveImage(mFile, allName);
            if (state) {
                //图片保存成功
                //设置图片到对象
                //保存对象，把url保存到对应的对象中
//            int row = informationService.addUrl(paInformation);
                int row = 1;
                if (row > 0) {
//                    InetAddress local = InetAddress.getLocalHost();
//                    String ipAddress = local.getHostAddress();
                    ClientUtils clientUtils = new ClientUtils();
                    String ipAddress;
                    String ip = clientUtils.getLocalIpAddress();
                    if (ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.16.") ||
                            ip.startsWith("172.17.") || ip.startsWith("172.18.") || ip.startsWith("172.19.") ||
                            ip.startsWith("172.20.") || ip.startsWith("172.21.") || ip.startsWith("172.22.") ||
                            ip.startsWith("172.23.") || ip.startsWith("172.24.") || ip.startsWith("172.25.") ||
                            ip.startsWith("172.26.") || ip.startsWith("172.27.") || ip.startsWith("172.28.") ||
                            ip.startsWith("172.29.") || ip.startsWith("172.30.") || ip.startsWith("172.31.") ||
                            ip.startsWith("169.254.")) {
                        ipAddress = clientUtils.getLocalIpAddress();
                    } else {
                        ipAddress = clientUtils.getServerIpAddress();
                    }
                    String imageurl = "http://" + ipAddress + ":" + port + "/photo/" + userid + "/" + fileName;
                    System.out.println(imageurl);
                    list.add(imageurl);

                } else {
                    return new ResponseJSONResult(210, "上传失败");
                }
            } else {
                return new ResponseJSONResult(210, "上传失败");
            }

        }
        return new ResponseJSONResult(200, "上传成功", list);
    }


    public static boolean VERIFY(String imgVerify){
        //可以随意添加图片类型 无需改代码
        String[] imgFormat = {".jpg",".png",".jpeg",".pjg",".pjeg",".jfif"};
        for(String img:imgFormat){
            if(img.equals(imgVerify)){
                return true;
            }
        }
        return false;
    }

    public static boolean saveImage(MultipartFile mFile, String dstPath) {
        File file = new File(dstPath);
        //查看文件夹是否存在，不存在则创建
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            //使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
            FileUtils.copyInputStreamToFile(mFile.getInputStream(),file);
            return true;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*删除项目中没用的文件*/
    public static boolean deleteFile(String path,String fileName) throws UnsupportedEncodingException {
        //http://192.168.142.1:8081/photo/InterImgfile/d7a2dd0d-6cf7-4fc6-821f-a8d1b0cbdff6.png
//        path = path+ "static/photo/";
        String substring = fileName.substring(0, fileName.length());
        String[] split = substring.split("/");//以逗号分割
        path = path+split[4]+"/"+split[5]+"/"+split[6];
        String filePath = URLDecoder.decode(path, "UTF-8");
        File file = new File(filePath);
        //判断文件存不存在
        if(!file.exists()){
            System.out.println("删除文件失败："+fileName+"不存在！");
            return false;
        }else{
            //判断这是不是一个文件，ps：有可能是文件夹
            if(file.isFile()){
                return file.delete();
            }
        }
        return false;
    }


}
