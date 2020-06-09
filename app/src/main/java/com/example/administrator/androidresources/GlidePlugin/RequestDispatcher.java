package com.example.administrator.androidresources.GlidePlugin;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.util.concurrent.LinkedBlockingDeque;


//具体图片请求的实现类
public class RequestDispatcher extends Thread{

    //Deque系列考虑了多线程，有独占锁，效率不高（可以换成Queue）
    private LinkedBlockingDeque<BitMapRequest> bitMapRequest;

    //放到主线程中加载
    private Handler handler = new Handler(Looper.getMainLooper());

    public RequestDispatcher(LinkedBlockingDeque<BitMapRequest> bitMapRequest){
        this.bitMapRequest = bitMapRequest;
    }

    @Override
    public void run() {
        super.run();
        //若当前线程没有中断就一直运行
        while (!isInterrupted()){
            //poll获取队列首元素，若没有了则返回null
            BitMapRequest br = bitMapRequest.poll();
            if(br!=null){
                //加载占位图
                loadImg(br);
                //网络加载图片
                Bitmap bitmap = httpDownLoad(br);
                //显示到具体容器中
                showImg(br,bitmap);
            }
        }
    }

    //展示图片
    private void showImg(BitMapRequest bitMapRequest, final Bitmap bitmap) {
        final ImageView imageView = bitMapRequest.getImageView();
        String urlMD5 = bitMapRequest.getUrlMD5();
        if(imageView!=null && bitmap!= null && imageView.getTag().equals(urlMD5)){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    //网络下载图片
    private Bitmap httpDownLoad(BitMapRequest bitMapRequest) {
        String url = bitMapRequest.getUrl();
        Bitmap bitmap = HttpUtil.HttpConnection(url);
        return bitmap;
    }

    //加载占位图
    private void loadImg(BitMapRequest bitMapRequest) {
        final int resId = bitMapRequest.getResId();
        final ImageView imageView = bitMapRequest.getImageView();
        if(resId > 0 && imageView!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }

}
