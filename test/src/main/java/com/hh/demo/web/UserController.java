package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    IUserService userService;

    //注册
    @RequestMapping("register.do")
    public ServerResponse register(User user){
        return userService.registerLogic(user);
    }


    //登录
    @RequestMapping("login.do")
    public ServerResponse login(String username, String password, HttpSession session){

        ServerResponse sr = userService.loginLogic(username,password);

        if (sr.isSuccess()) {
            session.setAttribute(Consts.USER,sr.getData());
        }
        return sr;
    }


    //登出
    @RequestMapping("logout.do")
    public ServerResponse logout(HttpSession session){
        session.setAttribute(Consts.USER,null);
        return ServerResponse.serverResponseBySuccess(null,null);
    }

}
