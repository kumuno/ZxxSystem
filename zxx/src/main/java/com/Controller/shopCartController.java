package com.Controller;


import com.Pojo.commodity;
import com.Pojo.commoditySonAttribute;
import com.Pojo.shopCart;
import com.Pojo.user;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.schema.Model;

import javax.annotation.Resource;
import javax.crypto.spec.PSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.sum;

@Slf4j
@ResponseBody
@Controller
@RequestMapping("/shopCartController")
public class shopCartController {
    @Resource
    shopCartServiceImpl shopCartService;

    @Resource
    userServiceImpl userService = new userServiceImpl();

    @Resource
    commoditySonAttributeServiceImpl commoditySonAttributeService = new commoditySonAttributeServiceImpl();

    @Resource
    commodityServiceImpl commodityService;

    @Resource
    commodityAttributeServiceImpl commodityAttributeService;

    //购物车添加商品
    @RequestMapping("/CartAdd")
    @ResponseBody
    public Map<String,Object> CartAdd(Model model,
                                      @RequestParam(value = "good_id",required = false) String good_id,
                                      @RequestParam(value = "good_index",required = false) String good_index,
                                      @RequestParam(value = "number",required = false) int number,
                                      @RequestParam(value = "openId",required = false) String openId
    )
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        System.out.println("id:"+openId);
        log.info( "Start CartAdd" );
        double goodPriceSum = 0.0;
        System.out.println(good_index);
        user user = userService.selectByUserId(openId);
        String user_id = user.getUser_id();
        String shopCartID = shopCartService.selectByShopCartID(good_index);
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);//时间存储为字符串
        if (shopCartID == null){
            System.out.println("111111111111111111111111111111111");
            String shopCharId = String.valueOf("car"+ (int) (Math.random() * 1000000000));
            //没有则需要新建购物车，后面再一次添加商品
            shopCart shopchar = new shopCart();
            shopchar.setShopcart_id(shopCharId);
            shopchar.setCommodity_id(good_id);
            shopchar.setUser_id(user_id);
            shopchar.setShopcart_amount(number);
            shopchar.setShopcart_sign(0);
            shopchar.setSonAttribute_id(good_index);
            shopchar.setShopcart_createTime(Timestamp.valueOf(str));
            shopchar.setChecked(0);
            shopCartService.shopCartEnroll(shopchar);
            log.info( "购物车添加新数据成功" );
//            goodPriceSum =sum(goodPriceSum,mul(number,good_price));
            System.out.println("goodPriceSum:"+goodPriceSum);
            map.put("number",number);
        }else{
            shopCart shopchar = shopCartService.selectByshopCartID(shopCartID);
            int number2 = sum(number,shopchar.getShopcart_amount());
            map.put("number",number2);
//            goodPriceSum =sum(goodPriceSum,mul(number2,good_price));
//            System.out.println("goodPriceSum:"+goodPriceSum);
            System.out.println(number2);
            shopchar.setShopcart_amount(number2);
            shopCartService.updateBySonAttributeId(number2,good_index);
            log.info("==============购物车更新商品信息================");
        }

        //返回商品总数量
        int cartGoodsCount = shopCartService.sum_shopcart_amount(user_id);
        System.out.println("cartGoodsCount："+cartGoodsCount);
        map.put("cartGoodsCount",cartGoodsCount);
        return map;
    }

    @RequestMapping("/CartChecked")
    @ResponseBody
    public Map<String,Object> CartChecked(Model model,
                                            @RequestParam(value = "openId",required = false) String openId,
                                            @RequestParam(value = "shopcart_id",required = false) String shopcart_id,
                                            @RequestParam(value = "checked",required = false) int checked,
                                            @RequestParam(value = "good_index",required = false) String good_index
                                      )
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        System.out.println("openId:"+openId);
        System.out.println("shopcart_id:"+shopcart_id);
        System.out.println("checked："+checked);
        System.out.println("good_index:"+good_index);
        if (checked == 1){
            checked =0;
        }else{
            checked =1;
        }
        System.out.println(checked);
        shopCart shopchar = shopCartService.selectByshopCartID(shopcart_id);
        shopchar.setChecked(checked);
        shopCartService.updateChecked(checked,good_index);
        log.info("==============购物车更新checked================");
        //查询所有商品的
        List<shopCart> allAttribute = shopCartService.allAttribute(openId);
        map.put("cartList",allAttribute);
        //查询checked=1的商品的
        List<shopCart> allCheckedAttribute = shopCartService.selectCheckedByshopCartID(openId);
        System.out.println("allCheckedAttribute:"+allCheckedAttribute);
        int goodsCount = 0; //当前产品总数量
        double goodsAmount = 0.00; //当前产品总数量
        int checkedGoodsCount = 0;
        double checkedGoodsAmount = 0;
        for (int i = 0; i < allAttribute.size(); i++) {
            goodsCount = goodsCount+allAttribute.get(i).getShopcart_amount();
            goodsAmount =goodsAmount + allAttribute.get(i).getShopcart_amount()*allAttribute.get(i).getCommoditySonAttribute().getCommodity_price();
        }
        for (int j = 0; j < allCheckedAttribute.size(); j++) {
            checkedGoodsCount = checkedGoodsCount+allCheckedAttribute.get(j).getShopcart_amount();
            checkedGoodsAmount =checkedGoodsAmount + allCheckedAttribute.get(j).getShopcart_amount()*allCheckedAttribute.get(j).getCommoditySonAttribute().getCommodity_price();
        }
        Map<String,Object> cartTotal = new HashMap<String, Object>(  );
        cartTotal.put("goodsCount",goodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        map.put("cartTotal",cartTotal);
        return map;
    }

    @RequestMapping("/CartUpdate")
    @ResponseBody
    public Map<String,Object> CartUpdate(Model model,
                                          @RequestParam(value = "openId",required = false) String openId,
                                          @RequestParam(value = "shopcart_id",required = false) String shopcart_id,
                                          @RequestParam(value = "shopcart_amount",required = false) int shopcart_amount
    ){
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        System.out.println("openId:"+openId);
        System.out.println("shopcart_id:"+shopcart_id);
        System.out.println("shopcart_amount:"+shopcart_amount);
        shopCart shopchar = shopCartService.selectByshopCartID(shopcart_id);
        shopchar.setShopcart_amount(shopcart_amount);
        shopCartService.updateShopCartAmount(shopcart_amount,shopcart_id);
        log.info("==============购物车更新Amount================");
        List<shopCart> allAttribute = shopCartService.allAttribute(openId);
        map.put("cartList",allAttribute);
        //查询checked=1的商品的
        List<shopCart> allCheckedAttribute = shopCartService.selectCheckedByshopCartID(openId);
        int goodsCount = 0; //当前产品总数量
        double goodsAmount = 0.00; //当前产品总数量
        int checkedGoodsCount = 0;
        double checkedGoodsAmount = 0;
        for (int i = 0; i < allAttribute.size(); i++) {
            goodsCount = goodsCount+allAttribute.get(i).getShopcart_amount();
            goodsAmount =goodsAmount + allAttribute.get(i).getShopcart_amount()*allAttribute.get(i).getCommoditySonAttribute().getCommodity_price();
        }
        for (int j = 0; j < allCheckedAttribute.size(); j++) {
            checkedGoodsCount = checkedGoodsCount+allCheckedAttribute.get(j).getShopcart_amount();
            checkedGoodsAmount =checkedGoodsAmount + allCheckedAttribute.get(j).getShopcart_amount()*allCheckedAttribute.get(j).getCommoditySonAttribute().getCommodity_price();
        }
        Map<String,Object> cartTotal = new HashMap<String, Object>(  );
        cartTotal.put("goodsCount",goodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        map.put("cartTotal",cartTotal);
        return map;
    }

    @RequestMapping("/CartDelete")
    @ResponseBody
    public Map<String,Object> CartDelete(Model model,
                                         @RequestParam(value = "openId",required = false) String openId,
                                         @RequestParam(value = "shopcart_id",required = false) String shopcart_id
    ){
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        shopCartService.deleteShopCart(shopcart_id);
        log.info("==============删除购物车================");
        List<shopCart> allAttribute = shopCartService.allAttribute(openId);
        map.put("cartList",allAttribute);
        //查询checked=1的商品的
        List<shopCart> allCheckedAttribute = shopCartService.selectCheckedByshopCartID(openId);
        int goodsCount = 0; //当前产品总数量
        double goodsAmount = 0.00; //当前产品总数量
        int checkedGoodsCount = 0;
        double checkedGoodsAmount = 0;
        for (int i = 0; i < allAttribute.size(); i++) {
            goodsCount = goodsCount+allAttribute.get(i).getShopcart_amount();
            goodsAmount =goodsAmount + allAttribute.get(i).getShopcart_amount()*allAttribute.get(i).getCommoditySonAttribute().getCommodity_price();
        }
        for (int j = 0; j < allCheckedAttribute.size(); j++) {
            checkedGoodsCount = checkedGoodsCount+allCheckedAttribute.get(j).getShopcart_amount();
            checkedGoodsAmount =checkedGoodsAmount + allCheckedAttribute.get(j).getShopcart_amount()*allCheckedAttribute.get(j).getCommoditySonAttribute().getCommodity_price();
        }
        Map<String,Object> cartTotal = new HashMap<String, Object>(  );
        cartTotal.put("goodsCount",goodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        map.put("cartTotal",cartTotal);
        return map;
    }
    @RequestMapping("/CartCheckedAll")
    @ResponseBody
    public Map<String,Object> CartCheckedAll(Model model,
                                             @RequestParam(value = "openId",required = false) String openId,
                                             @RequestParam(value = "isChecked",required = false) int isChecked)
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        System.out.println("openId:"+openId);
        System.out.println("isChecked:"+isChecked);
        if (isChecked==0){
            shopCartService.CartCheckedAll();
        }else{
            shopCartService.CancelCartCheckedAll();
        }
        List<shopCart> allAttribute = shopCartService.allAttribute(openId);
        map.put("cartList",allAttribute);
        //查询checked=1的商品的
        List<shopCart> allCheckedAttribute = shopCartService.selectCheckedByshopCartID(openId);
        int goodsCount = 0; //当前产品总数量
        double goodsAmount = 0.00; //当前产品总数量
        int checkedGoodsCount = 0;
        double checkedGoodsAmount = 0;
        for (int i = 0; i < allAttribute.size(); i++) {
            goodsCount = goodsCount+allAttribute.get(i).getShopcart_amount();
            goodsAmount =goodsAmount + allAttribute.get(i).getShopcart_amount()*allAttribute.get(i).getCommoditySonAttribute().getCommodity_price();
        }
        for (int j = 0; j < allCheckedAttribute.size(); j++) {
            checkedGoodsCount = checkedGoodsCount+allCheckedAttribute.get(j).getShopcart_amount();
            checkedGoodsAmount =checkedGoodsAmount + allCheckedAttribute.get(j).getShopcart_amount()*allCheckedAttribute.get(j).getCommoditySonAttribute().getCommodity_price();
        }
        Map<String,Object> cartTotal = new HashMap<String, Object>(  );
        cartTotal.put("goodsCount",goodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        map.put("cartTotal",cartTotal);
        return map;
    }

    @RequestMapping("/CartGoods")
    @ResponseBody
    public Map<String,Object> CartGoods(Model model,
                                        @RequestParam(value = "openId",required = false) String openId)
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        System.out.println("openId:"+openId);
        List<shopCart> allAttribute = shopCartService.allAttribute(openId);
        map.put("cartList",allAttribute);
        //查询checked=1的商品的
        List<shopCart> allCheckedAttribute = shopCartService.selectCheckedByshopCartID(openId);
        int goodsCount = 0; //当前产品总数量
        double goodsAmount = 0.00; //当前产品总数量
        int checkedGoodsCount = 0;
        double checkedGoodsAmount = 0;
        for (int i = 0; i < allAttribute.size(); i++) {
            goodsCount = goodsCount+allAttribute.get(i).getShopcart_amount();
            goodsAmount =goodsAmount + allAttribute.get(i).getShopcart_amount()*allAttribute.get(i).getCommoditySonAttribute().getCommodity_price();
        }
        for (int j = 0; j < allCheckedAttribute.size(); j++) {
            checkedGoodsCount = checkedGoodsCount+allCheckedAttribute.get(j).getShopcart_amount();
            checkedGoodsAmount =checkedGoodsAmount + allCheckedAttribute.get(j).getShopcart_amount()*allCheckedAttribute.get(j).getCommoditySonAttribute().getCommodity_price();
        }
        Map<String,Object> cartTotal = new HashMap<String, Object>(  );
        cartTotal.put("goodsCount",goodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        map.put("cartTotal",cartTotal);
        return map;
    }

    
    @RequestMapping("/CartCheckout")
    @ResponseBody
    public Map<String,Object> CartCheckout(Model model,
                                        @RequestParam(value = "openId",required = false) String openId)
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        int errno = 0;
        map.put("errno",errno);
        return map;
    }

    @RequestMapping("/cartGoodsCount")
    @ResponseBody
    public Map<String,Object> cartGoodsCount(Model model,
                                           @RequestParam(value = "openId",required = false) String openId)
    {
        Map<String,Object> map = new HashMap<String, Object>(  );
        if (!openId.equals("")){
            int errno = 0;
            map.put("errno",errno);
            map.put("cartGoodsCount",shopCartService.sum_shopcart_amount(openId));
        }else {
            int errno = 1;
            map.put("errno",errno);
            map.put("errMsg","查询购物车数据失败");
        }
        return map;
    }


}
