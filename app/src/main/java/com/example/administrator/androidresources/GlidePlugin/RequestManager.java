package com.example.administrator.androidresources.GlidePlugin;

import java.util.concurrent.LinkedBlockingDeque;

public class RequestManager {

    private static RequestManager instance = new RequestManager();
    private LinkedBlockingDeque<BitMapRequest> queue = new LinkedBlockingDeque<>();
    private RequestDispatcher[] requestDispatchers;

    public RequestManager() {
        start();
    }

    public static RequestManager getInstance(){
        return instance;
    }

    //开始处理请求
    public void start(){
        //强制停止
        stop();
        //重新开始
        processRequest();
    }

    private void stop() {
        if(requestDispatchers!=null && requestDispatchers.length>0){
            for(RequestDispatcher dispatcher:requestDispatchers){
                if(!dispatcher.isInterrupted()){
                    dispatcher.isInterrupted();
                }
            }
        }
    }

    //处理请求
    private void processRequest() {
        //首先获取手机中最大线程数
        int count = Runtime.getRuntime().availableProcessors();
        requestDispatchers = new RequestDispatcher[count];
        for(int i=0;i<count;i++){
            RequestDispatcher dispatcher = new RequestDispatcher(queue);
            dispatcher.start();
            requestDispatchers[i] = dispatcher;
        }
    }

    //添加请求
    public void addBitMapRequest(BitMapRequest request){
        if(request==null){
            return;
        }
        if(!queue.contains(request))
            queue.add(request);
    }



}
