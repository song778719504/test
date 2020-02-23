package com.hh.demo.publish;

import com.hh.demo.common.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

@Component
public class KVStoreSubClient extends Thread {


    @Autowired
    RedisApi redisApi;

    @Value("${redis.kvstore}")
    private  String channel;

    @Autowired
    private JedisPubSub listener;


    private  void subscribe(){
        if(listener==null || channel==null){
            System.err.println("Error:SubClient> listener or channel is null");
        }
        System.out.println("  >>> dingyue(SUBSCRIBE) > Channel:"+channel);
        System.out.println();
        //接收者在侦听订阅的消息时，将会阻塞进程，直至接收到quit消息（被动方式），或主动取消订阅
        redisApi.subscribe(listener, channel);
    }

    @Override
    public void run() {

        subscribe();
    }
}
