package com.example.administrator.androidresources.EventBusPlugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androidresources.R;

public class TestbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        EventBus.getDefault().post(new NEBean("我","你"));
    }


}
