package com.example.administrator.androidresources.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceCommon extends Service {

    private String TAG = "ServiceCommon";
    public String data = "";

    public ServiceCommon(){}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        Log.e(TAG, "setData: 传递的消息："+data);
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
