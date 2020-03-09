package com.hh.demo.alipay;

import org.springframework.stereotype.Service;

public class TestDo implements TestDoService {
    @Override
    public void test() {
        System.out.println("执行中。。。");
    }
}
