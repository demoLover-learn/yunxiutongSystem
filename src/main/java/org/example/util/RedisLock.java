package org.example.util;


import lombok.Data;


public interface RedisLock {



    //获取锁
   boolean tryLock(Long timeout);

   //释放锁
    void unlock();
}
