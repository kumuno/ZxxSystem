package com.service.impl;

import com.Pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.*;
import com.service.commodityService;
import com.util.ResponseJSONResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("commodityServiceImpl")
public class commodityServiceImpl implements commodityService {

    @Lazy
    @Resource
    private commodityMapper commodityMapper;

    @Lazy
    @Resource
    private orderItemMapper orderItemMapper;

    @Lazy
    @Resource
    private shopCartMapper shopCartMapper;

    @Lazy
    @Resource
    private commodityAttributeMapper commodityAttributeMapper;

    @Lazy
    @Resource
    private commoditySonAttributeMapper commoditySonAttributeMapper;

    //分页
    @Override
    public PageInfo<commodity> findAllCommodityByPage(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<commodity> list = commodityMapper.findAllCommodityByPage();
        return new PageInfo<commodity>(list);
    }


    @Override
    public void deleteByCommodityId(String commodity_id) {
        System.out.println("====================service业务层，下架商品========================");
        commodityMapper.deleteByCommodityId(commodity_id);
    }

    //删除商品
    @Override
    public ResponseJSONResult deleteByCommodityForever(String commodity_id) {
        System.out.println("====================service业务层，删除商品========================");
//        System.out.println("commodity_id:"+orderItemMapper.findOrderCommodity(commodity_id));
        if (orderItemMapper.findOrderCommodity(commodity_id).size() == 0){
            System.out.println("1111");
//            System.out.println("22"+commodityAttributeMapper.getAllAttribute_id(commodity_id));
            List<shopCart> list = shopCartMapper.selectByCommodityId(commodity_id);
            System.out.println(list);
            for (shopCart shopCart:list){
                System.out.println(shopCart.getShopcart_id());
                shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品有关的购物车全部清空
            }

            List<String> allAttribute_id = commodityAttributeMapper.getAllAttribute_id(commodity_id);//查找该商品id的所有商品属性id

            for (int i=0;i<allAttribute_id.size();i++){
                commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
                commoditySonAttribute.setAttribute_id(allAttribute_id.get(i));
                commoditySonAttribute.setCommoditySonAttribute_state(0);
                commoditySonAttributeMapper.updateCommoditySonAttribute_stateByAttribute_id(commoditySonAttribute);
            }
            commodityAttributeMapper.deleteByCommodityAttributeForever(commodity_id);
            commodityMapper.deleteByCommodityForever(commodity_id);//真正的删除商品信息
            return new  ResponseJSONResult(200,"由于该商品没有被顾客下单，已经把该商品的信息完全清除掉（包括所有顾客的和该商品有关的购物车信息）！！");
        }else{
            System.out.println("2222");
            List<String> allAttribute_id = commodityAttributeMapper.getAllAttribute_id(commodity_id);//查找该商品id的所有商品属性id
            for (int i=0;i<allAttribute_id.size();i++){
                commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
                System.out.println(allAttribute_id.get(i));
                commoditySonAttribute.setAttribute_id(allAttribute_id.get(i));
                commoditySonAttribute.setCommoditySonAttribute_state(0);
                commoditySonAttributeMapper.updateCommoditySonAttribute_stateByAttribute_id(commoditySonAttribute);
            }
            commodityAttributeMapper.deleteCommodityAttributeByCommodity_id(commodity_id);
            commodityMapper.deleteByCommodityId(commodity_id);

            //商品改为下架状态，同时把该商品用户的购物车也清空
            List<shopCart> list = shopCartMapper.selectByCommodityId(commodity_id);
            System.out.println(list);
            for (shopCart shopCart:list){
                System.out.println(shopCart.getShopcart_id());
                shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
            }

            return  new ResponseJSONResult(201,"由于该商品已经被顾客下单，已经把该商品的状态改为下架状态，如果需要可点击回归重新进行上架。");
        }

    }

    //回归被删除商品
    @Override
    public ResponseJSONResult returnDeleteByCommodityForever(String commodity_id) {
            System.out.println("2222");
            List<String> allAttribute_id = commodityAttributeMapper.getAllAttribute_id(commodity_id);//查找该商品id的所有商品属性id
            for (int i=0;i<allAttribute_id.size();i++){
                commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
                commoditySonAttribute.setAttribute_id(allAttribute_id.get(i));
                commoditySonAttribute.setCommoditySonAttribute_state(1);
                commoditySonAttributeMapper.updateCommoditySonAttribute_stateByAttribute_id(commoditySonAttribute);
            }
            commodityAttributeMapper.deleteCommodityAttributeByCommodity_id(commodity_id);
            commodityMapper.deleteByCommodityId(commodity_id);
            return  new ResponseJSONResult(201,"成功回归该商品的所有信息（包含商品属性，子属性），已改为上架状态");
    }


    @Override
    public commodity queryByCommodityId(String commodity_id) {
        System.out.println("====================service业务层，查询目标商品的所有信息========================");
        List<commodityAttribute> commodityAttributeList = commodityAttributeMapper.getCommodityAttributeByCommodity_id(commodity_id);//查找该商品id的所有商品属性id
        commodity commodity = commodityMapper.queryByCommodityId(commodity_id);
        if (commodity == null){//商品子属性表为空的时候，无法三个表一起查询
            commodity commodity1 = new commodity();
            commodity commodityByCommodity_id = commodityMapper.getCommodityByCommodity_id(commodity_id);
            commodity1 = commodityByCommodity_id;
            commodity1.setCommodityAttribute(commodityAttributeList);
            return commodity1;
        }
        commodity.setCommodityAttribute(commodityAttributeList);//需要重新对CommodityAttribute赋值，避免有其中一条子属性为空，其属性查询不到的情况出现
        return commodity;
    }

    @Override
    public ResponseJSONResult updateCommodity(commodity commodity) {
        System.out.println("====================service业务层，商品属性更新信息========================");
        if (orderItemMapper.findOrderCommodity(commodity.getCommodity_id()).size() == 0) {
            commodityMapper.updateCommodity(commodity);
            return  new ResponseJSONResult(200,"更新商品子属性成功");
        }
        String HCommodity_id = commodity.getCommodity_id();//保存最原始的商品编号
        List<commodityAttribute> commodityAttributeList = commodityAttributeMapper.getCommodityAttributeByCommodity_id(commodity.getCommodity_id());//查找该商品id的所有商品属性id
        commodity commodity1 = commodityMapper.queryByCommodityId(commodity.getCommodity_id());//三个表一起查询
        String commodity_id = "C"+(int) (Math.random() * 100000);
        List<String> stringList = new LinkedList<>();
        for (int i=0;i<commodityAttributeList.size();i++){
            stringList.add( "A"+ (int) (Math.random() * 100000));
        }
        if (commodity1 == null){//商品子属性表为空的时候，无法三个表一起查询
            commodityMapper.updateCommodityState(commodity.getCommodity_id(),0);
//            commodity commodity2 = new commodity();
//            commodity2 = commodityMapper.getCommodityByCommodity_id(commodity.getCommodity_id());
            commodity.setCommodity_id(commodity_id);
            commodity.setCommodity_state(1);
            commodityMapper.addCommodity(commodity);
            for (int i = 0;i<commodityAttributeList.size();i++){
                commodityAttributeMapper.updateCommodityAttribute_state(commodityAttributeList.get(i).getAttribute_id(),0);
                commodityAttributeList.get(i).setAttribute_id(stringList.get(i));
                commodityAttributeList.get(i).setCommodity_id(commodity_id);
                commodityAttributeList.get(i).setCommodityAttribute_state(1);
                commodityAttributeMapper.addCommodityAttribute(commodityAttributeList.get(i));
            }


            //商品改为下架状态，同时把该商品用户的购物车也清空
            List<shopCart> list = shopCartMapper.selectByCommodityId(HCommodity_id);
            System.out.println(list);
            for (shopCart shopCart:list){
                System.out.println(shopCart.getShopcart_id());
                shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
            }


//          commodity2.setCommodityAttribute(commodityAttributeList);
            return  new ResponseJSONResult(200,"由于该商品被顾客下单，无法更改原信息，已经生成最新的商品信息，旧商品被默认下架");
        }
        System.out.println("commodity_id:"+commodity_id);
        commodityMapper.updateCommodityState(commodity.getCommodity_id(),0);
        commodity.setCommodity_id(commodity_id);
        commodity.setCommodity_state(1);
        System.out.println("6666："+commodity);
        commodityMapper.addCommodity(commodity);
        for (int i = 0;i<commodityAttributeList.size();i++){
            //主要是为了存储每个商品属性有多少条商品子属性
            List<commoditySonAttribute> sonAttributeList = commoditySonAttributeMapper.getAllCommoditySonAttributeInformationByAttribute_id(commodityAttributeList.get(i).getAttribute_id());
            String Attribute_id = commodityAttributeList.get(i).getAttribute_id();//提前存储，更新原商品信息需要用到
            commodityAttributeList.get(i).setAttribute_id(stringList.get(i));
            commodityAttributeList.get(i).setCommodity_id(commodity_id);
//            commodityAttributeList.get(i).setCommodityAttribute_state(1);
            commodityAttributeMapper.addCommodityAttribute(commodityAttributeList.get(i));//新插入商品属性信息（修改的最新信息）

            //把原商品属性表改为下架状态
            commodityAttributeMapper.updateCommodityAttribute_state(Attribute_id,0);

            for(int j = 0;j<sonAttributeList.size();j++){
                String sonAttribute_id = sonAttributeList.get(j).getSonAttribute_id();//提前存储，更新原商品信息需要用到
                sonAttributeList.get(j).setSonAttribute_id("SA"+ (int) (Math.random() * 100000));
                sonAttributeList.get(j).setAttribute_id(stringList.get(i));
//                sonAttributeList.get(j).setCommoditySonAttribute_state(1);
                commoditySonAttributeMapper.addCommoditySonAttribute(sonAttributeList.get(j));//新插入商品子属性信息（修改的最新信息）

                commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
                commoditySonAttribute.setSonAttribute_id(sonAttribute_id);
                commoditySonAttribute.setCommoditySonAttribute_state(0);
                commoditySonAttributeMapper.updateCommoditySonAttribute_state(commoditySonAttribute);//把原商品子属性表改为下架状态
            }
        }


        //商品改为下架状态，同时把该商品用户的购物车也清空
        List<shopCart> list = shopCartMapper.selectByCommodityId(HCommodity_id);
        System.out.println(list);
        for (shopCart shopCart:list){
            System.out.println(shopCart.getShopcart_id());
            shopCartMapper.deleteShopCart(shopCart.getShopcart_id());//把该商品子属性有关的购物车全部清空
        }

//        commodity1.setCommodityAttribute(commodityAttributeList);//需要重新对CommodityAttribute赋值，避免有其中一条子属性为空，其属性查询不到的情况出现
        return  new ResponseJSONResult(201,"由于该商品被顾客下单，无法更改原信息，已经生成最新的商品信息，旧商品被默认下架");
    }

    @Override
    public String addCommodity(commodity commodity) {
        System.out.println("===============IMPL层添加商品============");
        System.out.println(commodity.getCommodity_img());
        System.out.println(commodity.getCommodity_carousel_Img());
        commodity.setCommodity_id("C"+ (int) (Math.random() * 100000));
        if (commodity.getCommodity_type().equals("水果")){
            commodity.setCommodity_flag("fruit");
        }else if (commodity.getCommodity_type().equals("蔬菜")){
            commodity.setCommodity_flag("vegetables");
        }else if (commodity.getCommodity_type().equals("肉")){
            commodity.setCommodity_flag("meat");
        }else if (commodity.getCommodity_type().equals("海鲜")){
            commodity.setCommodity_flag("seafood");
        }else {
            commodity.setCommodity_flag("others");
        }
        boolean msg= commodityMapper.addCommodity(commodity);
        if (msg==true){
            return commodity.getCommodity_id();
        }else{
            return "false";
        }
    }

    @Override
    public PageInfo<commodity> conditionalQueriesCommodity(Integer pageNum, String keyword1, String keyword2) {
        System.out.println("======================service业务层，商品多条件查询======================");
        PageHelper.startPage(pageNum,5);
        List<commodity> commodity = commodityMapper.conditionalQueriesCommodity(keyword1,keyword2);
        System.out.println(commodity);
        return new PageInfo<commodity>(commodity);
    }

    //商品修改/新增存储数据库照片路径
    @Override
    public boolean UpdateCommodity_url(commodity commodity) {
        System.out.println("执行");
        System.out.println(commodity);
        return commodityMapper.updateCommodity_url(commodity);
    }



}
