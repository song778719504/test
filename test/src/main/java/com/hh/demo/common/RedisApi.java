package com.hh.demo.common;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.util.List;
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

    public Long del(String key){

        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;

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

    public String hset(String key, Map<String,String> map){

        Jedis jedis = jedisPool.getResource();
        String result = jedis.hmset(key,map);
        jedis.close();
        return result;
    }

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

    public Map<String,String>  hgetAll(String key){
        Jedis jedis=jedisPool.getResource();
        Map<String,String> result=jedis.hgetAll(key);
        jedis.close();
        return result;
    }

    public Set<String>  hgetAllField(String key){
        Jedis jedis=jedisPool.getResource();
        Set<String> result=jedis.hkeys(key);
        jedis.close();
        return result;
    }

    public List<String> hgetAllVals(String key){
        Jedis jedis=jedisPool.getResource();
        List<String> result=jedis.hvals(key);
        jedis.close();
        return result;
    }

    public Long  hgetAllVals(String key,String field,Long incr){
        Jedis jedis=jedisPool.getResource();
        Long result=jedis.hincrBy(key,field,incr);
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


    /**
     * 发布消息
     * */

    public void pub(String channel,String message){

        Jedis jedis= jedisPool.getResource();
        System.out.println("  >>> fabu(PUBLISH) > Channel:"+channel+" > fa chu de Message:"+message);
        jedis.publish(channel, message);
        jedis.close();
    }

    /**
     * 订阅消息
     * */

    public void subscribe(JedisPubSub listener, String channel){
        Jedis jedis= jedisPool.getResource();
        jedis.subscribe(listener,channel);
        jedis.close();
    }

    /**
     * 取消订阅消息
     * */

    public void unsubscribe(JedisPubSub listener,String channel){
        Jedis jedis= jedisPool.getResource();
        System.out.println("  >>> qu xiao ding yue(UNSUBSCRIBE) > Channel:"+channel);
        listener.unsubscribe(channel);
        jedis.close();
    }


}
