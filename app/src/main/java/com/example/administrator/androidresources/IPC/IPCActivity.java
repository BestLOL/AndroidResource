package com.example.administrator.androidresources.IPC;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.androidresources.IPC.AIDL.IBookManager;
import com.example.administrator.androidresources.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IPCActivity extends AppCompatActivity implements ServiceConnection {

    private String TAG = "IPCActivity";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

    }


    //序列化过程
    public void serialProcess(){
        User user = new User(1,"张三",15);
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cache.txt"))){
            outputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化过程
    public void transSerialProcess(){
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("cache.txt"));){
            User user = (User) inputStream.readObject();
            Log.e(TAG, user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //服务绑定
    public void bindService(){
        //绑定服务
        Intent intent = new Intent(IPCActivity.this,IPCService.class);
        bindService(intent,IPCActivity.this,BIND_AUTO_CREATE);
    }

    private IBookManager mBinder = null;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBinder = IBookManager.Stub.asInterface(service);
        //返回对象后,给对象设置死亡代理，设置完成之后，如果服务端死亡，那么就会接收到Binder死亡的消息，然后重新绑定服务
        try {
            service.linkToDeath(mDeathRecipient,0);
            Toast.makeText(getApplicationContext(), "绑定服务成功",
                    Toast.LENGTH_LONG).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    //当Binder死亡时会回调这个方法，也就是监听Service是否死亡，若死亡我们可以移除之前绑定的Binder代理，并重新绑定远程服务
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mBinder==null){
                return;
            }
            mBinder.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBinder = null;
            //下面重新绑定远程Service
            bindService();
        }
    };


    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
