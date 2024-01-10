package com.Controller;

import com.Pojo.shipAddress;
import com.common.ResultInfo;
import com.service.commodityService;
import com.service.impl.orderInformationServiceImpl;
import com.service.impl.shipAddressServiceImpl;
import com.service.shipAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/orderInformationController")
public class orderInformationController {
    @Resource
    orderInformationServiceImpl orderInformationService;

    //订单查找用户地址
    @RequestMapping("/orderFindAddress")
    @ResponseBody
    public shipAddress orderFindAddress(String addressId, String openId) throws IOException {
        return orderInformationService.orderFindAddress(addressId,openId);
    }

    //订单查找已经选择购买的商品信息
    @RequestMapping("/orderFindCart")
    @ResponseBody
    public Map<String,Object> orderFindCart(String openId) throws IOException {
        return orderInformationService.orderFindCart(openId);
    }

    //查询直接购买商品信息
    @RequestMapping("/orderFindDirectGood")
    @ResponseBody
    Map<String,Object> orderFindDirectGood(String commodity_id, String attribute_id, String sonAttribute_id, int number) throws IOException {
        return orderInformationService.orderFindDirectGood(commodity_id,attribute_id,sonAttribute_id,number);
    }

    //用户提交订单
    @RequestMapping("/orderSubmit")
    @ResponseBody
    ResultInfo<Object> orderSubmit(
            @RequestParam(value = "checkedGoodsList",required = false) String checkedGoodsList,
            @RequestParam(value = "openId",required = false) String openId,
            @RequestParam(value = "addressId",required = false) String addressId,
            @RequestParam(value = "postscript",required = false) String postscript,
            @RequestParam(value = "freightPrice",required = false) Double freightPrice,
            @RequestParam(value = "goodsTotalPrice",required = false) Double goodsTotalPrice,
            @RequestParam(value = "actualPrice",required = false) Double actualPrice,
            @RequestParam(value = "goodsCount",required = false) Integer goodsCount,
            @RequestParam(value = "addType",required = false) Integer addType){
       return orderInformationService.orderSubmit(checkedGoodsList,openId,addressId,postscript,freightPrice,goodsTotalPrice,
                                                    actualPrice,goodsCount,addType);
    }

    //更新微信支付的信息
    @RequestMapping("/orderUpdatePayInformation")
    @ResponseBody
    ResultInfo<Object> orderUpdatePayInformation( @RequestParam(value = "openId",required = false) String openId,
                                                  @RequestParam(value = "orderId",required = false) String orderId){
        return orderInformationService.orderUpdatePayInformation(openId,orderId);
    }

    //用户查询所有订单
    @RequestMapping("/findAllOrder")
    @ResponseBody
    ResultInfo<Object> findAllOrder( @RequestParam(value = "openId",required = false) String openId,
                                      @RequestParam(value = "showType",required = false) Integer showType,
                                     HttpServletRequest request){
        System.out.println("绝对路径："+request.getSession().getServletContext().getRealPath("/"));
        return orderInformationService.findAllOrder(openId,showType);
    }

    //订单查找用户地址
    @RequestMapping("/orderByTypeNumber")
    @ResponseBody
    ResultInfo<Object> orderByTypeNumber( @RequestParam(value = "openId",required = false) String openId){
        return orderInformationService.orderByTypeNumber(openId);
    }

    //通过order_id查询该订单的所有信息
    @RequestMapping("/selectByOrderId")
    @ResponseBody
    ResultInfo<Object> selectByOrderId( @RequestParam(value = "order_id",required = false) String order_id){
        return orderInformationService.selectByOrderId(order_id);
    }

    //通过order_id查询该总订单的商品订单所有信息
    @RequestMapping("/findOrderGoodsByOrderId")
    @ResponseBody
    ResultInfo<Object> findOrderGoodsByOrderId( @RequestParam(value = "order_id",required = false) String order_id){
        return orderInformationService.findOrderGoodsByOrderId(order_id);
    }

    //用户取消或者删除订单
    @RequestMapping("/orderCancel")
    @ResponseBody
    ResultInfo<Object> orderCancel( @RequestParam(value = "order_id",required = false) String order_id,
                                    @RequestParam(value = "user_id",required = false) String user_id){
        return orderInformationService.orderCancel(order_id,user_id);
    }

    //修改总订单的备注信息
    @RequestMapping("/updateOrderRemark")
    @ResponseBody
    ResultInfo<Object> updateOrderRemark( @RequestParam(value = "order_id",required = false) String order_id,
                                          @RequestParam(value = "user_id",required = false) String user_id,
                                          @RequestParam(value = "order_remark",required = false) String order_remark){
        return orderInformationService.updateOrderRemark(order_id,user_id,order_remark);
    }

    //用户申请退款
    @RequestMapping("/updateApplyOrderRefund")
    @ResponseBody
    ResultInfo<Object> updateApplyOrderRefund( @RequestParam(value = "order_id",required = false) String order_id,
                                               @RequestParam(value = "user_id",required = false) String user_id,
                                               @RequestParam(value = "order_refund_reason",required = false) String order_refund_reason,
                                               @RequestParam(value = "order_refund_instructions",required = false) String order_refund_instructions,
                                               @RequestParam(value = "order_refund_image",required = false) String order_refund_image){
        return orderInformationService.updateApplyOrderRefund(order_id,user_id,order_refund_reason,order_refund_instructions,order_refund_image);
    }

    //修改订单状态为已收货
    @RequestMapping("/updateOrderStatusToArrive")
    @ResponseBody
    ResultInfo<Object> updateOrderStatusToArrive( @RequestParam(value = "order_id",required = false) String order_id,
                                               @RequestParam(value = "user_id",required = false) String user_id){
        return orderInformationService.updateOrderStatusToArrive(order_id,user_id);
    }
}
