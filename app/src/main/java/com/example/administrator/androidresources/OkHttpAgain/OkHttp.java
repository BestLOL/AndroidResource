package com.example.administrator.androidresources.OkHttpAgain;

//线程池与请求的结合类
public class OkHttp {

    public static<T,M> void sendRequest(String url,M requestData,Class<T> responseClass,CallBackListener listener){
        HttpTaskAgain<T,M> task = new HttpTaskAgain<>(url,requestData,listener,responseClass);
        ThreadPoolManagerAgain.getInstance().addTask(task);
    }
}
