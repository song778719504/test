package com.hh.demo.service.impl;

import com.hh.demo.utils.MD5Utils;
import com.hh.demo.common.RoleEnum;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.UserMapper;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {

    @Resource
    UserMapper userMapper;

    //用户注册
    @Override
    public ServerResponse registerLogic(User user) {

        //用户为空
        if (user == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg());
        }
        //用户名为空
        if (user.getUsername() == null || "".equals(user.getUsername())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.USERNAME_NOT_EMPTY.getStatus(),
                    StatusEnum.USERNAME_NOT_EMPTY.getMsg());
        }
        //用户密码为空
        if (user.getPassword() == null || "".equals(user.getPassword())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PASSWORD_NOT_EMPTY.getStatus(),
                    StatusEnum.PASSWORD_NOT_EMPTY.getMsg());
        }
        //手机号为空
        if (user.getPhone() == null || "".equals(user.getPhone())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PHONE_NOT_EMPTY.getStatus(),
                    StatusEnum.PHONE_NOT_EMPTY.getMsg());
        }
        //邮箱地址为空
        if (user.getEmail() == null || "".equals(user.getEmail())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.EMAIL_NOT_EMPTY.getStatus(),
                    StatusEnum.EMAIL_NOT_EMPTY.getMsg());
        }
        //密保问题为空
        if (user.getQuestion() == null || "".equals(user.getQuestion())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.QUESTION_NOT_EMPTY.getStatus(),
                    StatusEnum.QUESTION_NOT_EMPTY.getMsg());
        }
        //密保答案为空
        if (user.getAnswer() == null || "".equals(user.getAnswer())){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ANSWER_NOT_EMPTY.getStatus(),
                    StatusEnum.ANSWER_NOT_EMPTY.getMsg());
        }
        //用户名已存在
        if (userMapper.selectByUsername(user.getUsername()) != 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.USERNAME_EXISTS.getStatus(),
                    StatusEnum.USERNAME_EXISTS.getMsg());
        }
        //邮箱已存在
        if (userMapper.selectByEmail(user.getEmail()) != 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.EMAIL_EXISTS.getStatus(),
                    StatusEnum.EMAIL_EXISTS.getMsg());
        }

        //密码加密
        String password = MD5Utils.getMD5Code(user.getPassword());
        user.setPassword(password);

        //设置用户权限
        user.setRole(RoleEnum.USER.getStatus());

        //添加用户
        int count = userMapper.insert(user);
        if (count == 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.REGISTER_FAIL.getStatus(),
                    StatusEnum.REGISTER_FAIL.getMsg()
            );
        }
        return ServerResponse.serverResponseBySuccess("注册成功",null);
    }

    //用户登录
    @Override
    public ServerResponse loginLogic(String username, String password) {

        //用户名为空
        if (username == null || "".equals(username)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.USERNAME_NOT_EMPTY.getStatus(),
                    StatusEnum.USERNAME_NOT_EMPTY.getMsg()
            );
        }
        //密码为空
        if (password == null || "".equals(password)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PASSWORD_NOT_EMPTY.getStatus(),
                    StatusEnum.PASSWORD_NOT_EMPTY.getMsg()
            );
        }
        //用户名不存在
        if (userMapper.selectByUsername(username) == 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.USERNAME_NOT_EXISTS.getStatus(),
                    StatusEnum.USERNAME_NOT_EXISTS.getMsg()
            );
        }
        String _password = MD5Utils.getMD5Code(password);
        //用户名密码匹配
        User user = userMapper.selectByUsernameAndPassword(username,_password);
        if (user == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PASSWORD_WRONG.getStatus(),
                    StatusEnum.PASSWORD_WRONG.getMsg()
            );
        }

        return ServerResponse.serverResponseBySuccess(null,user);
    }

}
