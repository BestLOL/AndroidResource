package com.example.administrator.androidresources;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";

    TextView tv;

    Button p1,p2,get,next;

    volatile SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");

        p1 = findViewById(R.id.put1);
        p2 = findViewById(R.id.put2);
        get = findViewById(R.id.get);
        next = findViewById(R.id.next);

        next.setText("第一个");

        sharedPreferences = getSharedPreferences("123",Context.MODE_PRIVATE);


        /*NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(2);*/


        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().putString("12","张1").commit();
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("12","张2").commit();
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = sharedPreferences.getString("12","");
                Toast.makeText(MainActivity.this,test,Toast.LENGTH_SHORT).show();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity1.class));
            }
        });

        /*tv = findViewById(R.id.tv_show);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,MainActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                *//*WindowTest.showPopWindow(MainActivity.this,tv);*//*

                //实例化对象
                *//*MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(getSupportFragmentManager(),"myDialogFragment");*//*

                *//*WindowTest.showDialogFragment(MainActivity.this);*//*
            }
        });*/



        /*EventBus.getDefault().register(this);

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("https://github.com/cozing").build();
        Call cal = client.newCall(request);

        try {
            //1 同步请求的方式
            Response response = cal.execute();

            //2 异步请求的方式
            cal.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.w("cozing", "交易失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.w("cozing", "交易成功");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");

        /*MainActivity.h = null;*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    static class HH{

    }

    public void startService(View view) {
        //startService(new Intent(this,ForeService.class));
        String ID = "DEFAULT";
        String NAME = "NAME";
        NotificationChannel notificationChannel = null;
        NotificationManager manager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, ID)
                .setContentTitle("title")
                .setContentText("Content")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        /*startForeground(1,notification.build());*/

        if (manager != null) {
            notification.setSmallIcon(R.mipmap.ic_launcher);
            manager.notify(2, notification.build());
        }
    }
}
