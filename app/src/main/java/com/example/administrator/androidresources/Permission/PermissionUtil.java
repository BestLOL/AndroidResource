package com.example.administrator.androidresources.Permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

//权限动态工具类
public class PermissionUtil {


    /**
     * 判断是有拥有某个权限
     * @param context 上下文
     * @param permission 需要判断的权限
     * @return
     */
    public static boolean hasPermission(Context context,String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //API大于23需要动态申请权限
            if(context.checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 请求权限
     * @param activity 请求主体
     * @param permissions 需要请求的权限
     * @param requestCode 请求权限返回的Code
     */
    public static void requestPermission(Activity activity, String[] permissions,int requestCode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //动态申请权限
            activity.requestPermissions(permissions,requestCode);
        }
    }

    /**
     * 返回缺失的权限
     * @param context 上下文
     * @param permissions 需要判断的权限
     * @return
     */
    public static String[] getDeniedPermissions(Context context,String[] permissions){
        List<String> list = new ArrayList<>();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            for(String permission:permissions){
                boolean check = hasPermission(context,permission);
                if(!check)
                    list.add(permission);
            }

            if(list.size()>0){
                return list.toArray(new String[list.size()]);
            }
        }
        return null;
    }

}
