package com.example.administrator.androidresources.GlideAgain;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.administrator.androidresources.GlidePlugin.HttpUtil;

//图片请求具体封装实现类
public class RequestDispatcherAgain extends Thread{


    BitMapRequestAgain request;

    Handler handler = new Handler(Looper.getMainLooper());

    public RequestDispatcherAgain(BitMapRequestAgain request){
        this.request = request;
    }

    @Override
    public void run() {
        super.run();
        if(request!=null){
            //加载占位图
            loadImg(request);
            //网络加载图片
            Bitmap bitmap = httpDownLoad(request);
            //显示到具体容器中
            showImg(request,bitmap);
        }
        /*while(!interrupted()){
            //当前线程没有被中断就继续执行

        }*/
    }

    //加载占位符
    private void loadImg(BitMapRequestAgain request){
        final ImageView imageView = request.getImageView().get();
        final int redId = request.getResId();
        if(redId>0 && imageView!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(redId);
                }
            });
        }
    }

    //网络加载图片
    private Bitmap httpDownLoad(BitMapRequestAgain request){
        String url = request.getUrl();
        if(url!=null){
            Bitmap bitmap = HttpUtil.HttpConnection(url);
            return bitmap;
        }
        return null;
    }

    //显示到具体容器中
    private void showImg(BitMapRequestAgain request, final Bitmap bitmap){
        final ImageView imageView = request.getImageView().get();
        String urlMD5 = request.getUrlMD5();
        if(imageView!=null && bitmap!=null && imageView.getTag().equals(urlMD5)){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

}
