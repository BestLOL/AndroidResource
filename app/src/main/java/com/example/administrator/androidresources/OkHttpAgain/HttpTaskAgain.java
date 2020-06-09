package com.example.administrator.androidresources.OkHttpAgain;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

//请求封装类
public class HttpTaskAgain<T,M> implements Runnable{

    private Request<T> request;

    /**
     *
     * @param url 访问的url
     * @param requestData 发送的请求对象数据，需要转换成JSON的byte数据
     * @param listener 回调接口
     * @param responseData 请求返回的数据类型
     */
    public HttpTaskAgain(String url,M requestData,CallBackListener listener,Class<T> responseData){
        request = new JsonHttpRequestAgain<>();
        request.setUrl(url);
        request.setCallBackListener(listener,responseData);
        try {
            request.setData(JSON.toJSONString(requestData).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        //执行请求
        try {
            request.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Request", "请求执行部分出现错误");
        }
    }
}
