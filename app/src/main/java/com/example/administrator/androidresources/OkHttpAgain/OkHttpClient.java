package com.example.administrator.androidresources.OkHttpAgain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.androidresources.OkHttp.Response;
import com.example.administrator.androidresources.R;

public class OkHttpClient extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        findViewById(R.id.bt_sendRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://blog.csdn.net/qq_39959838/article/details/82918676";
                OkHttp.sendRequest(url, null, Response.class, new CallBackListener<Response>() {
                    @Override
                    public void onSuccess(Response responseClass) {
                        Log.e("-=====>", responseClass.toString());
                    }
                });
            }
        });


    }
}
