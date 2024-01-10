package com.Controller;

import com.Pojo.orderInformation;
import com.github.pagehelper.PageInfo;
import com.service.impl.orderInformationServiceImpl;
import com.util.ResponseJSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
//@RequestMapping("/managerOrderInformationController")
public class orderInformationController {
    @Resource
    orderInformationServiceImpl orderInformationService;

    //管理员查看所有订单
    @RequestMapping("/findAllUserOrder")
    @ResponseBody
    public PageInfo<orderInformation> findAllUserOrder(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) throws IOException {
        PageInfo<orderInformation> allUserOrder = orderInformationService.findAllUserOrder(pageNum);
        return allUserOrder;
    }


    //获取订单一个订单的详情信息
    @RequestMapping("/getOrderInformation")
    @ResponseBody
    public ResponseJSONResult getOrderInformation(@RequestParam(value = "order_id", required = false) String order_id) throws IOException {
        return new ResponseJSONResult(200,orderInformationService.selectByOrderId(order_id));
    }

    //更新订单信息
    @RequestMapping(value = "/updateOrderInformation",method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONResult updateOrderInformation(@RequestParam(value = "orderInformation", required = false) String orderInformation,
                                         @RequestParam(value = "shipAddress", required = false) String shipAddress) throws IOException {
            ResponseJSONResult responseJSONResult = orderInformationService.updateOrderInformation(orderInformation, shipAddress);
        return responseJSONResult;
    }

    //删除订单信息（把删除标志从0改为1）
    @RequestMapping(value = "/deleteOrderInformation",method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONResult deleteOrderInformation(@RequestParam(value = "order_id", required = false) String order_id) throws IOException {
        ResponseJSONResult responseJSONResult = orderInformationService.deleteOrderInformation(order_id);
        return responseJSONResult;
    }

    //回归删除订单信息（把删除标志从1改为0）
    @RequestMapping(value = "/returnDeleteOrderInformation",method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONResult returnDeleteOrderInformation(@RequestParam(value = "order_id", required = false) String order_id) throws IOException {
        ResponseJSONResult responseJSONResult = orderInformationService.returnDeleteOrderInformation(order_id);
        return responseJSONResult;
    }

    //管理员搜索订单
    @RequestMapping("/SearchUserOrder")
    @ResponseBody
    public PageInfo<orderInformation> SearchUserOrder(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                                      @RequestParam(value = "order_id") String order_id,
                                                      @RequestParam(value = "user_id") String user_id) throws IOException {
        PageInfo<orderInformation> allUserOrder = orderInformationService.SearchUserOrder(pageNum,order_id,user_id);
        return allUserOrder;
    }

    //统计订单状态的数量
    @RequestMapping("/countOrderStatus")
    @ResponseBody
    public ResponseJSONResult countOrderStatus() throws IOException {
        ResponseJSONResult orderStatus = orderInformationService.countOrderStatus();
        return orderStatus;
    }

    //统计一些重要的信息
    @RequestMapping("/countInformation")
    @ResponseBody
    public ResponseJSONResult countInformation() throws IOException {
        ResponseJSONResult orderStatus = orderInformationService.countInformation();
        return orderStatus;
    }

    //获取所有的订单总价格和收货时间
    @RequestMapping("/getAllOrderSumPriceAndConfirmTime")
    @ResponseBody
    public ResponseJSONResult getAllOrderSumPriceAndConfirmTime(){
        ResponseJSONResult orderStatus = orderInformationService.getAllOrderSumPriceAndConfirmTime();
        return orderStatus;
    }

    //获取所有的订单总价格和收货时间
    @RequestMapping("/getOrderAccountAndConfirmTimeAndCommodityType")
    @ResponseBody
    public ResponseJSONResult getOrderAccountAndConfirmTimeAndCommodityType(){
        ResponseJSONResult orderStatus = orderInformationService.getOrderAccountAndConfirmTimeAndCommodityType();
        return orderStatus;
    }
}
