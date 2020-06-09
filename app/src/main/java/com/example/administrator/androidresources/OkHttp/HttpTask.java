package com.example.administrator.androidresources.OkHttp;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

//请求封装类,要实现Delayed也就是重试机制就要实现Delayed这个接口
public class HttpTask<T> implements Runnable, Delayed {

    private HttpRequest request;

    public HttpTask(HttpRequest request,String url,T requestData,CallBackListener listener) {
        this.request = request;
        request.setUrl(url);
        request.setListener(listener);
        try {
            request.setData(JSON.toJSONString(requestData).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //这里执行失败之后
        try{
            request.execute();
        }catch (Exception e){
            //失败后加入延迟线程池
            ThreadPoolManager.getInstance().addHttpTask(this);
        }
    }

    /*重试机制需要完善的接口*/
    private long delayTime;//延迟时间
    private int retryCount;//重试次数

    private boolean repeat;//是否需要延迟

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis()+delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        //转换延迟时间为我们想要的时间
        return timeUnit.convert(this.delayTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }
}
