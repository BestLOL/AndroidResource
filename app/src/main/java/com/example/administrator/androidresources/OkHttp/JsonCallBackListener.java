package com.example.administrator.androidresources.OkHttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//数据返回监听类
public class JsonCallBackListener<T> implements CallBackListener{

    //要转换成的数据类型
    private Class<T> responseClass;
    //转换完毕之后，传递出去
    private JsonDataTransformListener listener;

    //传到主线程
    private Handler handler = new Handler(Looper.getMainLooper());


    public JsonCallBackListener(Class<T> responseClass, JsonDataTransformListener listener) {
        this.responseClass = responseClass;
        this.listener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //字节流转为具体内容
        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response,responseClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                //将转换完毕的对象传到主线程
                listener.onSuccess(clazz);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine())!=null){
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", "Error="+e.getMessage());
            System.out.println("Error="+e.getMessage());
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error="+e.getMessage());
            }
            content = sb.toString();
            return content;
        }
    }


    @Override
    public void onFailed() {

    }
}
