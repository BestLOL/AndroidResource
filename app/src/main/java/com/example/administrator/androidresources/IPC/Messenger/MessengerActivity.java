package com.example.administrator.androidresources.IPC.Messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androidresources.R;

public class MessengerActivity extends AppCompatActivity {

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null,1);
            Bundle data = new Bundle();
            data.putString("msg","你好");
            msg.setData(data);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
