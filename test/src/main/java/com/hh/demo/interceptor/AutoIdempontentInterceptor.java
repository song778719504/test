package com.hh.demo.interceptor;

import com.google.gson.Gson;
import com.hh.demo.annotation.AutoIdempontent;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class AutoIdempontentInterceptor implements HandlerInterceptor {

    @Autowired
    ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AutoIdempontent autoIdempontent = method.getAnnotation(AutoIdempontent.class);
        if (autoIdempontent != null){
            //该方法上含有AutoIdempontent注解

            //校验token
            ServerResponse serverResponse = tokenService.checkToken(request);
            if (!serverResponse.isSuccess()){
                //重写Response
                PrintWriter printWriter=null;
                try {
                    response.reset();//重置
                    response.addHeader("Content-Type","application/json;charset=utf-8");
                    printWriter= response.getWriter();
                    Gson gson=new Gson();
                    String json=gson.toJson(serverResponse);
                    printWriter.write(json);
                    //将缓存区的数据清空，类似于刷新操作，防止直接close丢失缓存数据
                    printWriter.flush();
                    printWriter.close();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(printWriter!=null){
                        printWriter.close();
                    }
                }
            }
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
