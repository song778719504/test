package com.hh.demo.web;


import com.hh.demo.common.ServerResponse;
import com.hh.demo.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token/")
public class TokenController {

    @Autowired
    ITokenService tokenService;



    @RequestMapping("getToken")
    public ServerResponse generateToken(){

        return tokenService.generateToken();
    }


}
