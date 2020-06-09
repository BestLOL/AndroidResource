package com.example.administrator.androidresources.GlideAgain;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.androidresources.GlidePlugin.RequestListener;
import com.example.administrator.androidresources.Permission.PermissionActivity;
import com.example.administrator.androidresources.Permission.PermissionManager;
import com.example.administrator.androidresources.R;

public class GlideAgainActivity extends PermissionActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private PermissionManager permissionManager;

    private static String[] IMAGES = {
            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1561691125&di=2100857095062279ad9ba6bddbf82d9e&src=http://hbimg.b0.upaiyun.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183820&di=355355f64356f34054666affad22ee48&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fdf9c608cdc843e6fb6ef6d145cd69b14c9b79ca55968d-3UmfMm_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183820&di=934a3f1e93137424fb7fe62655586362&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FWPoHgfhyqEjUG_HP2AK7ow%3D%3D%2F6631872608210454282.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183819&di=4874273b77c7c8822d94c1875558f30c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ff00204e4eea23d34e8e413290e5e799b68318395194f6-lRnmz8_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183819&di=b7ca727c5fa8b2142f91db7564c26e1c&imgtype=0&src=http%3A%2F%2Fwww.chinadaily.com.cn%2Fhqzx%2Fimages%2Fattachement%2Fjpg%2Fsite385%2F20120924%2F00221918200911ca40e52b.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183819&di=67234b035d3d84bf9bea92e4c87c30c2&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fc999a12ebdc52d6be8592404254f36e4a76266241e90d-cNSF1k_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183819&di=1272b4daa6432c9845803aae111b7432&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net%2FVAyaZr0DXxxpriN30Me7YQ%3D%3D%2F3358277947236783845.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183819&di=a9a523b61a4bfe6d14b669aece312b05&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F16880e1f05dc8807c3e5336d1d8eb4eeeb6130ac3423e-xa0xnN_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183818&di=89cc27a2caeef0dc1eab8105c3d69b89&imgtype=0&src=http%3A%2F%2Fd-pic-image.yesky.com%2F1080x-%2FuploadImages%2F2019%2F044%2F59%2F1113V6L3Q6TY.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561702183818&di=2ffc29a0d1f215ff87351a503dbbafac&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201412%2F27%2F20141227174553_auCHe.jpeg",
    };

    private Button addOne;
    private Button addMore;
    private LinearLayout showImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_a);

        verifyStoragePermission();
    }


    private void initView(){
        addOne = findViewById(R.id.addOne);
        addMore = findViewById(R.id.addMore);
        showImage = findViewById(R.id.showImage);

        addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                more();
            }
        });
    }


    private void one(){
        ImageView imageView = createImageView();
        showImage.addView(imageView);

        GlideAgain.with(GlideAgainActivity.this)
                .loading(R.mipmap.ic_launcher)
                .load(IMAGES[0])
                .listener(new RequestListener() {
                    @Override
                    public boolean onSuccess(Bitmap bitmap) {
                        return true;
                    }

                    @Override
                    public boolean onFailed() {
                        return false;
                    }
                })
                .into(imageView);
    }

    private void more(){
        for(int i=0;i<IMAGES.length;i++){
            ImageView imageView = createImageView();
            showImage.addView(imageView);

            GlideAgain.with(GlideAgainActivity.this)
                    .loading(R.mipmap.ic_launcher)
                    .load(IMAGES[i])
                    .listener(new RequestListener() {
                        @Override
                        public boolean onSuccess(Bitmap bitmap) {
                            Log.e("图片加载监听", "图片加载成功");
                            return true;
                        }
                        @Override
                        public boolean onFailed() {
                            Log.e("图片加载监听", "图片加载失败");
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    //创建图片
    private ImageView createImageView(){
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    //动态申请权限
    private void verifyStoragePermission() {
        permissionManager = new PermissionManager(this,this);
        permissionManager.requestPermission();
    }

    /**
     * 权限状态返回
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissionManager.requestPermissionResult(requestCode,permissions,grantResults)){
            //请求已处理，并已回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //下面四个重写父类方法
    @Override
    public int getPermissionsRequestCode() {
        return 1000;
    }

    @Override
    public String[] getPermissions() {
        return PERMISSIONS_STORAGE;
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限得到允许，开始具体操作
        initView();
    }

    @Override
    public void requestPermissionsFail() {
        //权限没有得到允许，结束
        finish();
    }
}
