package com.hh.demo.service.impl;

import com.hh.demo.common.Consts;
import com.hh.demo.common.RedisApi;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    @Autowired
    RedisApi redisApi;
    @Value("${token.timeout}")
    Integer tokenTimeout;

    @Override
    public ServerResponse generateToken() {

        //生成token
        String uuid = UUID.randomUUID().toString();
        StringBuffer buffer = new StringBuffer();
        buffer.append(Consts.TOKEN_PREFIX).append(uuid);


        //保存到redis
        redisApi.setEx(buffer.toString(),tokenTimeout,buffer.toString());

        if (buffer.toString() != null && !"".equals(buffer.toString())){
            return ServerResponse.serverResponseBySuccess(null,buffer.toString());
        }

        return ServerResponse.serverResponseByFail(
                StatusEnum.TOKEN_GENERATE_FAIL.getStatus(),
                StatusEnum.TOKEN_GENERATE_FAIL.getMsg()
        );
    }

    @Override
    public ServerResponse checkToken(HttpServletRequest request) {

        //step1:先从请求头中获得token
        String token = request.getHeader(Consts.TOKEN_NAME);
        if (token == null || "".equals(token)){
            //step2:获取token
            token = request.getParameter(Consts.TOKEN_NAME);
        }

        if (token == null || "".equals(token)){
            //没有携带token
            return ServerResponse.serverResponseByFail(
                    StatusEnum.TOKEN_EMPTY.getStatus(),
                    StatusEnum.TOKEN_EMPTY.getMsg()
            );
        }

        //step3:校验token是否存在
        String value = redisApi.get(token);
        if (value == null || "".equals(value)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NOT_REPEATABLE.getStatus(),
                    StatusEnum.NOT_REPEATABLE.getMsg()
            );
        }

        //step4:token有效，本次请求可以通过拦截器
        Long result = redisApi.del(token);
        if (request == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NOT_REPEATABLE.getStatus(),
                    StatusEnum.NOT_REPEATABLE.getMsg()
            );
        }

        return ServerResponse.serverResponseBySuccess(null,null);
    }

}
