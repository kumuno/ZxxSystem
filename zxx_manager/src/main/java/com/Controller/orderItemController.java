package com.Controller;

import com.Pojo.orderItem;
import com.service.orderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/managerOrderItemController")
public class orderItemController {
    @Resource
    private orderItemService orderItemService;
    //    跳转订单详情页面

    @RequestMapping("/OrderDetails")
    public String OrderDetails(@RequestParam(value = "order_id", required = false) String order_id,
                               HttpSession session, HttpServletRequest request) {
        System.out.println("========================Controller层，跳转订单详情页面=================");
        return "Order_details";
    }
}
