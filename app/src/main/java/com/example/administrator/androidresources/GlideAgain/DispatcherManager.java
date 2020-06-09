package com.example.administrator.androidresources.GlideAgain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DispatcherManager {

    public static DispatcherManager getInstance(){
        return DispatcherManagerGet.dispatcherManager;
    }
    private static class DispatcherManagerGet{
        private static final DispatcherManager dispatcherManager = new DispatcherManager();
    }

    private LinkedBlockingQueue<BitMapRequestAgain> queue = new LinkedBlockingQueue<>();
    private ExecutorService service = Executors.newFixedThreadPool(5);

    public DispatcherManager(){
        start();
    }

    private void start() {
        service.execute(new Runnable() {
            BitMapRequestAgain requestAgain;
            @Override
            public void run() {
                while(true){
                    try {
                        requestAgain = queue.take();
                        RequestDispatcherAgain dispatcher = new RequestDispatcherAgain(requestAgain);
                        service.execute(dispatcher);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    //添加请求
    public void addDispatcher(BitMapRequestAgain request){
        if(request==null){
            return;
        }
        if(!queue.contains(request)){
            try {
                queue.put(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
