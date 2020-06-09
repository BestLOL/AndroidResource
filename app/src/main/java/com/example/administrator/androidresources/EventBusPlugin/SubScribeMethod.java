package com.example.administrator.androidresources.EventBusPlugin;

import java.lang.reflect.Method;

public class SubScribeMethod {
    // 1 方法
    private Method method;
    // 2 参数
    private Class<?> clazz;
    // 3 SubScribe参数
    private ThreadModel threadModel;

    public SubScribeMethod(Method method, Class<?> clazz, ThreadModel threadModel) {
        this.method = method;
        this.clazz = clazz;
        this.threadModel = threadModel;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ThreadModel getThreadModel() {
        return threadModel;
    }

    public void setThreadModel(ThreadModel threadModel) {
        this.threadModel = threadModel;
    }
}
