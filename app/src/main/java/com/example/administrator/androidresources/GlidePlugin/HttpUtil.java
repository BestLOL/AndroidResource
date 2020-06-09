package com.example.administrator.androidresources.GlidePlugin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    //使用HttpUriConnection方式获取Bitmap对象
    public static Bitmap HttpConnection(String url){

        InputStream is = null;
        HttpURLConnection conn = null;
        Bitmap bitmap = null;
        try {
            URL imgUrl = new URL(url);
            conn = (HttpURLConnection) imgUrl.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn!=null){
                conn.disconnect();
            }
        }
        return bitmap;
    }

    //使用OkHttpClient请求，这个需要导包暂时不写


}
