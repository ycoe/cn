package com.duoec.web.cn.core.common.cache.impl;

import com.duoec.web.core.freemarker.service.ICache;
import com.duoec.web.core.freemarker.service.IDataFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ycoe
 * @date 16/12/22
 */
@Service
public class RedisCache implements ICache {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> T get(String key, IDataFetch<T> dataFetch, int second) {
        ValueOperations ops = redisTemplate.opsForValue();
        T data = (T) ops.get(key);
        if(data == null) {
            T dataObject = dataFetch.fetch();
            ops.set(key, dataObject, second, TimeUnit.SECONDS);
            return dataObject;
        }
        return (T) data;
    }

    @Override
    public <T> T get(String key, IDataFetch<T> dataFetch) {
        return get(key, dataFetch, 60 * 60);
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> void set(String key, T data) {
        set(key, data, 60 * 60);
    }

    @Override
    public <T> void set(String key, T data, int second) {
        redisTemplate.opsForValue().set(key, data, second, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String id) {
        redisTemplate.opsForValue().getOperations().delete(id);
    }

    public void deleteAll(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        RedisOperations ops = redisTemplate.opsForValue().getOperations();
        for (String key : keys) {
            ops.delete(key);
        }
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
