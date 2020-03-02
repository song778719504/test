package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.ICartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/cart/")
public class CartController {

    @Resource
    ICartService cartService;

    /**
     * 查询购物车列表
     */

    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){
        //登录判断
        User user = (User)session.getAttribute(Consts.USER);

        return cartService.list(user.getId());
    }


    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,Integer count,HttpSession session){

        //登录判断
        User user = (User)session.getAttribute(Consts.USER);

        return cartService.add(user.getId(),productId,count);
    }


    @RequestMapping("delete_product.do")
    public ServerResponse delete(HttpSession session,String productIds){

        //登录判断
        User user = (User)session.getAttribute(Consts.USER);

        return cartService.delete(user.getId(),productIds );

    }

}
