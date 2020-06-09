package com.example.administrator.androidresources.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.androidresources.MainActivity;
import com.example.administrator.androidresources.R;

//前台服务
public class ForeService extends Service {

    private String ID = "DEFAULT";
    private String NAME = "NAME";

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationChannel notificationChannel = null;
        NotificationManager manager = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(ID,NAME,NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,ID)
                .setContentTitle("title")
                .setContentText("Content")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setAutoCancel(true);
        startForeground(1,notification.build());

        if(manager!=null){
            notification.setSmallIcon(R.mipmap.ic_launcher);
            manager.notify(2,notification.build());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}