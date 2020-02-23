package com.hh.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hh.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /*1.类别模块接入redis做缓存

    2.购物车数据保存到redis(选做)

    3.预习创建订单接口(相对复杂的接口)*/

}
