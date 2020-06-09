package com.example.administrator.androidresources.OkHttpAgain;

//请求类接口对象
public interface Request<T> {

    void setUrl(String url);//设置请求路径
    void setData(byte[] data);//设置需要传输的数据
    void execute();//请求执行
    void setCallBackListener(CallBackListener listener,Class<T> responseClass);//设置回调接口,返回类型
}
