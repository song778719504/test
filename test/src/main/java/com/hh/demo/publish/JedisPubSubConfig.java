package com.hh.demo.publish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPubSub;

@Configuration
public class JedisPubSubConfig {

    @Bean
    public JedisPubSub jedisPubSub(){

        return new KVStoreMessageListener();
    }
}
