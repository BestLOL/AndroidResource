package com.example.administrator.androidresources.BroadCast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.androidresources.R;

//本地广播
public class LocalBroadCastTest extends AppCompatActivity {

    private String action = "NIHAO";
    private String action_static = "STATIC";
    private String action_dyname = "DYNAMIC";

    private Button sendLocal,sendStatic,sendDynamic;

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"收到广播"+intent.getAction(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        sendLocal = findViewById(R.id.bt_local);
        sendStatic = findViewById(R.id.bt_static);
        sendDynamic = findViewById(R.id.bt_dynamic);

        //本地广播注册
        registerReceive(this,action);
        //动态广播注册
        dynamicReceive(action_dyname);

        //本地广播
        sendLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocalBroadCast(LocalBroadCastTest.this,action);
            }
        });
        //静态广播
        sendStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStaticBroadCast(action_static);
            }
        });
        //动态广播
        sendDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMyBroadCast(action_dyname);
            }
        });
    }

    //发送本地广播
    public void sendLocalBroadCast(Context context,String action){
        Intent intent = new Intent(action);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.sendBroadcast(intent);
    }

    //本地广播接收器注册
    public void registerReceive(Context context,String action){
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(receiver,filter);
    }

    //发送静态广播
    public void sendStaticBroadCast(String action){
        Intent intent = new Intent(action);
        //静态广播的变化，需要指定包名，和接收器名称
        intent.setComponent(new ComponentName("com.example.administrator.androidresources","com.example.administrator.androidresources.BroadCast.StaticBroadCastReceiver"));
        sendBroadcast(intent);
    }

    //发送动态广播
    public void sendMyBroadCast(String action){
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    //动态广播接收器注册
    public void dynamicReceive(String action){
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        registerReceiver(receiver,filter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
