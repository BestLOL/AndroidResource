package com.example.administrator.androidresources.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.androidresources.R;

public class MyActivity extends Activity {

    private ViewStub viewStub;
    private LinearLayout parentContainer;
    private TextView textView;

    public String TAG = "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylayout);


        Log.e(TAG, "onCreate: ");

        initView();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        viewStub = findViewById(R.id.vs_layout);
        viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                //inflate ViewStub的时候显示
                Log.e("ViewStub","ViewStub is loaded! viewStub==null"+(viewStub==null));
            }
        });
    }

    public void showViewStub(View view){
        showViewStub();
    }

    private void showViewStub() {
        try {
            Log.e("ViewStub","ViewStub load before! viewStub==null"+(viewStub==null));
            parentContainer = (LinearLayout) viewStub.inflate();
            textView = (TextView) parentContainer.findViewById(R.id.tv_erro_tips);
            textView.setText("不好意思，还未录入任何数据");
        }catch (Exception e){
            if(parentContainer==null) {
                parentContainer = (LinearLayout) findViewById(R.id.container_error_layout);
            }
            if(textView==null) {
                textView = (TextView) parentContainer.findViewById(R.id.tv_erro_tips);
            }
            textView.setText("不好意思，还未录入任何数据");
            viewStub.setVisibility(View.VISIBLE);
        }
    }

    public void hideViewStub(View view){
        viewStub.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("activity", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


       /* int i = 1/0;*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }


}
