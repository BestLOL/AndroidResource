package com.example.administrator.androidresources.Handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidresources.R;

public class HandlerTest extends Activity {

    private Handler handler = new Handler();
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tv = findViewById(R.id.sendHanlder);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 1:
                                Log.e("Handler", "handleMessage");
                                break;
                            default:
                                tv.setText("123");
                                break;
                        }
                    }
                };

                Looper.loop();

            }
        }).start();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(2);
                    }
                }).start();

/*                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Handler", "handleMessage");
                    }
                });*/
            }
        });

    }




}
