package com.Controller;

import com.Pojo.commodity;
import com.Pojo.search;
import com.service.impl.commodityServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/commodityController")
public class commodityController {

    @Resource
    commodityServiceImpl commodityService;


    //按类型查询所有商品
    @RequestMapping("/findAllCommodityByType")
    @ResponseBody
    public Map<String, List<commodity>> findAllCommodityByType() throws IOException {
        Map<String, List<commodity>> map = new HashMap<>();
        map.put("水果",commodityService.findByTypeCommodity("水果"));
        map.put("蔬菜",commodityService.findByTypeCommodity("蔬菜"));
        map.put("肉",commodityService.findByTypeCommodity("肉"));
        map.put("海鲜",commodityService.findByTypeCommodity("海鲜"));
        map.put("研学活动",commodityService.findByTypeCommodity("研学活动"));
        System.out.println(map);
        return map;
    }

    //通过商品名字模糊搜索查询商品
    @RequestMapping("/findCommodityBySearch")
    @ResponseBody
    public List<commodity> findCommodityBySearch(String keyword,String sort,String order, String sales) throws IOException {
        System.out.println("keyword："+keyword);
        System.out.println("sort："+sort);
        System.out.println("order："+order);
        System.out.println("sales："+sales);
        commodity commodity = new commodity();
        commodity.setCommodity_name(keyword);
        List<commodity> list = null;
        if (sort.equals("default")){
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("commodity_price", order);
            map.put("commodity_sales", sales);
            commodity.setSortMap(map);
            list = commodityService.findCommodityBySearch(commodity);
        }else if (sort.equals("price")){
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("commodity_price", order);
            map.put("commodity_sales", sales);
            commodity.setSortMap(map);
            list = commodityService.findCommodityBySearch(commodity);
        }else if(sort.equals("sales")){
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("commodity_sales", sales);
            map.put("commodity_price", order);
            commodity.setSortMap(map);
            list = commodityService.findCommodityBySearch(commodity);
        }
        System.out.println("搜索结果："+list);
        return list;
    }

    //查找所有商品类型
    @RequestMapping("/findAllCommodityType")
    @ResponseBody
    public LinkedHashSet findAllCommodityType() throws IOException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<Map<String,String>> list =commodityService.findAllCommodityType();
        for (int i=0;i<list.size();i++){
            linkedHashSet.add(list.get(i));
        }
        System.out.println("所有商品类型："+linkedHashSet);
        return linkedHashSet;
    }

    //查询所有商品
    @RequestMapping("/findAllCommodity")
    @ResponseBody
    public List<commodity> findAllCommodity() throws IOException {
        return commodityService.findAllCommodity();
    }

    //添加搜索记录
    @RequestMapping("/insertSearch")
    @ResponseBody
    public void insertSearch(String keyword,String user_id) throws IOException {
        System.out.println("keyword："+keyword);
        System.out.println("user_id："+user_id);
        Random rand = new Random();
        String setSearch_id = String.valueOf(rand.nextInt(900000000)+ 100000000);
        search search1 = new search();
        search1.setSearch_id("S"+setSearch_id);
        search1.setUser_id(user_id);
        search1.setSearch_content(keyword);
        if (commodityService.selectByUserId(user_id) == null){
            System.out.println("该用户没有注册就搜索了，不存储搜索记录进数据库");
        }else {
            if (commodityService.findSearchWordByUser_idAndSearch_content(user_id,keyword) == null){
                System.out.println("是否插入搜索记录："+commodityService.insertSearch(search1));
            }else {
                System.out.println("该用户已经搜索过这个记录，不再重复存储进数据库里面");
            }
        }

    }

    //按客户获取历史记录
    @RequestMapping("/findByUserSearch")
    @ResponseBody
    public LinkedHashSet findByUserSearch(String user_id) throws IOException {
        System.out.println("user_id："+user_id);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<search> list =commodityService.findByUserSearch(user_id);
        for (int i=0;i<list.size();i++){
            linkedHashSet.add(list.get(i).getSearch_content());
        }
        return linkedHashSet;
    }

    //删除搜索记录
    @RequestMapping("/deleteByUserSearch")
    @ResponseBody
    public void deleteByUserSearch(String user_id) throws IOException {
        System.out.println("user_id："+user_id);
        System.out.println("是否清空该客户搜索记录："+commodityService.deleteByUserSearch(user_id));
    }

    //查询所有商品
    @RequestMapping("/findAllCommodityByid/{id}")
    @ResponseBody
    public commodity findAllCommodityByid(@PathVariable String id) throws IOException {
        commodity commodity = new commodity();
        System.out.println(id);
        commodity = commodityService.findByTypeCommodityByid(id);
        System.out.println(commodity);
        return commodity;
    }



}
