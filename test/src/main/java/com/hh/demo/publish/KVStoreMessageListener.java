package com.hh.demo.publish;

import redis.clients.jedis.JedisPubSub;

public class KVStoreMessageListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("  <<< listener-dingyue(SUBSCRIBE)< Channel:" + channel + " >jieshoude Message:" + message );
        System.out.println();
        //当接收到的message为quit时，取消订阅(被动方式)
        if(message.equalsIgnoreCase("quit")){
            this.unsubscribe(channel);
        }


    }

}
