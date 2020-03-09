package com.hh.demo.alipay;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JVMDynamicAgency implements InvocationHandler {

    private  Object target;

    public  Object proxy(Object target){
        this.target = target;
        Object o = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("=======方法执行前=======");
        Object o = method.invoke(target,args);
        System.out.println("=======方法执行后=======");

        return o;
    }
}

