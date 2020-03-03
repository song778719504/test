package com.hh.demo.interceptor;

import com.google.gson.Gson;
import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginCheckInterceptor  implements HandlerInterceptor {


    /**
     *
     * 请求到达controller之前先通过preHandle
     * true:  代表可以通过本拦截器，到达目标controller
     * false:拦截请求，返回到前端；
     *
     * */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {


        HttpSession session=request.getSession();
        //step1:先判断用户是否登录
        User user=(User)session.getAttribute(Consts.USER);

        if(user!=null){//用户已经登录

            return true; //可以通过拦截器
        }


        //重写Response

        PrintWriter printWriter=null;

        try {
            response.reset();//重置
            response.addHeader("Content-Type","application/json;charset=utf-8");
            printWriter= response.getWriter();
            ServerResponse serverResponse=ServerResponse.serverResponseByFail(
                    StatusEnum.NO_LOGIN.getStatus(),
                    StatusEnum.NO_LOGIN.getMsg()
            );

            Gson gson=new Gson();
            String json=gson.toJson(serverResponse);

            printWriter.write(json);
            //将缓存区的数据清空，类似于刷新操作，防止直接close丢失缓存数据
            printWriter.flush();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(printWriter!=null){
                printWriter.close();
            }
        }


        return false;
    }

    /**
     *
     * 当controller往前端响应时，通过该拦截器的postHandle方法。
     * */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        System.out.println("===========postHandle====");
    }

    /**
     * 一次http完成后，调用afterCompletion方法
     * */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {

        System.out.println("===========afterCompletion====");
    }
}
