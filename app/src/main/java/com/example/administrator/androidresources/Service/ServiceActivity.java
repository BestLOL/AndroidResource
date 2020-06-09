package com.example.administrator.androidresources.Service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidresources.R;

public class ServiceActivity extends AppCompatActivity implements ServiceConnection{

    private ServiceBind.MyBinder binder = null;

    private TextView tv;
    private Button startService,bindSerice,unbindService,sendMsg1,sendMsg2;

    public String TAG = "ServiceActivity";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        tv = findViewById(R.id.tv_show);
        bindSerice = findViewById(R.id.bt_bind);
        startService = findViewById(R.id.bt_startService);
        unbindService = findViewById(R.id.bt_unbind);
        sendMsg1 = findViewById(R.id.bt_sendMsg1);
        sendMsg2 = findViewById(R.id.bt_sendMsg2);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this,ServiceCommon.class);
                intent.putExtra("data","String数据");
                startService(intent);
            }
        });

        bindSerice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定服务
                Intent intent = new Intent(ServiceActivity.this,ServiceBind.class);
                bindService(intent,ServiceActivity.this,BIND_AUTO_CREATE);
            }
        });

        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(ServiceActivity.this);
            }
        });

        sendMsg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binder!=null){
                    binder.setData("bt1");
                }
            }
        });

        sendMsg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binder!=null){
                    binder.setData("bt2");
                }
            }
        });


    }

    //服务绑定时调用
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.e(TAG, "onServiceConnected: 绑定服务");
        Toast.makeText(getApplicationContext(),"绑定服务",Toast.LENGTH_SHORT).show();
        binder = (ServiceBind.MyBinder) service;

        binder.getService().setCallback(new ServiceBind.CallBack() {
            @Override
            public void onDataChanged(String data) {
                Message msg = Message.obtain();
                final Bundle bundle = new Bundle();
                bundle.putString("data","String数据");
                msg.setData(bundle);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String str  = (String) bundle.get("data");
                        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //解绑时调用
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "onServiceConnected: 服务已被强行中断");
        Toast.makeText(getApplicationContext(),"服务已被强行中断",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }
}
