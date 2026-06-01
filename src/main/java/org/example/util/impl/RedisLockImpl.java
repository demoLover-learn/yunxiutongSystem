package org.example.util.impl;

import org.example.util.RedisLock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;



public class RedisLockImpl implements RedisLock {

    private String name;

    private StringRedisTemplate stringRedisTemplate;

    public RedisLockImpl(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String PREFIX_LOCK="lock:";
    //lua脚本的加载
    private static final DefaultRedisScript<Long> REDISSCRIPT_LOCK;
    static {
        REDISSCRIPT_LOCK=new DefaultRedisScript<>();
        REDISSCRIPT_LOCK.setLocation(new ClassPathResource("unlock.lua"));
        REDISSCRIPT_LOCK.setResultType(Long.class);
    }
    //获取锁
    @Override
    public boolean tryLock(Long timeout) {
        //获取当前线程id
        long currentId = Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(PREFIX_LOCK + name, currentId + "", timeout, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlock() {
        //释放锁
        stringRedisTemplate.execute(REDISSCRIPT_LOCK,
                Collections.singletonList(PREFIX_LOCK + name)
                ,Thread.currentThread().getId()+"");
    }
}
