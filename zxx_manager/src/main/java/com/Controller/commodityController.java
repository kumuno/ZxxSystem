package com.Controller;

import com.Pojo.boss;
import com.Pojo.commodity;
import com.Pojo.commodityAttribute;
import com.Pojo.hireUser;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.github.pagehelper.PageInfo;
import com.service.commodityService;
import com.service.commoditySonAttributeService;
import com.service.hireUserService;
import com.service.impl.commodityServiceImpl;
import com.util.FileUp;
import com.util.ResponseJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/managerCommodityController")
public class commodityController {

    @Autowired(required = false)
    private commodityService commodityService;

    //端口号
    @Value("${server.port}")
    private String port;

    //管理员查看所有商品
    @RequestMapping("/findAllCommodityByPage")
    @ResponseBody
    public PageInfo<commodity> findAllCommodityByPage(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) throws IOException {
        System.out.println("分页");
        PageInfo<commodity> allCommodity = commodityService.findAllCommodityByPage(pageNum);
        System.out.println(allCommodity);
        return allCommodity;
    }

    //    商品修改状态位
    @ResponseBody
    @RequestMapping("/deleteByCommodityId")
    public void deleteByCommodityId(String commodity_id) {
        System.out.println("========================Controller层，商品修改状态位=================");
        commodityService.deleteByCommodityId(commodity_id);
    }

    //    商品修改状态位(更新为下架状态)
    @ResponseBody
    @RequestMapping("/deleteByCommodityForever")
    public ResponseJSONResult deleteByCommodityForever(String commodity_id) {
        System.out.println("========================Controller层，商品修改状态位=================");
        return commodityService.deleteByCommodityForever(commodity_id);
    }

    //商品修改状态位(更新为上架状态)
    @ResponseBody
    @RequestMapping("/returnDeleteByCommodityForever")
    public ResponseJSONResult returnDeleteByCommodityForever(String commodity_id) {
        System.out.println("========================Controller层，商品修改状态位=================");
        return commodityService.returnDeleteByCommodityForever(commodity_id);
    }

    //通过商品id查询该商品所有信息
    @ResponseBody
    @RequestMapping("/queryByCommodityId")
    public commodity queryByCommodityId(String commodity_id) {
        System.out.println("========================Controller层，查找用户=================");
        return commodityService.queryByCommodityId(commodity_id);
    }

    //    更新商品信息
    @ResponseBody
    @RequestMapping("/updateCommodity")
    public ResponseJSONResult updateCommodity(@RequestBody(required = false) commodity commodity) {
        System.out.println("========================Controller层，更新商品信息=================");
        return commodityService.updateCommodity(commodity);
    }

//    //添加商品
//    @ResponseBody
//    @RequestMapping("/addCommodity")
//    public void addCommodity(@RequestBody(required = false) commodity commodity) {
//        System.out.println("========================Controller层，添加商品=======================");
//        System.out.println(commodity);
//        commodityService.addCommodity(commodity);
//    }

    //多图片上传
    @ResponseBody
    @PostMapping(value = "/addCommodity")
    public ResponseJSONResult addCommodity(@RequestPart(value = "files") MultipartFile[] files, @RequestParam(value = "commodityPhoto") String commodityPhoto, @RequestPart("commodity") commodity commodity) throws UnsupportedEncodingException, UnknownHostException {
        System.out.println("controller层添加商品");
//        String path = this.getClass().getClassLoader().getResource("").getPath();
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString()+"/photo/";

        System.out.println(commodity);
        String id = commodityService.addCommodity(commodity);
        System.out.println(id);
        if (!id.equals("false")) {
            ResponseJSONResult responseJSONResult = FileUp.PhotoUploadMore(files, commodityPhoto + "/" + id, path, port);
            List<String> url = (List<String>) responseJSONResult.getData();
            String newUrl = null;
            newUrl = url.get(0);
            commodity.setCommodity_img(newUrl);
            if (url.size() > 1) {
                for (int i = 1; i < url.size(); i++) {
                    newUrl = newUrl +"--" + url.get(i);
                    System.out.println("执行了"+i+"次");
                }
            }
            commodity.setCommodity_carousel_Img(newUrl);
            System.out.println("newUrl:"+newUrl);
            if (!commodityService.UpdateCommodity_url(commodity)){
                return new ResponseJSONResult(220, "更新数据库照片路径失败");
            }
            return responseJSONResult;
        } else {
            return new ResponseJSONResult(220, "插入商品信息失败");
        }

    }

    @ResponseBody
    @PostMapping(value = "/updateCommodity_url")
    public ResponseJSONResult updateCommodity_url(@RequestPart(value = "files") MultipartFile[] files,
                                                  @RequestParam(value = "commodityPhoto") String commodityPhoto,
                                                  @RequestPart("commodity") commodity commodity) throws UnsupportedEncodingException, UnknownHostException {
        System.out.println("controller层添加商品");
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        String path = null;
//        path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String Path_n= URLDecoder.decode(path, "UTF-8");
//        System.out.println("pathtttttttttttttttttttt："+Path_n);

//        String jarPath  = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println("啦啦啦啦啦啦啦啦："+jarPath );
//        String path = URLDecoder.decode(jarPath, "UTF-8");
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString()+"/photo/";

        // 对上传文件路径进行编码
//        String encodedPath = URLEncoder.encode(path, "UTF-8");
//        // 返回上传文件的路径，并对路径进行解码
//        String decode = URLDecoder.decode(encodedPath, "UTF-8");

        String oldurl=commodity.getCommodity_carousel_Img();
        if(!oldurl.equals("")){
            System.out.println(oldurl);
            String[] oldStr = oldurl.split("--");	// 分割成数
            for(int i=0;i<oldStr.length;i++){
                FileUp.deleteFile(path,oldStr[i]);
            }
        }
        System.out.println(commodity);
        ResponseJSONResult responseJSONResult = FileUp.PhotoUploadMore(files, commodityPhoto + "/" + commodity.getCommodity_id(), path, port);
        List<String> url = (List<String>) responseJSONResult.getData();
        String newUrl = null;
        newUrl = url.get(0);
        commodity.setCommodity_img(newUrl);
        if (url.size() > 1) {
            for (int i = 1; i < url.size(); i++) {
                newUrl = newUrl +"--" + url.get(i);
                System.out.println("执行了"+i+"次");
            }
        }
        commodity.setCommodity_carousel_Img(newUrl);
        System.out.println("newUrl:"+newUrl);
        if (!commodityService.UpdateCommodity_url(commodity)){
            return new ResponseJSONResult(220, "更新数据库照片路径失败");
        }
        return responseJSONResult;
    }

    //商品多条件查询
    @ResponseBody
    @RequestMapping("/conditionalQueriesCommodity")
    public PageInfo<commodity> conditionalQueriesCommodity(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                                           @RequestParam(value = "keyword1", required = false) String keyword1,
                                                           @RequestParam(value = "keyword2", required = false) String keyword2) {
        System.out.println("========================Controller层，多条件查询=================");
        System.out.println(keyword1);
        System.out.println(keyword2);
        return commodityService.conditionalQueriesCommodity(pageNum, keyword1, keyword2);
    }

}
