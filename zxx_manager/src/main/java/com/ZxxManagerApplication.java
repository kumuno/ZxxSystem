package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.mapper")
@SpringBootApplication
public class ZxxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxxManagerApplication.class, args);
//        System.out.println(ZxxManagerApplication.class.getResource("/"));
    }

}
//public class ZxxManagerApplication extends SpringBootServletInitializer {
//
//    public static void main(String[] args) {
//        SpringApplication.run(ZxxManagerApplication.class, args);
////        System.out.println(ZxxManagerApplication.class.getResource("/"));
//    }
//
//    //继承SpringBootServletInitializer，起到web.xml作用
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里要指向原先用main方法执行的Application启动类
//        return builder.sources(ZxxManagerApplication.class);
//    }
//
//}
