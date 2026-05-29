package org.example.context;

public class BaseContext {

//小抽屉，是一个用ThreadLocal实现的（线程本地储存）工具类，专门用来给当前请求线程存用户id
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();


    public static void setCurrentId(Long id) {
        THREAD_LOCAL.set(id);
    }

    public static Long getCurrentId() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrentId() {
        THREAD_LOCAL.remove();
    }
}
