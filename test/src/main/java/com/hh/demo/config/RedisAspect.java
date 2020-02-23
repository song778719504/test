package com.hh.demo.config;

import com.google.gson.Gson;
import com.hh.demo.common.RedisApi;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.utils.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisAspect {

    @Autowired
    RedisApi redisApi;

    //方法的访问修饰符 返回值类型 包名类名方法名(参数列表) 任意用..表示
    @Pointcut("execution(public * com.hh.demo.service.impl.CategoryService.getCategoriesByParentId(..))")
    public void pointCut(){}

/*
    *//**
     * 前置通知
     *//*
    @Before("pointCut()")
    public void before(){
        System.out.println(2 + ":before");
    }


    *//**
     * 后置通知
     *//*
    @After("pointCut()")
    public void after(){
        System.out.println(5 + ":after");
    }


    *//**
     * 返回后通知
     *//*
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println(6 + ":afterReturning");
    }


    *//**
     * 抛出异常后通知
     *//*
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("n:afterThrowing");
    }*/


    /**
     * 环绕通知
     * 可以代替其他四个方法
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){

        StringBuffer stringBuffer = new StringBuffer();

        //所属类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
        stringBuffer.append(className);

        //得到方法名
        String name = joinPoint.getSignature().getName();
        stringBuffer.append(name);

        //得到方法的参数
        Object[] args = joinPoint.getArgs();
        for(Object o :args){
            stringBuffer.append(o);
        }

        String cacheKey = MD5Utils.getMD5Code(stringBuffer.toString());

        String cacheValue = redisApi.get(cacheKey);
        Gson gson = new Gson();
        if (cacheValue == null){
            //读db
            try {
                Object value = joinPoint.proceed();
                //将数据转json
                String valueStr = gson.toJson(value);
                //写入缓存
                redisApi.set(cacheKey,valueStr);
                return value;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }else {//缓存中有数据
            System.out.println("ok");

            ServerResponse serverResponse = gson.fromJson(cacheValue, ServerResponse.class);
            return serverResponse;
        }

        return null;
    }

}
