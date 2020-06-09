package com.example.administrator.androidresources.TanChuang;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.administrator.androidresources.R;

//PopWindow
public class WindowTest {

    /**
     * 三种获取View的方式
     * View view = LayoutInflater.from(activity).inflate(R.layout.dialog_window,null);
     * View view = inflater.inflate(R.layout.dialog_window,container);
     * View view = View.inflate();
     */

    //PopWindow是根据控件来调整自身位置的
    public static void showPopWindow(final Activity activity, View needView){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_window,null);
        final PopupWindow popupWindow = new PopupWindow(view,WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,false);//最后的true是获取焦点

        //设置显示位置
        popupWindow.showAsDropDown(needView,0,0);

        //其他设置,pw对话框设置半透明背景。原理：pw显示时，改变整个窗口的透明度为0.7，当pw消失时，透明度为1
        final WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        final Window window = activity.getWindow();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                window.setAttributes(params);
            }
        });

        view.findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                params.alpha = 1f;
                window.setAttributes(params);
            }
        });
    }

    //AlertDialog
    public static void showDialogFragment(Activity activity){
        View view = View.inflate(activity,R.layout.dialog_window,null);
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                                .setView(view)
                                .setCancelable(false)
                                .create();

        dialog.show();

        view.findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


}
