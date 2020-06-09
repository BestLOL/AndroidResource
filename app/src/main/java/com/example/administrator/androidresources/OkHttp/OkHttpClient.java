package com.example.administrator.androidresources.OkHttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.androidresources.R;

public class OkHttpClient extends AppCompatActivity {

    //private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb52107206585ab074f5e59a8c73875b";
    private String url = "http://";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        NEHttp.sendJsonRequest(Response.class, url,null, new JsonDataTransformListener<Response>() {
            @Override
            public void onSuccess(Response clazz) {
                Log.e("-=====>", clazz.toString());
            }
        });
    }
}
