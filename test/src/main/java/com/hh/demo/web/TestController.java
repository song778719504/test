package com.hh.demo.web;

import com.hh.demo.dao.CartMapper;
import com.hh.demo.entity.Cart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    CartMapper cartMapper;

    @RequestMapping("/cart/{id}")
    public Cart findById(@PathVariable("id") Integer id){
        return cartMapper.selectByPrimaryKey(id);
    }

}
