package com.melo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangxin
 * @date 2025-04-26 12:50
 */
@Service
public class RedisService {




    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 设置数据
    public void setData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);  // 使用 opsForValue() 来操作 String 类型数据
    }

    // 获取数据
    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除数据
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    // 将数据推送到 List 中
    public void pushToList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);  // 左推入数据
    }

    // 从 List 中弹出数据
    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);  // 左弹出数据
    }

    // 获取 List 中的所有数据
    public List<Object> getAllFromList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);  // 获取列表所有元素
    }



}
