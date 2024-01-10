package com.service.impl;

import com.Pojo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Config;
import com.common.ResultInfo;
import com.mapper.*;
import com.service.orderInformationService;
import com.service.shopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderInformationServiceImpl")
public class orderInformationServiceImpl implements orderInformationService {
    @Lazy
    @Autowired
    private orderInformationMapper orderInformationMapper;

    @Lazy
    @Autowired
    private orderItemMapper orderItemMapper;

    @Lazy
    @Autowired
    private shipAddressMapper shipAddressMapper;

    @Lazy
    @Autowired
    private shopCartMapper shopCartMapper;

    @Lazy
    @Autowired
    private commodityMapper commodityMapper;

    @Lazy
    @Autowired
    private userMapper userMapper;

    //订单查找用户地址
    @Override
    public shipAddress orderFindAddress(String addressId , String openId) {
        System.out.println("addressId:"+addressId+"   openId: "+openId);
        if (!addressId.equals("")) {
            return shipAddressMapper.findShipAdderssByaddress_id(addressId);
        }
        shipAddress shipAddress = shipAddressMapper.findShipAdderssByaddress_default(openId);
        return shipAddress;//如果还没有选择地址就直接返回默认地址
    }

    //订单查找已经选择购买的商品信息
    @Override
    public Map<String,Object> orderFindCart(String openId) {
        System.out.println("openId: "+openId);
            List<shopCart> shopCarts = shopCartMapper.selectCheckedByshopCartID(openId);
            int checkedGoodsCount = 0;
            double checkedGoodsAmount = 0;
            for (int j = 0; j < shopCarts.size(); j++) {
                checkedGoodsCount = checkedGoodsCount+shopCarts.get(j).getShopcart_amount();
                checkedGoodsAmount =checkedGoodsAmount + shopCarts.get(j).getShopcart_amount()*shopCarts.get(j).getCommoditySonAttribute().getCommodity_price();
            }
            Map<String,Object> cartTotal = new HashMap<String, Object>(  );
            cartTotal.put("checkedGoodsCount",checkedGoodsCount);
            cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
            cartTotal.put("shopCarts",shopCarts);
            return cartTotal;
    }

    //查询直接购买商品信息
    @Override
    public Map<String,Object> orderFindDirectGood(String commodity_id, String attribute_id, String sonAttribute_id, int number) {
        commodity commodity = commodityMapper.oderFindOneCommodity(commodity_id, attribute_id, sonAttribute_id);

        commodity commodity1 = new  commodity();
        commodity1.setCommodity_img(commodity.getCommodity_img());
        commodity1.setCommodity_name(commodity.getCommodity_name());
        commodity1.setCommodity_id(commodity.getCommodity_id());
//        commodity1.setAddress_id(commodity.getAddress_id());
        commodity1.setCommodity_carousel_Img(commodity.getCommodity_carousel_Img());
        commodity1.setCommodity_flag(commodity.getCommodity_flag());
        commodity1.setCommodity_introduce(commodity.getCommodity_introduce());
        commodity1.setCommodity_sales(commodity.getCommodity_sales());
        commodity1.setCommodity_state(commodity.getCommodity_state());
        commodity1.setCommodity_tag(commodity.getCommodity_tag());
        commodity1.setCommodity_type(commodity.getCommodity_type());

        commoditySonAttribute commoditySonAttribute = new commoditySonAttribute();
        commoditySonAttribute.setAttribute_id(commodity.getCommoditySonAttribute().getAttribute_id());
        commoditySonAttribute.setAttribute_name(commodity.getCommoditySonAttribute().getAttribute_name());
        commoditySonAttribute.setCommodity_number(commodity.getCommoditySonAttribute().getCommodity_number());
        commoditySonAttribute.setCommodity_price(commodity.getCommoditySonAttribute().getCommodity_price());
        commoditySonAttribute.setSonAttribute_id(commodity.getCommoditySonAttribute().getSonAttribute_id());

        Map<String,Object> cartTotal = new HashMap<String, Object>(  );//为了方便数据的结构和通过购物车购买获取到的数据的结构一样
        shopCart shopCart = new shopCart();
        shopCart.setChecked(1);
        shopCart.setCommodity(commodity1);
        shopCart.setCommoditySonAttribute(commoditySonAttribute);
        shopCart.setShopcart_amount(number);
        List<shopCart>  shopCarts = new LinkedList<>();
        shopCarts.add(shopCart);
//        cartTotal.put("cartTotal",commodity);
        cartTotal.put("shopCarts",shopCarts);
        cartTotal.put("checkedGoodsCount",number);//商品数量
        cartTotal.put("checkedGoodsAmount",number*commodity.getCommoditySonAttribute().getCommodity_price());//总价
        return cartTotal;
    }

    //用户提交订单
    @Override
    public ResultInfo<Object> orderSubmit(String checkedGoodsList,//购买的商品信息
                                          String openId,//用户id
                                          String addressId,//地址id
                                          String postscript,//备注信息
                                          Double freightPrice,//运费
                                          Double goodsTotalPrice,//商品总价
                                          Double actualPrice,//实际支付价钱
                                          Integer goodsCount,//总商品数量
                                          Integer addType)// 0：正常加入购物车，1:立即购买(判断是否要删除购物车信息)
    {
        //先插入总订单信息
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month =  String.valueOf((now.get(Calendar.MONTH) + 1));
        if (Integer.parseInt(month)<10){
            month = "0"+month;
        }
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(day)<10){
            day = "0"+day;
        }
        String order_id = year + month + day + (int) (Math.random() * 1000000000);
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);//时间存储为字符串
        orderInformation orderInformation = new orderInformation();
        orderInformation.setOrder_id(order_id);
        orderInformation.setOrder_account(goodsCount);
        orderInformation.setUser_id(openId);
        orderInformation.setGood_sumprice(goodsTotalPrice);
        orderInformation.setOrder_sumPrice(actualPrice);
        orderInformation.setOrder_paymentTime(null);//现在只是创建订单，还没进行支付，支付之后才会更新它
        orderInformation.setOrder_status("待付款");
        orderInformation.setOrder_way("待支付");
        orderInformation.setOrder_remark(postscript);
        orderInformation.setAddress_id(addressId);
        orderInformation.setOrder_user_delete(0);
        orderInformation.setOrder_createTime(Timestamp.valueOf(str));
        orderInformation.setOrder_newCreateTime(Timestamp.valueOf(str));
        int flat = orderInformationMapper.insertOrderInformation(orderInformation);


        JSONArray jsonArray =(JSONArray) JSONArray.parse(checkedGoodsList);//先将String转为Json数组
        List<String> listGoodName = new LinkedList<String>();
//        System.out.println("checkedGoodsList:"+jsonArray);
        for(Object o : jsonArray){
            JSONObject jsonObject = (JSONObject) o;//再将Json数组中String转为Json对象
            JSONObject commodity = (JSONObject) jsonObject.get("commodity");//先获取commodity对象才能获取其里面的属性
            JSONObject commoditySonAttribute = (JSONObject) jsonObject.get("commoditySonAttribute");//先获取commoditySonAttribute对象才能获取其里面的属性
            if (addType ==0 && flat ==1){//在购物车生成订单成功要删除加入购物车的商品信息
                shopCartMapper.deleteShopCart(String.valueOf(jsonObject.get("shopcart_id")));
            }
            listGoodName.add(String.valueOf(commodity.get("commodity_name")));//微信支付需要填写所有商品名称的信息
            if (flat == 1){//若生成总订单成功
                orderItem orderItem = new orderItem();
                orderItem.setOrderItem_id("orderItem"+ (int) (Math.random() * 1000000000));
                orderItem.setOrder_id(order_id);
                orderItem.setCommodity_id(String.valueOf(commodity.get("commodity_id")));
                orderItem.setSonAttribute_id(String.valueOf(commoditySonAttribute.get("sonAttribute_id")));
                orderItem.setOrderItem_amount(Integer.parseInt(String.valueOf(jsonObject.get("shopcart_amount"))));
                orderItem.setOrderItem_goodname(String.valueOf(commodity.get("commodity_name")));
                orderItem.setOrderItem_goodimage(String.valueOf(commodity.get("commodity_img")));
                orderItem.setAttribute_name(String.valueOf(commoditySonAttribute.get("attribute_name")));
                orderItem.setOrderItem_goodprice(Double.parseDouble(String.valueOf(commoditySonAttribute.get("commodity_price"))));
                orderItem.setOrderItem_createTime(Timestamp.valueOf(str));
                orderItem.setOrderItem_user_delete(0);
                orderItemMapper.insertOrderItem(orderItem);//生成订单商品信息
                //更新商品的销售量库存量
                //新库存量 = 旧库存量-购买量
                int commodity_number = Integer.parseInt(String.valueOf(commoditySonAttribute.get("commodity_number")))-Integer.parseInt(String.valueOf(jsonObject.get("shopcart_amount")));
                //新销售量 = 旧销售量+购买量
                int commodity_sales = Integer.parseInt(String.valueOf(commodity.get("commodity_sales")))+Integer.parseInt(String.valueOf(jsonObject.get("shopcart_amount")));
                commodityMapper.updateCommodityNumber(String.valueOf(commodity.get("commodity_id")),String.valueOf(commoditySonAttribute.get("sonAttribute_id")),
                                                        commodity_number,commodity_sales);
            }
        }
        JSONObject params = new JSONObject();
        int code;
        if (flat ==1){
            params.put("orderId", order_id);
            params.put("listGoodName",listGoodName);
            code=0;
        }else {
            params.put("errmsg", "下单失败");
            code=1;
        }
        ResultInfo<Object> resultInfo = new ResultInfo<>(code,params);
        return resultInfo;
    }

    //更新微信支付的信息
    @Override
    public ResultInfo<Object> orderUpdatePayInformation(String openId, String orderId) {
//        System.out.println("orderId:"+orderId+"   openId: "+openId);
        int code;
        JSONObject params = new JSONObject();
        if (!orderId.equals("")) {
            Date date = new Date();//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date);//时间存储为字符串
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            //获取之前几天时间用减，几天后时间用加
            c1.add(Calendar.DATE, + 14);//默认设置预计到达时间为下单的14天之后
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("14天后日期"+df.format(c1.getTime()));
            orderInformation orderInformation = new orderInformation();
            orderInformation.setOrder_id(orderId);
            orderInformation.setUser_id(openId);
            orderInformation.setOrder_status("待发货");
            orderInformation.setOrder_way("微信支付");
            orderInformation.setOrder_paymentTime(Timestamp.valueOf(str));
            orderInformation.setOrder_newCreateTime(Timestamp.valueOf(str));
            orderInformation.setOrder_predict_predictTime(Timestamp.valueOf(df.format(c1.getTime())));
            orderInformationMapper.updateOrderStatus(orderInformation);

            code=0;
            params.put("msg","更新微信支付信息成功");
        }else {
            code = 1;
            params.put("errmsg", "更新微信支付信息失败");
        }
        return new ResultInfo<>(code,params);
    }

    //用户查询所有订单
    @Override
    public ResultInfo<Object> findAllOrder(String openId,Integer showType) {
//        System.out.println("openId: "+openId);
        int code;
        JSONObject params = new JSONObject();
        if (!openId.equals("")){
            code = 0;
            if (showType ==0){//查看全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, null);
                int count = orderInformationMapper.orderByTypeNumber(openId,null);
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }else if (showType ==1){//查看待付款的全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, "待付款");
                int count = orderInformationMapper.orderByTypeNumber(openId,"待付款");
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }else if (showType ==2){//查看待发货的全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, "待发货");
                int count = orderInformationMapper.orderByTypeNumber(openId,"待发货");
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }else if (showType ==3){//查看待收货的全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, "待收货");
                int count = orderInformationMapper.orderByTypeNumber(openId,"待收货");
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }else if (showType ==4){//查看待收货的全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, "已收货");
                int count = orderInformationMapper.orderByTypeNumber(openId,"已收货");
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }else if (showType ==5){//查看待收货的全部订单
                List<orderInformation> orderList = orderInformationMapper.findAllOrder(openId, "退款");//里面有模糊查询,可以查到待退款和已退款的订单信息
                int count = orderInformationMapper.orderByTypeNumber(openId,"退款");//里面有模糊查询,可以查到待退款和已退款的订单总数
                params.put("count",count);//统计该类型订单总数，用来分页
                params.put("orderList",orderList);
            }
        }else {
            code = 1;
            params.put("errmsg","获取全部订单信息失败");
        }
        return new ResultInfo<>(code,params);
    }

    //统计各类型的订单数量
    @Override
    public ResultInfo<Object> orderByTypeNumber(String openId) {
//        System.out.println("openId: "+openId);
        int code;
        JSONObject params = new JSONObject();
        if (!openId.equals("")) {
            code = 0;
            int toPayNumber = orderInformationMapper.orderByTypeNumber(openId,"待付款");
            params.put("toPay",toPayNumber);
            int toDeliveryNumber = orderInformationMapper.orderByTypeNumber(openId,"待发货");
            params.put("toDelivery",toDeliveryNumber);
            int toReceiveNumber = orderInformationMapper.orderByTypeNumber(openId,"待收货");
            params.put("toReceive",toReceiveNumber);
            int toDeleteNumber = orderInformationMapper.orderByTypeNumber(openId,"已收货");
            params.put("toDelete",toDeleteNumber);
            int toWaitRefundNumber = orderInformationMapper.orderByTypeNumber(openId,"待退款");
            int toAccomplishRefundNumber = orderInformationMapper.orderByTypeNumber(openId,"已退款");
            params.put("toRefund",toWaitRefundNumber + toAccomplishRefundNumber);//待退款和已退款共同在一个页面显示
        }else {
            code = 1;
            params.put("errmsg","获取订单数量失败");
        }
        return new ResultInfo<>(code,params);
    }

    //通过order_id查询该订单的所有信息
    @Override
    public ResultInfo<Object> selectByOrderId(String order_id) {
//        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        JSONObject params1 = new JSONObject();//params的子属性
        if (!order_id.equals("")) {
            code = 0;
            orderInformation orderInformation = orderInformationMapper.selectByOrderId(order_id);
            params.put("orderInformation",orderInformation);
            if (orderInformation.getOrder_status().equals("待付款")){
                params1.put("cancel",true);
            }else if (orderInformation.getOrder_status().equals("待发货")){
                params1.put("wait",true);
            }else if (orderInformation.getOrder_status().equals("待收货")){
                params1.put("confirm",true);
            }else if (orderInformation.getOrder_status().equals("已收货")){
                params1.put("delete",true);
            }else if (orderInformation.getOrder_status().equals("待退款")){
                params1.put("waitRefund",true);
            }else if (orderInformation.getOrder_status().equals("已退款")){
                params1.put("accomplishRefund",true);
            }
            params.put("handleOption",params1);

        }else {
            code = 1;
            params.put("errmsg","获取订单详情信息失败");
        }
        return new ResultInfo<>(code,params);
    }

    //通过order_id查询该总订单的商品订单所有信息
    @Override
    public ResultInfo<Object> findOrderGoodsByOrderId(String order_id) {
//        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        if (!order_id.equals("")) {
            code = 0;
            List<orderItem> orderGoodsList = orderItemMapper.findOrderGoodsByOrderId(order_id);
            params.put("orderGoodsList",orderGoodsList);
        }else {
            code = 1;
            params.put("errmsg","获取订单详情信息失败");
        }
        return new ResultInfo<>(code,params);
    }

    //用户取消或者删除订单
    @Override
    public ResultInfo<Object> orderCancel(String order_id,String user_id) {
        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        if (!order_id.equals("")) {
            code = 0;
            List<orderItem> orderGoodsList = orderItemMapper.findOrderGoodsByOrderId(order_id);
            for (int i=0;i<orderGoodsList.size();i++){
                //更新商品的销售量库存量（更新回来）
                //新库存量 = 旧库存量+购买量
                int commodity_number = Integer.parseInt(String.valueOf(orderGoodsList.get(i).getCommoditySonAttribute().getCommodity_number() + orderGoodsList.get(i).getOrderItem_amount()));
                //新销售量 = 旧销售量-购买量
                int commodity_sales = Integer.parseInt(String.valueOf(orderGoodsList.get(i).getCommodity().getCommodity_sales() - orderGoodsList.get(i).getOrderItem_amount()));
//                System.out.println("新库存量:"+commodity_number);
//                System.out.println("新销售量:"+commodity_sales);
                commodityMapper.updateCommodityNumber(orderGoodsList.get(i).getCommodity_id(),orderGoodsList.get(i).getSonAttribute_id(),
                        commodity_number,commodity_sales);
            }
            //用户取消/删除订单
            int orderCancel = orderInformationMapper.orderCancel(order_id, user_id);
            if (orderCancel ==1){
                params.put("errmsg","用户取消/删除订单成功");
            }
        }else {
            code = 1;
            params.put("errmsg","用户取消/删除订单失败");
        }
        return new ResultInfo<>(code,params);
    }

    //修改总订单的备注信息
    @Override
    public ResultInfo<Object> updateOrderRemark(String order_id,String user_id,String order_remark) {
//        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        if (!order_id.equals("")) {
            code = 0;
            int updateOrderRemark = orderInformationMapper.updateOrderRemark(order_id,user_id,order_remark);
            params.put("errmsg","成功修改订单备注");
        }else {
            code = 1;
            params.put("errmsg","修改订单备注失败");
        }
        return new ResultInfo<>(code,params);
    }

    //用户申请退款
    @Override
    public ResultInfo<Object> updateApplyOrderRefund(String order_id,String user_id,String order_refund_reason,String order_refund_instructions,String order_refund_image) {
//        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        if (!order_id.equals("")) {
            code = 0;
            Date date = new Date();//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date);//时间存储为字符串
            orderInformation orderInformation = new orderInformation();
            orderInformation.setOrder_id(order_id);
            orderInformation.setUser_id(user_id);
            orderInformation.setOrder_refund_reason(order_refund_reason);
            orderInformation.setOrder_refund_instructions(order_refund_instructions);
            orderInformation.setOrder_refund_image(order_refund_image);
            orderInformation.setOrder_apply_refundTime(Timestamp.valueOf(str));
            int updateApplyOrderRefund = orderInformationMapper.updateApplyOrderRefund(orderInformation);
            params.put("errmsg","成功申请退款");
        }else {
            code = 1;
            params.put("errmsg","申请退款失败");
        }
        return new ResultInfo<>(code,params);
    }

    //修改订单状态为已收货
    @Override
    public ResultInfo<Object> updateOrderStatusToArrive(String order_id,String user_id) {
        System.out.println("order_id: "+order_id);
        int code;
        JSONObject params = new JSONObject();
        if (!order_id.equals("")) {
            code = 0;
            Date date = new Date();//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date);//时间存储为字符串
            orderInformation orderInformation = new orderInformation();
            orderInformation.setOrder_id(order_id);
            orderInformation.setUser_id(user_id);
            orderInformation.setOrder_status("已收货");
            orderInformation.setOrder_confirm_time(Timestamp.valueOf(str));
            int updateApplyOrderRefund = orderInformationMapper.updateOrderStatus(orderInformation);//更新订单状态
            int orderSumPrice = orderInformationMapper.findOrderSumPriceByOrder_id(order_id).intValue();//获取订单的总价钱
            int userIntegral = userMapper.getUserIntegral(user_id);//获取当前用户的积分
            System.out.println("orderSumPrice："+orderSumPrice);
            System.out.println("userIntegral："+userIntegral);
            user user = new user();
            user.setUser_id(user_id);
            user.setUser_Integral(userIntegral+orderSumPrice);
            userMapper.updateUserIntegral(user);//更新用户的积分
            params.put("errmsg","成功修改订单状态为已收货");
        }else {
            code = 1;
            params.put("errmsg","修改订单状态为已收货失败");
        }
        return new ResultInfo<>(code,params);
    }
}
