package com.nz.test.controller;

import com.nz.test.utils.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author zhengja@dist.com.cn
 * @data 2019/6/17 17:28
 */
@RestController
@RequestMapping(value = "rest/redis/util")
public class RedisUtilTestController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/v1/redisSave", method = {RequestMethod.GET})
    public Object redisSave(@RequestParam String key,
                            @RequestParam String value) {
        redisUtil.set(key, value);
        return redisUtil.get(key);
    }

    @RequestMapping(value = "/v1/redisGetAllKeyValue", method = {RequestMethod.GET})
    public Object redisGetAllKeyValue() {
        Map<String, Object> map = new HashMap<>();
        Set<String> keys = redisUtil.keys("*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object o = redisUtil.get(key);
            map.put(key, o);
        }
        return map;
    }

    @RequestMapping(value = "/v1/deleteRedisAll", method = {RequestMethod.GET})
    public Object deleteRedisAll() {
        Set<String> keys = redisUtil.keys("*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            System.out.println("#### "+iterator.next().toString());
            redisUtil.del(iterator.next());
        }
        return "删除redis所有数据成功";
    }

    @RequestMapping(value = "/v1/getRedisValue", method = {RequestMethod.GET})
    public Object getRedisValue(@RequestParam String key) {
        return redisUtil.get(key);
    }

}
