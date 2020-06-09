package com.example.administrator.androidresources.GlideAgain;

import android.content.Context;
import android.widget.ImageView;

import com.example.administrator.androidresources.GlidePlugin.MD5Util;
import com.example.administrator.androidresources.GlidePlugin.RequestListener;

import java.lang.ref.SoftReference;

//图片请求
public class BitMapRequestAgain {
    //上下文对象
    private Context context;
    //图片请求的URL
    private String url;
    //容器,使用软引用
    private SoftReference<ImageView> imageView;
    //图片标记
    private String urlMD5;
    //占位符
    private int resId;

    //监听成功与否
    private RequestListener listener;

    public BitMapRequestAgain(Context context){
        this.context = context;
    }

    //加载占位符
    public BitMapRequestAgain loading(int res){
        this.resId = res;
        return this;
    }

    //加载url
    public BitMapRequestAgain load(String url){
        this.url = url;
        this.urlMD5 = MD5Util.MD5(url);
        return this;
    }

    //加载listener
    public BitMapRequestAgain listener(RequestListener listener){
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView){
        imageView.setTag(this.urlMD5);
        this.imageView = new SoftReference<ImageView>(imageView);

        DispatcherManager.getInstance().addDispatcher(this);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SoftReference<ImageView> getImageView() {
        return imageView;
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public RequestListener getListener() {
        return listener;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }
}
