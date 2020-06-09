package com.example.administrator.androidresources.IPC.Messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerService extends Service {


    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("MessengerService", "MessengerService："+msg.getData().get("msg"));
                    break;
                default:
                    break;
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

}
