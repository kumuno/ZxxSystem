package com.common;

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
        registry.addResourceHandler("/images/**").addResourceLocations("file:/www/wwwroot/zxx/images/");
    }
}
