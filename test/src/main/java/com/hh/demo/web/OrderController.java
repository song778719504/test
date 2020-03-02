package com.hh.demo.web;


import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping("create.do")
    public ServerResponse create(HttpSession session,Integer shippingId){

        User user = (User)session.getAttribute(Consts.USER);


        return orderService.createOrder(user.getId(),shippingId);

    }

    @RequestMapping("cancel.do")
    public ServerResponse cancel(Long orderNo){


        return orderService.cancelOrder(orderNo);

    }

}
