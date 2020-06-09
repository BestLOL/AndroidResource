package com.example.administrator.androidresources.OkHttp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements HttpRequest{

    private String url;
    private byte[] data;
    private CallBackListener listener;
    private HttpURLConnection connection = null;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener listener) {
        this.listener = listener;
    }

    @Override
    public void execute() {
        //网络请求部分的代码实现
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
            connection.connect();//连接,从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接

            //使用字节流发送数据
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
            bos.write(data);//写入缓冲区
            bos.flush();//刷新缓冲区，发送数据
            bos.close();

            //字符流写入数据
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = connection.getInputStream();


                listener.onSuccess(in);
            }else{
                throw new RuntimeException("请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }

    }

}
