package com.Controller;

import com.Pojo.boss;
import com.Pojo.commodity;
import com.Pojo.commodityAttribute;
import com.Pojo.commoditySonAttribute;
import com.service.bossService;
import com.service.commodityAttributeService;
import com.service.commoditySonAttributeService;
import com.util.ResponseJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/commodityAttributeController")
public class commodityAttributeController {

    @Autowired(required=false)
    private commodityAttributeService commodityAttributeService;

    @Autowired(required=false)
    private commoditySonAttributeService commoditySonAttributeService;

    //    更新商品属性信息
    @ResponseBody
    @RequestMapping("/updateCommodityAttribute")
    public void updateCommodityAttribute(@RequestBody(required=false) commodityAttribute commodityAttribute,
                           HttpSession session, HttpServletRequest request){

        commodityAttributeService.updateCommodityAttribute(commodityAttribute);
        System.out.println("========================Controller层，用户更新信息=================");
    }

    //    寻找当前商品属性的信息
    @ResponseBody
    @RequestMapping("/queryCommodityAttribute")
    public List<commodityAttribute> queryCommodityAttribute(String Attribute_id){
        System.out.println("========================Controller层，寻找当前商品属性的信息=================");
        return  commodityAttributeService.queryCommodityAttribute(Attribute_id);
    }

    //添加商品属性
    @ResponseBody
    @RequestMapping("/addCommodityAttribute")
    public String addCommodityAttribute(@RequestParam(value="newcommodityAttribute", required=false) String newcommodityAttribute){
        System.out.println("========================Controller层，添加商品属性=======================");
        System.out.println(newcommodityAttribute);
        return commodityAttributeService.addCommodityAttribute(newcommodityAttribute);
    }

    //删除对应得商品属性
    @ResponseBody
    @RequestMapping("/deleteCommodityAttribute")
    public ResponseJSONResult deleteCommodityAttribute(@RequestParam(value="attribute_id", required=false)String Attribute_id,
                                                       @RequestParam(value="returnOrDelete", required=false)int returnOrDelete){
        System.out.println("========================Controller层，商品删除对应属性=================");
        return commodityAttributeService.deleteCommodityAttribute(Attribute_id,returnOrDelete);
    }


    //    通过商品属性ID模糊查询
    @ResponseBody
    @RequestMapping("/queryAttributeAndSonAttribute")
    public Map<String,Object> queryAttributeAndSonAttribute(@RequestParam(value = "keyword1",required = false) String Attribute_id){
        System.out.println("========================Controller层，寻找当前商品属性的信息=================");
        Map<String,Object> map = new HashMap<String, Object>();
        List<commodityAttribute> commodityAttribute = commodityAttributeService.queryCommodityAttribute(Attribute_id);
        List<commoditySonAttribute> commoditySonAttribute = commoditySonAttributeService.querySonAttributeAndAttribute(Attribute_id);
        System.out.println(commodityAttribute);
        System.out.println(commoditySonAttribute);
        map.put("commodityAttribute",commodityAttribute);
        map.put("commoditySonAttribute",commoditySonAttribute);
        return map;
    }
}
