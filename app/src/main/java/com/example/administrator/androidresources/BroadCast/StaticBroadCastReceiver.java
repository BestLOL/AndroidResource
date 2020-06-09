package com.example.administrator.androidresources.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//静态广播注册
public class StaticBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("StaticBroadCastReceiver", "onReceive: "+"asssssss");

        Toast.makeText(context, "Boot Complete"+intent.getAction(), Toast.LENGTH_LONG).show();
    }
}
