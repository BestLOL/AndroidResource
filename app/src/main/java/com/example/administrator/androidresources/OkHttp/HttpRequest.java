package com.example.administrator.androidresources.OkHttp;

//网络请求功能接口
public interface HttpRequest {
    void setUrl(String url); //设置路径
    void setData(byte[] data); //设置需要的数据
    void setListener(CallBackListener listener); //设置返回接口
    void execute();//执行请求
}

