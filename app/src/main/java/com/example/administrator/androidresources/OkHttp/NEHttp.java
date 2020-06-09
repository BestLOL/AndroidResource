package com.example.administrator.androidresources.OkHttp;

//将请求放入线程池，连接ThreadPoolManager 与JsonHttpRequest类
public class NEHttp {

    public static<M,T> void sendJsonRequest(Class<M> responseClass, String url, T requestData, JsonDataTransformListener transFormListener) {
        CallBackListener listener = new JsonCallBackListener<>(responseClass,transFormListener);
        HttpRequest request = new JsonHttpRequest();
        HttpTask ht = new HttpTask(request,url,request,listener);
        ThreadPoolManager.getInstance().addTask(ht);
    }
}
