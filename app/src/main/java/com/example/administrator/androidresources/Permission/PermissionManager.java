package com.example.administrator.androidresources.Permission;

import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionManager {

    private Activity activity;
    private PermissionInterface permissionInterface;

    public PermissionManager(Activity activity, PermissionInterface permissionInterface) {
        this.activity = activity;
        this.permissionInterface = permissionInterface;
    }

    /**
     * 开始请求权限。
     * 方法内部已经对Android M 或以上版本进行了判断，外部使用不再需要重复判断。
     * 如果设备还不是M或以上版本，则也会回调到requestPermissionsSuccess方法。
     */
    public void requestPermission(){
        String[] deniedPermissions = PermissionUtil.getDeniedPermissions(activity,permissionInterface.getPermissions());
        if(deniedPermissions!=null && deniedPermissions.length>0){
            PermissionUtil.requestPermission(activity,deniedPermissions,permissionInterface.getPermissionsRequestCode());
        }else{
            //返回请求成功
            permissionInterface.requestPermissionsSuccess();
        }
    }


    /**
     * 在Activity中的onRequestPermissionsResult中调用
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return true 代表对该requestCode感兴趣，并已经处理掉了。false 对该requestCode不感兴趣，不处理。
     * */
    public boolean requestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        if(requestCode==permissionInterface.getPermissionsRequestCode()){
            boolean isAllGranted = true;
            for(int result:grantResults){
                if(result == PackageManager.PERMISSION_DENIED)
                    isAllGranted = false;
                    break;
            }
            if(isAllGranted){
                permissionInterface.requestPermissionsSuccess();
            }else{
                permissionInterface.requestPermissionsFail();
            }
            return true;
        }
        return false;
    }
}
