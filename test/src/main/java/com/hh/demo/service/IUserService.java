package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.User;

public interface IUserService {

    //用户注册
    ServerResponse registerLogic(User user);

    //用户登录
    ServerResponse loginLogic(String username,String password);

}
