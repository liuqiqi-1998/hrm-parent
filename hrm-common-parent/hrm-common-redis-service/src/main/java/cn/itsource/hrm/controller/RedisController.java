package cn.itsource.hrm.controller;

import cn.itsource.hrm.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/set")
    public void set(String key,String value){
        redisUtils.set(key,value);
    }

    @GetMapping("/get")
    public String get(String key){
        return redisUtils.get(key);
    }
}
