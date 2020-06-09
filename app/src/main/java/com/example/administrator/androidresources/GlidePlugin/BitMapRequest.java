package com.example.administrator.androidresources.GlidePlugin;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

//图片的请求，也就是每个请求的对象类
public class BitMapRequest {

    //上下文对象
    private Context context;

    //图片请求的URL
    private String url;

    //每个对象的监听
    private RequestListener requestListener;

    //占位符图片
    private int resId;

    //每个图片的标记
    private String urlMD5;

    //每个图片的容器
    private SoftReference<ImageView> imageView;

    public BitMapRequest(Context context){
        this.context = context;
    }

    //存放图片路径
    public BitMapRequest load(String url){
        this.url = url;
        this.urlMD5 = MD5Util.MD5(url);
        return this;
    }

    //存放占位符
    public BitMapRequest loading(int resId){
        this.resId = resId;
        return this;
    }

    //设置图片加载监听
    public BitMapRequest listener(RequestListener requestListener){
        this.requestListener = requestListener;
        return this;
    }

    //设置容器
    public void into(ImageView imageView){
        imageView.setTag(this.urlMD5);//设置标记
        this.imageView = new SoftReference<>(imageView);
        //将请求加入到处理中
        RequestManager.getInstance().addBitMapRequest(this);
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public int getResId() {
        return resId;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public ImageView getImageView() {
        return imageView.get();
    }
}
