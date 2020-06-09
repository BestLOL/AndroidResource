package com.example.administrator.androidresources.OkHttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池，负责将添加线程入线程池，线程池只有一个，所以使用单例模式
public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    //添加请求线程到队列中
    public void addTask(Runnable runnable){
        if(runnable!=null){
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                //线程执行失败的返回接口
                addTask(runnable);
            }
        });
        threadPoolExecutor.execute(coreRunnable);
        threadPoolExecutor.execute(delayRunnable);
    }

    //创建一个核心线程，负责将Reuqest网络请求的需求添加到线程池中
    private Runnable coreRunnable = new Runnable() {
        Runnable runn = null;
        @Override
        public void run() {
            while(true){
                try {
                    runn = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadPoolExecutor.execute(runn);
            }
        }
    };

    //重试机制的完成
    private DelayQueue<HttpTask> delayQueue = new DelayQueue<>();
    public void addHttpTask(HttpTask task){
        if(task!=null){
            task.setDelayTime(3000);
            delayQueue.offer(task);
        }
    }

    //创建一个延迟线程
    private Runnable delayRunnable = new Runnable() {
        HttpTask ht = null;
        @Override
        public void run() {
            while(true){
                try {
                    ht = delayQueue.take();
                    if(ht.isRepeat()){
                        //判断这个需求是否要重试

                    }

                    if(ht.getRetryCount() < 3){
                        threadPoolExecutor.execute(ht);
                        ht.setRetryCount(ht.getRetryCount()+1);
                        Log.e("===重试机制====", ht.getRetryCount()+" "+System.currentTimeMillis());
                    }else{
                        Log.e("===重试机制====", "重试请求太多");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
