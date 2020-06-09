package com.example.administrator.androidresources.Permission;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.androidresources.NetWork.NetWorkChangReceiver;
import com.example.administrator.androidresources.NetWork.NetWorkInterface;

public class PermissionActivity extends AppCompatActivity implements PermissionInterface ,NetWorkInterface {

    private boolean isRegistered = false;
    private NetWorkChangReceiver netWorkChangReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册网络状态监听广播
        netWorkChangReceiver = new NetWorkChangReceiver(this);
        netWorkChangReceiver.setNetworkInterface(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        /*android.net.wifi.WIFI_STATE_CHANGED*/
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        /*android.net.wifi.STATE_CHANGE*/
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
        isRegistered = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (isRegistered) {
            unregisterReceiver(netWorkChangReceiver);
        }
    }

    //获取申请权限的Code
    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    //获取需要申请的权限
    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    //权限申请成功
    @Override
    public void requestPermissionsSuccess() {

    }

    //部分权限申请失败
    @Override
    public void requestPermissionsFail() {

    }

    //网络连接状态
    @Override
    public void connectState(boolean state) {
        if(state){
            //连接成功
            Toast.makeText(PermissionActivity.this,"网络连接成功",Toast.LENGTH_SHORT).show();
        }else{
            //连接失败
            Toast.makeText(PermissionActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
    }
}
