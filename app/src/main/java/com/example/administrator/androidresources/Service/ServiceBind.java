package com.example.administrator.androidresources.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceBind extends Service {

    public String data = "";
    private String TAG = "ServiceBind";
    private CallBack callBack;

    public ServiceBind(){}

    public void setCallback(CallBack callback){
        this.callBack = callback;
    }

    public interface CallBack{
        //在Service中有数据更新时，通过这个接口传出去
        void onDataChanged(String data);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void setData(String data){
            ServiceBind.this.data = data;
            Log.e(TAG, "setData: 传递的消息："+data);
            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
        }

        //获取服务
        public ServiceBind getService(){
            return ServiceBind.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"解除绑定",Toast.LENGTH_SHORT).show();
    }
}
