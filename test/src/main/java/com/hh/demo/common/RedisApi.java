package com.hh.demo.common;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * 封装redis中字符串、哈希、列表、集合、有序集合api
 */

@Component
public class RedisApi {

    @Resource
    private JedisPool jedisPool;

    public String set(String key,String value){
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key,value);
        jedis.close();
        return result;
    }

    public String get(String key){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public Long setNx(String key,String value){

        Jedis jedis = jedisPool.getResource();
        Long result = jedis.setnx(key,value);
        jedis.close();
        return result;

    }

    public String getSet(String key,String value){
        Jedis jedis = jedisPool.getResource();
        String result = jedis.getSet(key,value);
        jedis.close();
        return result;
    }

    public Long ttl(String key){
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    public Long expire(String key,Integer seconds){
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key,seconds);
        jedis.close();
        return result;
    }

    public String setEx(String key,Integer seconds,String value){

        Jedis jedis = jedisPool.getResource();
        String result = jedis.setex(key,seconds,value);
        jedis.close();
        return result;

    }



}
