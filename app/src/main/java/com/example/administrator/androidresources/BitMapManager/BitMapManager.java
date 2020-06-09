package com.example.administrator.androidresources.BitMapManager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;

import com.example.administrator.androidresources.R;

public class BitMapManager extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);



    }

    //DiskLrucache
    public void useDiskLruCache(){
        long maxSize = 1024*1024*50;//50MB

    }

    //Lrucache使用
    public void useCache(){
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);//原来的单位为B
        int cacheSize = maxMemory/8;//总容量为当前进程可用内存的1/8，单位为KB

        //使用Lrucache
        LruCache lruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //sizeOf计算缓存对象的大小
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                //这个方法是在移除旧缓存时进行，可以用来进行资源回收操作
            }
        };

        lruCache.put("张三",BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background));
        lruCache.get("张三");
        lruCache.remove("张三");
    }

    //高效加载Bitmap，核心就是采用BitmapFactory.Options来加载所需尺寸的图片
    public static Bitmap decodeSampleBitmapFromResource(Resources resource,int resID,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置为true时，会解析图片的宽/高信息，并不会加载图片。解析出来的值和图片存放位置（drawable里面目录），设备本身有关系
        BitmapFactory.decodeResource(resource,resID,options);

        //计算采样率，也就是缩放比例
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //采样率计算完毕之后，进行图片缩放，再加载出bitmap后返回
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource,resID,options);
    }

    //计算采样率
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        //获取图片的宽高信息
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSzie = 1;

        if(width>reqWidth || height>reqHeight){
            int halfWidth = width/2;
            int halfHeight = height/2;

            while((halfHeight/inSampleSzie)>=reqHeight && (halfWidth/inSampleSzie)>=reqWidth){
                inSampleSzie *= 2;//缩小为原来的1/2,且每次扩大为2的整数倍，且2代表的是缩小1/2，如果是3就是缩小1/3
            }
        }
        return inSampleSzie;
    }
}
