package com.hh.demo.common;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

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


    /**
     * 哈希结构-api封装
     * 批量设置key，field，value
     */

    public Long hset(String key, String field,String value){

        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key,field,value);
        jedis.close();
        return result;
    }

    public String hget(String key,String field){
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key,field);
        jedis.close();
        return result;
    }

    public String hmset(String key,Map<String,String> map){
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hmset(key,map);
        jedis.close();
        return result;
    }

    public Long hdel(String key,String... fields){
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key,fields);
        jedis.close();
        return result;
    }

    public boolean hexists(String key,String field){
        Jedis jedis = jedisPool.getResource();
        boolean result = jedis.hexists(key,field);
        jedis.close();
        return result;
    }

    public Map<String,String> hgetAll(String key){
        Jedis jedis = jedisPool.getResource();
        Map<String,String> result = jedis.hgetAll(key);
        jedis.close();
        return result;
    }

    public Set<String> hkeys(String key){
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.hkeys(key);
        jedis.close();
        return result;
    }

    public Long hincrBy(String key,String field,Long value){
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hincrBy(key,field,value);
        jedis.close();
        return result;
    }




}
