package com.Config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class webMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        //设置页面注册
        registry.addViewController("/IIIIII").setViewName("/IIIIII");

        registry.addViewController("LoginBoss").setViewName("LoginBoss");
        registry.addViewController("Login").setViewName("Login");
        registry.addViewController("Index").setViewName("Index");
        registry.addViewController("CommodityList").setViewName("CommodityList");
        registry.addViewController("Orders").setViewName("Orders");
        registry.addViewController("Address").setViewName("Address");
        registry.addViewController("Delivery").setViewName("Delivery");
        registry.addViewController("Coupons").setViewName("Coupons");
        registry.addViewController("HireUser_management").setViewName("HireUser_management");
        registry.addViewController("Mine").setViewName("Mine");
        registry.addViewController("Member_info").setViewName("Member_info");
        registry.addViewController("Modify_Info").setViewName("Modify_Info");
        registry.addViewController("sign-in").setViewName("sign-in");
        registry.addViewController("Modify_hireInfo").setViewName("Modify_hireInfo");
        registry.addViewController("Modify_orders").setViewName("Modify_orders");
        registry.addViewController("Modify_goodslist").setViewName("Modify_goodslist");
        registry.addViewController("Modify_member").setViewName("Modify_member");
        registry.addViewController("Modify_coupons").setViewName("Modify_coupons");
        registry.addViewController("Modify_commodity").setViewName("Modify_commodity");
        registry.addViewController("managerCommodityController/Commodity_details").setViewName("Commodity_details");
//        registry.addViewController("/commodityAttributeController/addCommodityAttribute").setViewName("/Commodity_details");
        registry.addViewController("managerCommodityController/Modify_commodityAttribute").setViewName("Modify_commodityAttribute");
        registry.addViewController("managerCommodityController/Modify_commoditySonAttribute").setViewName("Modify_commoditySonAttribute");
        registry.addViewController("managerCommodityController/Modify_commodity").setViewName("Modify_commodity");
        registry.addViewController("InternalUser_management").setViewName("InternalUser_management");
        registry.addViewController("User_management").setViewName("User_management");
        registry.addViewController("Modify_user").setViewName("Modify_user");
        registry.addViewController("Modify_internalUser").setViewName("Modify_internalUser");
        registry.addViewController("HireUser_management").setViewName("HireUser_management");
        registry.addViewController("Boss_management").setViewName("Boss_management");
        registry.addViewController("BossController/Index").setViewName("Index");
        registry.addViewController("Order_details").setViewName("Order_details");
    }

////    指定静态资源路径
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/photo/**").addResourceLocations("file:E:/photo/");
//        registry.addResourceHandler("/photo/**")   //虚拟路径，即通过url访问时的路径
//                .addResourceLocations("/target/classes/static/photo"); //配置图片存储的实际路径
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
        String path = jarF.getParentFile().toString()+"/photo/";
        registry.addResourceHandler("/photo/**").addResourceLocations("file:"+path);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
