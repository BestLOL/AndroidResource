package com.example.administrator.androidresources.NetWork;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.administrator.androidresources.Permission.PermissionActivity;


public class NetWorkChangReceiver extends BroadcastReceiver {
    private Activity activity;
    private NetWorkInterface networkInterface;

    public NetWorkChangReceiver(){}

    public NetWorkChangReceiver(Activity activity) {
        this.activity = activity;
    }

    public void setNetworkInterface(NetWorkInterface networkInterface){
        this.networkInterface = networkInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            //获取联网状态的NetworkInfo对象
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(PermissionActivity.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info!=null){
                //如果当前的网络连接成功并且网络连接可用
                if(NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()){
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Log.i("NetWorkState", getConnectionType(info.getType()) + "连上");
                        networkInterface.connectState(true);
                    }
                }else{
                    Log.i("NetWorkState", getConnectionType(info.getType()) + "断开");
                    networkInterface.connectState(false);
                }
            }else{
                Log.e("NetWorkState", "DisConnect");
                networkInterface.connectState(false);
            }
        }
    }

   /*
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

}
