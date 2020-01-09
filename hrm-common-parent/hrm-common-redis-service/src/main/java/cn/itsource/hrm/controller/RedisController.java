package cn.itsource.hrm.controller;

import cn.itsource.hrm.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/set")
    public void set(String key,String value){
        redisUtils.set(key,value);
    }

    /**
     *
     * @param key
     * @param value
     */
    @PostMapping("/setex")
    public void set(String key,String value,int time){
        Jedis source = redisUtils.getSource();
        source.setex(key,time,value);
        source.close();
    }

    @GetMapping("/get")
    public String get(String key){
        return redisUtils.get(key);
    }
}
