package com.example.administrator.androidresources;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

//多进程的Application配置
public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //获取进程id
        int pid = android.os.Process.myPid();
        //根据进程id获取进程名称
        String pName = getProcessName(this,pid);
    }


    //获取进程名称
    private String getProcessName(Context context, int pid) {
        //获取所有当前正在运行的进程
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if(runningApps==null){
            return null;
        }

        //根据正在运行进程的pid和本进程id比较，找出运行进程的进程名称
        for(ActivityManager.RunningAppProcessInfo info : runningApps){
            if(info.pid == pid){
                return info.processName;
            }
        }
        return null;
    }


}
