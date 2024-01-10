package com.Controller;

import com.Pojo.commodityAttribute;
import com.Pojo.commoditySonAttribute;
import com.service.commodityAttributeService;
import com.service.commoditySonAttributeService;
import com.service.impl.commoditySonAttributeServiceImpl;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commoditySonAttributeController")
public class commoditySonAttributeController {
    @Autowired(required=false)
    private commoditySonAttributeService commoditySonAttributeService;

    @RequestMapping("/findAllCommoditySonAttributeByid/{id}")
    @ResponseBody
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(@PathVariable String id) throws IOException {
        List<commoditySonAttribute> list = new ArrayList<>();
        System.out.println(id);
        list = commoditySonAttributeService.findAllCommoditySonAttributeByid(id);
        System.out.println(list.toString());
        return list;
    }

    //    更新子商品属性信息
    @ResponseBody
    @RequestMapping("/updateCommoditySonAttribute")
    public ResponseJSONResult updateCommoditySonAttribute(@RequestBody(required=false) commoditySonAttribute commoditySonAttribute){
        System.out.println("========================Controller层，更新子商品属性信息=================");
       return commoditySonAttributeService.updateCommoditySonAttribute(commoditySonAttribute);
    }

    //    寻找当前商品子属性的信息
    @ResponseBody
    @RequestMapping("/queryCommoditySonAttribute")
    public commoditySonAttribute queryCommoditySonAttribute(String sonAttribute_id){
        System.out.println("========================Controller层，寻找当前商品属性的信息=================");
        return  commoditySonAttributeService.queryCommoditySonAttribute(sonAttribute_id);
    }

    //    寻找当前商品子属性的信息
    @ResponseBody
    @RequestMapping("/querySonAttributeAndAttribute")
    public List<commoditySonAttribute> querySonAttributeAndAttribute(String sonAttribute_id){
        System.out.println("========================Controller层，寻找当前商品属性的信息=================");
        return  commoditySonAttributeService.querySonAttributeAndAttribute(sonAttribute_id);
    }

    //添加商品子属性
    @ResponseBody
    @RequestMapping("/addCommoditySonAttribute")
    public void addCommoditySonAttribute(@RequestBody(required=false) commoditySonAttribute commoditySonAttribute){
        System.out.println("========================Controller层，添加商品属性=======================");
        System.out.println(commoditySonAttribute);
        commoditySonAttributeService.addCommoditySonAttribute2(commoditySonAttribute);
    }

    //添加商品子属性
    @ResponseBody
    @RequestMapping("/addCommoditySonAttribute2")
    public void addCommoditySonAttribute2(@RequestParam(value="newcommoditySonAttribute", required=false) String newcommoditySonAttribute){
        System.out.println("========================Controller层，添加商品属性=======================");
        System.out.println(newcommoditySonAttribute);
        commoditySonAttributeService.addCommoditySonAttribute(newcommoditySonAttribute);
    }

    //删除对应得商品属性
    @ResponseBody
    @RequestMapping("/deleteCommoditySonAttribute")
    public ResponseJSONResult deleteCommoditySonAttribute(String sonAttribute_id ,int returnOrDelete){
        System.out.println("========================Controller层，商品删除对应属性=================");
      return  commoditySonAttributeService.deleteCommoditySonAttribute(sonAttribute_id,returnOrDelete);
    }
}
