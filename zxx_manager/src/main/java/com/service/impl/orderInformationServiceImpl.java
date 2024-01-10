package com.service.impl;

import com.Pojo.orderInformation;
import com.Pojo.shipAddress;
import com.Pojo.user;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.orderInformationMapper;
import com.mapper.shipAddressMapper;
import com.mapper.userMapper;
import com.service.orderInformationService;
import com.util.ResponseJSONResult;
import net.sf.jsqlparser.schema.Database;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("orderInformationServiceImpl")
public class orderInformationServiceImpl implements orderInformationService {
    @Lazy
    @Autowired
    private orderInformationMapper orderInformationMapper;

    @Lazy
    @Autowired
    private shipAddressMapper shipAddressMapper;

    @Override
    //管理员分页查看所有订单信息
    public PageInfo<orderInformation> findAllUserOrder(Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<orderInformation> list = orderInformationMapper.findAllUserOrder();
        return new PageInfo<orderInformation>(list);
    }

    @Override
    //通过order_id查询该订单的所有信息
    public orderInformation selectByOrderId(String order_id){
        return orderInformationMapper.selectByOrderId(order_id);
    }

    @Override
    //更新订单信息
    public ResponseJSONResult updateOrderInformation(String orderInformation,String shipAddress) {
        String data = null;//解encodeURIComponent码
        try {
            data = java.net.URLDecoder.decode(orderInformation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonArray = JSONArray.parseObject(data);
        orderInformation orderInformationUpdate = new orderInformation();
        orderInformationUpdate.setOrder_id(String.valueOf(jsonArray.get("order_id")));
        orderInformationUpdate.setOrder_account(Integer.parseInt(String.valueOf(jsonArray.get("order_account"))));
        orderInformationUpdate.setUser_id(String.valueOf(jsonArray.get("user_id")));
        orderInformationUpdate.setGood_sumprice(Double.parseDouble(String.valueOf(jsonArray.get("good_sumprice"))));
        orderInformationUpdate.setOrder_sumPrice(Double.parseDouble(String.valueOf(jsonArray.get("order_sumPrice"))));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (jsonArray.get("order_paymentTime")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_paymentTime")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_paymentTime = new Timestamp(date.getTime());//将前端的datetime-local类型的时间在后端转成TimeStamp格式
            orderInformationUpdate.setOrder_paymentTime(order_paymentTime);
        }
        orderInformationUpdate.setOrder_status(String.valueOf(jsonArray.get("order_status")));
        if (jsonArray.get("order_way")!=null){
            orderInformationUpdate.setOrder_remark(String.valueOf(jsonArray.get("order_remark")));
        }
        orderInformationUpdate.setOrder_way(String.valueOf(jsonArray.get("order_way")));
        orderInformationUpdate.setOrder_user_delete(Integer.parseInt(String.valueOf(jsonArray.get("order_user_delete"))));
        if (jsonArray.get("order_refund_reason")!=null){
            orderInformationUpdate.setOrder_refund_reason(String.valueOf(jsonArray.get("order_refund_reason")));
        }
        if (jsonArray.get("order_refund_instructions")!=null){
            orderInformationUpdate.setOrder_refund_instructions(String.valueOf(jsonArray.get("order_refund_instructions")));
        }
        if (jsonArray.get("order_createTime")!=null){
            System.out.println("时间："+jsonArray.get("order_createTime"));
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_createTime")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_createTime = new Timestamp(date.getTime());
            System.out.println(order_createTime+"  Timestamp格式的时间（java.sql的包）");
            orderInformationUpdate.setOrder_createTime(order_createTime);
        }
        if (jsonArray.get("order_newCreateTime")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_newCreateTime")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_newCreateTime = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_newCreateTime(order_newCreateTime);
        }
        if (jsonArray.get("order_shipping_time")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_shipping_time")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_shipping_time = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_shipping_time(order_shipping_time);
        }
        if (jsonArray.get("order_confirm_time")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_confirm_time")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_confirm_time = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_confirm_time(order_confirm_time);
        }
        if (jsonArray.get("order_apply_refundTime")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_apply_refundTime")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_apply_refundTime = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_apply_refundTime(order_apply_refundTime);
        }
        if (jsonArray.get("order_refund_time")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_refund_time")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_refund_time = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_refund_time(order_refund_time);
        }
        if (jsonArray.get("order_predict_predictTime")!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(jsonArray.get("order_predict_predictTime")).replaceAll("T", " ") + ":00", df);
            Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
            Timestamp order_predict_predictTime = new Timestamp(date.getTime());
            orderInformationUpdate.setOrder_predict_predictTime(order_predict_predictTime);
        }
        shipAddress shipAddress1 = new shipAddress();

        JSONObject jsonObject = JSONArray.parseObject(shipAddress);
        orderInformation orderInformation1 = orderInformationMapper.selectByOrderId(String.valueOf(jsonArray.get("order_id")));
        if (orderInformation1.getShipAddress().getAddress_contact().equals(String.valueOf(jsonObject.get("address_contact")))&&
            orderInformation1.getShipAddress().getAddress_phone().equals(String.valueOf(jsonObject.get("address_phone")))&&
            orderInformation1.getShipAddress().getRegionAddress().equals(String.valueOf(jsonObject.get("regionAddress")))&&
            orderInformation1.getShipAddress().getDetailAddress().equals(String.valueOf(jsonObject.get("detailAddress")))){
            if (orderInformationUpdate.getOrder_status().equals("已收货")){
                int orderSumPrice = orderInformationMapper.findOrderSumPriceByOrder_id(orderInformationUpdate.getOrder_id()).intValue();//获取订单的总价钱
                int userIntegral = orderInformationMapper.getUserIntegral(orderInformationUpdate.getUser_id());//获取当前用户的积分
                System.out.println("orderSumPrice："+orderSumPrice);
                System.out.println("userIntegral："+userIntegral);
                user user = new user();
                user.setUser_id(orderInformationUpdate.getUser_id());
                user.setUser_Integral(userIntegral+orderSumPrice);//添加积分
                orderInformationMapper.updateUserIntegral(user);//更新用户的积分
            }else if (orderInformationUpdate.getOrder_status().equals("已退款")){
                int orderSumPrice = orderInformationMapper.findOrderSumPriceByOrder_id(orderInformationUpdate.getOrder_id()).intValue();//获取订单的总价钱
                int userIntegral = orderInformationMapper.getUserIntegral(orderInformationUpdate.getUser_id());//获取当前用户的积分
                System.out.println("orderSumPrice："+orderSumPrice);
                System.out.println("userIntegral："+userIntegral);
                user user = new user();
                user.setUser_id(orderInformationUpdate.getUser_id());
                if (userIntegral-orderSumPrice<0){
                    user.setUser_Integral(0);//减积分
                }else {
                    user.setUser_Integral(userIntegral-orderSumPrice);//减积分
                }
                orderInformationMapper.updateUserIntegral(user);//更新用户的积分
            }
            int i = orderInformationMapper.updateById(orderInformationUpdate);
            System.out.println("1111"+jsonObject.get("regionAddress"));
            if (i==1){
                return new ResponseJSONResult(200,"订单数据更改成功");
            }
        }else {
            String newAddressId = "address"+ (int) (Math.random() * 1000000000);
            shipAddress1.setAddress_id(newAddressId);
            shipAddress1.setRegionAddress(String.valueOf(jsonObject.get("regionAddress")));
            shipAddress1.setDetailAddress(String.valueOf(jsonObject.get("detailAddress")));
            shipAddress1.setAddress_contact(String.valueOf(jsonObject.get("address_contact")));
            shipAddress1.setAddress_phone(String.valueOf(jsonObject.get("address_phone")));
            shipAddress1.setAddress_default(Integer.parseInt(String.valueOf(jsonObject.get("address_default"))));
            shipAddress1.setAddress_state(0);
            System.out.println("22222"+jsonObject.get("regionAddress"));
            if (orderInformationUpdate.getOrder_status().equals("已收货")){
                int orderSumPrice = orderInformationMapper.findOrderSumPriceByOrder_id(orderInformationUpdate.getOrder_id()).intValue();//获取订单的总价钱
                int userIntegral = orderInformationMapper.getUserIntegral(orderInformationUpdate.getUser_id());//获取当前用户的积分
                System.out.println("orderSumPrice："+orderSumPrice);
                System.out.println("userIntegral："+userIntegral);
                user user = new user();
                user.setUser_id(orderInformationUpdate.getUser_id());
                user.setUser_Integral(userIntegral+orderSumPrice);//添加积分
                orderInformationMapper.updateUserIntegral(user);//更新用户的积分
            }else if (orderInformationUpdate.getOrder_status().equals("已退款")){
                int orderSumPrice = orderInformationMapper.findOrderSumPriceByOrder_id(orderInformationUpdate.getOrder_id()).intValue();//获取订单的总价钱
                int userIntegral = orderInformationMapper.getUserIntegral(orderInformationUpdate.getUser_id());//获取当前用户的积分
                System.out.println("orderSumPrice："+orderSumPrice);
                System.out.println("userIntegral："+userIntegral);
                user user = new user();
                user.setUser_id(orderInformationUpdate.getUser_id());
                if (userIntegral-orderSumPrice<0){
                    user.setUser_Integral(0);//减积分
                }else {
                    user.setUser_Integral(userIntegral-orderSumPrice);//减积分
                }
                orderInformationMapper.updateUserIntegral(user);//更新用户的积分
            }
            shipAddressMapper.addShipAddress(shipAddress1);//在shipAddress表插入更改的用户收地址信息（避免修改旧的旧的用户收货地址）
            int j = orderInformationMapper.updateOrderInformationAddressId(String.valueOf(jsonArray.get("order_id")), newAddressId);//在该订单更换为最新的地址id
            int i = orderInformationMapper.updateById(orderInformationUpdate);
            if (i==1 || j==1){
                return new ResponseJSONResult(200,"订单数据更改成功");
            }
        }
        return new ResponseJSONResult(201,"订单数据更改失败");
    }


    @Override
    //删除订单信息（把删除标志从0改为1）
    public ResponseJSONResult deleteOrderInformation(String order_id) {
        System.out.println("order_id："+order_id);
        int i = orderInformationMapper.deleteOrderInformation(order_id);
        System.out.println(i);
        if (i==0){
           return new ResponseJSONResult(201,"删除订单数据失败");
        }
        return new ResponseJSONResult(200,"删除订单数据成功");
    }

    @Override
    //回归删除订单信息（把删除标志从1改为0）
    public ResponseJSONResult returnDeleteOrderInformation(String order_id) {
        int i = orderInformationMapper.returnDeleteOrderInformation(order_id);
        System.out.println(i);
        if (i==0){
            return new ResponseJSONResult(201,"回归已被删除订单数据失败");
        }
        return new ResponseJSONResult(200,"回归已被删除订单数据成功");
    }

    @Override
    //搜索订单
    public PageInfo<orderInformation> SearchUserOrder(Integer pageNum,String order_id, String user_id){
        PageHelper.startPage(pageNum,8);
        List<orderInformation> list = orderInformationMapper.SearchUserOrder(order_id,user_id);
//        System.out.println("order_id:"+order_id+"user_id:"+user_id);
//        System.out.println("list:"+new PageInfo<orderInformation>(list));
        return new PageInfo<orderInformation>(list);
    }

    @Override
    //统计订单状态的数量
    public ResponseJSONResult countOrderStatus(){
        List<Map<String, Integer>> maps = orderInformationMapper.countOrderStatus();
//        System.out.println("map"+maps);
//        System.out.println("order_status："+maps.get(1).get("order_status"));
//        System.out.println("count(order_status)："+maps.get(1).get("count(order_status)"));
        List<String> countOrderList = new ArrayList<String>();
        for (Map<String, Integer> map : maps) {
            countOrderList.add("{" + "value:" + map.get("count(order_status)") + "," + "name:" + "'" + map.get("order_status") + "'" + "}");
        }
        System.out.println("countOrderList："+countOrderList);
        return new ResponseJSONResult(200,countOrderList);
    }

    @Override
    //统计一些重要的信息
    public ResponseJSONResult countInformation(){
//        System.out.println("所有订单数量："+orderInformationMapper.countTotalOrderNumber());
//        System.out.println("总营业额："+orderInformationMapper.countTotalSales());
//        System.out.println("总销售量："+orderInformationMapper.countTotalSellNumber());
//        System.out.println("顾客总数："+orderInformationMapper.countTotalUserNumber());
//        System.out.println("111："+orderInformationMapper.getAllOrderSumPriceAndConfirmTime());
//        System.out.println("222："+orderInformationMapper.getAllOrderSumPrice());

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("totalOrderNumber", String.valueOf(orderInformationMapper.countTotalOrderNumber()));
        map.put("totalSales",String.valueOf(orderInformationMapper.countTotalSales()));
        map.put("totalSellNumber", String.valueOf(orderInformationMapper.countTotalSellNumber()));
        map.put("totalUserNumber", String.valueOf(orderInformationMapper.countTotalUserNumber()));
        return new ResponseJSONResult(200,map);
    }

    @Override
    //获取所有的订单总价格和收货时间
    public ResponseJSONResult getAllOrderSumPriceAndConfirmTime() {
        List<Map<String, Double>> maps = orderInformationMapper.getAllOrderSumPriceAndConfirmTime();
//        System.out.println("order_confirm_time："+maps.get(1).get("order_confirm_time"));
//        System.out.println("order_sumPrice："+maps.get(1).get("order_sumPrice"));
        Double monthSalMoney[] ={0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
        for (int i=0;i<maps.size();i++){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            if(maps.get(i).get("order_confirm_time")!=null){
                int datet;
                try {
                    datet = f.parse(String.valueOf(maps.get(i).get("order_confirm_time"))).getMonth() + 1;
//                    System.out.println("d:"+datet);
                    if (datet == 1) {
                        monthSalMoney[0] = monthSalMoney[0] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 2) {
                        monthSalMoney[1] = monthSalMoney[1] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 3) {
                        monthSalMoney[2] = monthSalMoney[2] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 4) {
                        monthSalMoney[3] = monthSalMoney[3] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 5) {
                        monthSalMoney[4] = monthSalMoney[4] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 6) {
                        monthSalMoney[5] = monthSalMoney[5] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 7) {
                        monthSalMoney[6] = monthSalMoney[6] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 8) {
                        monthSalMoney[7] = monthSalMoney[7] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 9) {
                        monthSalMoney[8] = monthSalMoney[8] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 10) {
                        monthSalMoney[9] = monthSalMoney[9] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 11) {
                        monthSalMoney[10] = monthSalMoney[10] + maps.get(i).get("order_sumPrice");
                    } else if (datet == 12) {
                        monthSalMoney[11] = monthSalMoney[11] + maps.get(i).get("order_sumPrice");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
//        for (int j = 0;j<monthSalMoney.length;j++){
//            System.out.println("金额："+monthSalMoney[j]);
//        }
        return new ResponseJSONResult(200,monthSalMoney);
    }

    @Override
    //销售总量图表
    public ResponseJSONResult getOrderAccountAndConfirmTimeAndCommodityType(){
        List<Map<String,Object>> dataMap = new ArrayList<Map<String, Object>>();
        dataMap = orderInformationMapper.getOrderAccountAndConfirmTimeAndCommodityType();
        System.out.println("333:"+dataMap);
        Set<String> keysSetType = new HashSet<String>();
        int [][] monthSal = new int[5][12];
        for (Map<String, Object> collisionMap : dataMap) {
            String keysType = collisionMap.get("commodity_type").toString();
//            int beforeSizeType = keysSetType.size();
            keysSetType.add(keysType);
//            int afterSizeType = keysSetType.size();
//            if (afterSizeType == beforeSizeType + 1) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            if (collisionMap.get("order_confirm_time").toString() != null) {
                int datet;
                try {
                    datet = f.parse(String.valueOf(collisionMap.get("order_confirm_time").toString())).getMonth() + 1;
//                    System.out.println("d:" + datet);
                    if (datet == 1) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 2) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 3) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 4) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 5) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 6) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 7) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 8) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 9) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 10) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 11) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    } else if (datet == 12) {
                        if (keysType.equals("水果")){
                            monthSal[0][datet-1] = monthSal[0][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("蔬菜")){
                            monthSal[1][datet-1] = monthSal[1][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("肉")){
                            monthSal[2][datet-1] = monthSal[2][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else if (keysType.equals("海鲜")){
                            monthSal[3][datet-1] = monthSal[3][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }else {
                            monthSal[4][datet-1] = monthSal[4][datet-1] + Integer.parseInt(collisionMap.get("orderItem_amount").toString());
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
//            }
        }

        List list = new ArrayList(keysSetType);
        List<String> countOrderList = new ArrayList<String>();
        for(int i=0;i<list.size();i++){ //先遍历行
            if (list.get(i).equals("水果")){
                countOrderList.add("{" + "'" + "产品类别" + "'"+":" + "'" + "水果类" + "'" + "," + "'" +"1月" + "'" + ":"  + monthSal[0][0] + "," + "'" + "2月" + "'" + ":"  + monthSal[0][1] + "," +
                        "'" + "3月" + "'"+":" +  monthSal[0][2] + "," + "'" +  "4月" + "'"+":" + monthSal[0][3] + "," +  "'" + "5月" + "'"+":" + monthSal[0][4] + "," + "'" + "6月"  + "'"+":" + monthSal[0][5] + "," +
                        "'" + "7月" + "'"+":" +  monthSal[0][6] + "," + "'" +  "8月" + "'"+":" + monthSal[0][7] + "," +  "'" + "9月" + "'"+":" + monthSal[0][8] + "," + "'" + "10月" + "'"+":" + monthSal[0][9] + "," +
                        "'" + "11月"+ "'"+":" +  monthSal[0][10] +"," + "'" + "12月" + "'"+":" + monthSal[0][11] + "}");
            }
            else if (list.get(i).equals("蔬菜")){
                countOrderList.add("{" + "'" + "产品类别" + "'"+":" + "'" + "蔬菜类" + "'" + "," + "'" +"1月" + "'" + ":"  + monthSal[1][0] + "," + "'" + "2月" + "'" + ":"  + monthSal[1][1] + "," +
                        "'" + "3月" + "'"+":" +  monthSal[1][2] + "," + "'" +  "4月" + "'"+":" + monthSal[1][3] + "," +  "'" + "5月" + "'"+":" + monthSal[1][4] + "," + "'" + "6月"  + "'"+":" + monthSal[1][5] + "," +
                        "'" + "7月" + "'"+":" +  monthSal[1][6] + "," + "'" +  "8月" + "'"+":" + monthSal[1][7] + "," +  "'" + "9月" + "'"+":" + monthSal[1][8] + "," + "'" + "10月" + "'"+":" + monthSal[1][9] + "," +
                        "'" + "11月"+ "'"+":" +  monthSal[1][10] +"," + "'" + "12月" + "'"+":" + monthSal[1][11] + "}");
            }else if (list.get(i).equals("肉")){
                countOrderList.add("{" + "'" + "产品类别" + "'"+":" + "'" + "肉类" + "'" + "," + "'" +"1月" + "'" + ":"  + monthSal[2][0] + "," + "'" + "2月" + "'" + ":"  + monthSal[2][1] + "," +
                        "'" + "3月" + "'"+":" +  monthSal[2][2] + "," + "'" +  "4月" + "'"+":" + monthSal[2][3] + "," +  "'" + "5月" + "'"+":" + monthSal[2][4] + "," + "'" + "6月"  + "'"+":" + monthSal[2][5] + "," +
                        "'" + "7月" + "'"+":" +  monthSal[2][6] + "," + "'" +  "8月" + "'"+":" + monthSal[2][7] + "," +  "'" + "9月" + "'"+":" + monthSal[2][8] + "," + "'" + "10月" + "'"+":" + monthSal[2][9] + "," +
                        "'" + "11月"+ "'"+":" +  monthSal[2][10] +"," + "'" + "12月" + "'"+":" + monthSal[2][11] + "}");
            }else if (list.get(i).equals("海鲜")){
                countOrderList.add("{" + "'" + "产品类别" + "'"+":" + "'" + "海鲜类" + "'" + "," + "'" +"1月" + "'" + ":"  + monthSal[3][0] + "," + "'" + "2月" + "'" + ":"  + monthSal[3][1] + "," +
                        "'" + "3月" + "'"+":" +  monthSal[3][2] + "," + "'" +  "4月" + "'"+":" + monthSal[3][3] + "," +  "'" + "5月" + "'"+":" + monthSal[3][4] + "," + "'" + "6月"  + "'"+":" + monthSal[3][5] + "," +
                        "'" + "7月" + "'"+":" +  monthSal[3][6] + "," + "'" +  "8月" + "'"+":" + monthSal[3][7] + "," +  "'" + "9月" + "'"+":" + monthSal[3][8] + "," + "'" + "10月" + "'"+":" + monthSal[3][9] + "," +
                        "'" + "11月"+ "'"+":" +  monthSal[3][10] +"," + "'" + "12月" + "'"+":" + monthSal[3][11] + "}");
            }else {
                countOrderList.add("{" + "'" + "产品类别" + "'"+":" + "'" + "其他类" + "'" + "," + "'" +"1月" + "'" + ":"  + monthSal[4][0] + "," + "'" + "2月" + "'" + ":"  + monthSal[4][1] + "," +
                        "'" + "3月" + "'"+":" +  monthSal[4][2] + "," + "'" +  "4月" + "'"+":" + monthSal[4][3] + "," +  "'" + "5月" + "'"+":" + monthSal[4][4] + "," + "'" + "6月"  + "'"+":" + monthSal[4][5] + "," +
                        "'" + "7月" + "'"+":" +  monthSal[4][6] + "," + "'" +  "8月" + "'"+":" + monthSal[4][7] + "," +  "'" + "9月" + "'"+":" + monthSal[4][8] + "," + "'" + "10月" + "'"+":" + monthSal[4][9] + "," +
                        "'" + "11月"+ "'"+":" +  monthSal[4][10] +"," + "'" + "12月" + "'"+":" + monthSal[4][11] + "}");
            }
        }
        System.out.println("countOrderList："+countOrderList);
        return new ResponseJSONResult(200,countOrderList);
    }

}
