package com.example.administrator.androidresources.OkHttpAgain;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池处理类
public class ThreadPoolManagerAgain {

    //处理类单例模式
    private static class ThreadPoolGet{
        private static final ThreadPoolManagerAgain instance = new ThreadPoolManagerAgain();
    }
    public static ThreadPoolManagerAgain getInstance(){
        return ThreadPoolGet.instance;
    }

    //向线程池中添加请求
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    public void addTask(Runnable task){
        if(task!=null){
            try {
                //利用阻塞方式插入
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    private ThreadPoolExecutor poolExecutor;
    private ThreadPoolManagerAgain(){
        poolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //线程执行失败的返回接口
                addTask(r);
            }
        });

        poolExecutor.execute(coreRunnable);
    }

    //创建一个核心线程池，负责将Request的网络请求添加到线程池中
    private Runnable coreRunnable = new Runnable() {
        Runnable runnable = null;
        @Override
        public void run() {
            while (true){
                try {
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                poolExecutor.execute(runnable);
            }
        }
    };


}
