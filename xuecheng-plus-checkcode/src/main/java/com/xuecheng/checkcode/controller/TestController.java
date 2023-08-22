package com.xuecheng.checkcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class TestController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/{key}")
    public String getValueByKey(@PathVariable String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
