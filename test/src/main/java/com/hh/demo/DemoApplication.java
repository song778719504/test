package com.hh.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hh.demo.dao")
@EnableScheduling//开启定时任务
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /*1.类别模块接入redis做缓存

    2.购物车数据保存到redis(选做)

    3.预习创建订单接口(相对复杂的接口)*/

}
