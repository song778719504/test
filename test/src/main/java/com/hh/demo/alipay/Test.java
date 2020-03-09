package com.hh.demo.alipay;

public class Test {

    public static void main(String[] args) {

        TestDo testDo = new TestDo();
        JVMDynamicAgency test = new JVMDynamicAgency();
        TestDoService testDoService = (TestDoService) test.proxy(testDo);

        testDoService.test();

    }

}
