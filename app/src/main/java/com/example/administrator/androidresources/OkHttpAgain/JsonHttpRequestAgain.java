package com.example.administrator.androidresources.OkHttpAgain;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

//Http请求类
public class JsonHttpRequestAgain<T> implements Request<T>{

    private String url;
    private byte[] data;
    private CallBackListener listener;
    private Class<T> responseClass;

    private HttpURLConnection connection;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void execute() {
        //具体的请求执行方法
        OutputStream outputStream = null;
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(6000);//连接超时时间
            connection.setUseCaches(false);//是否使用缓存
            connection.setInstanceFollowRedirects(true);//成员函数，仅作用于当前函数，设置这个连接是否可以被重定向
            connection.setReadTimeout(3000);//响应超时时间
            connection.setDoOutput(true);//设置这个连接是否可以输出数据
            connection.setDoInput(true);//设置这个链接是否可以写入数据
            connection.setRequestMethod("POST");//设置请求方式
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");//设置消息的类型

            //连接建立完毕
            connection.connect();//连接,从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接

            //使用字节流发送数据
            outputStream = connection.getOutputStream();
            outputStream.write(this.data);
            outputStream.flush();

            //字符流写入数据
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = connection.getInputStream();
                String response = getContent(in);

                //JSON解析成固定Class对象
                final T clazz = JSON.parseObject(response,responseClass);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(clazz);
                    }
                });
            }else{
                throw new RuntimeException("请求失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String getContent(InputStream inputStream){
        StringBuilder builder = new StringBuilder();
        String line = null;
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            while((line = bufferedReader.readLine())!=null){
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", "Error="+e.getMessage());
            System.out.println("Error="+e.getMessage());
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "Error="+e.getMessage());
                System.out.println("Error="+e.getMessage());
            }

            return builder.toString();
        }
    }


    @Override
    public void setCallBackListener(CallBackListener listener, Class<T> responseClass) {
        this.listener = listener;
        this.responseClass = responseClass;
    }

}
