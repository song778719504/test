package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.Shipping;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.IShippingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Resource
    IShippingService shippingService;
    @RequestMapping("add.do")
    public ServerResponse add(Shipping shipping, HttpSession session){



        User user=(User) session.getAttribute(Consts.USER);


        shipping.setUserId(user.getId());

        return shippingService.add(shipping);
    }

}
