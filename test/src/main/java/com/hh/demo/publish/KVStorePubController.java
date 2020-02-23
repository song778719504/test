package com.hh.demo.publish;

import com.hh.demo.common.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class KVStorePubController {


    @Autowired
    RedisApi redisApi;

    @Value("${redis.kvstore}")
    private  String channel;



    @RequestMapping("/pub")
    public String  pub(String msg){


        //消息发送者继续发消息
        for (int i = 0; i < 5; i++) {
            String message=UUID.randomUUID().toString();
            redisApi.pub(channel, message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }

    @Autowired
    KVStoreSubClient kvStoreSubClient;
    @RequestMapping("/sub")
    public String sub(){
        kvStoreSubClient.start();
        return "订阅成功-success";
    }


}
