package com.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class FileConfig {
    @Configuration
    @EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/photo/**")   //虚拟路径，即通过url访问时的路径
                    .addResourceLocations("/classes/static/photo/"); //配置图片存储的实际路径
            //如果要上传到服务器要改一下这里的路径 换成服务器的路径
            //在这里是可以找到target/classes/static/img这个路径
            //但是在服务器上我们一般只是运行jar文件没有这个目录所以他会找不到这个目录。
            //使用SpringBoot静态资源映射，将服务器上/root/volunteer/currversion/img/目录下的文件映射到SpringBoot的static/img/下的文件这样就可以访问的
        }
    }
}
