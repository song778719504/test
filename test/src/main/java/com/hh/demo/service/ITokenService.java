package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;

public interface ITokenService {

    /**
     * 生成token
     */
    ServerResponse generateToken();


    /**
     * 校验token
     */
    ServerResponse checkToken(HttpServletRequest request);

}
