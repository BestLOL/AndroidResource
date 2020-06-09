package com.example.administrator.androidresources.EventBusPlugin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.androidresources.R;

public class TestaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        EventBus.getDefault().register(this);

        findViewById(R.id.bt_eventBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestaActivity.this,TestbActivity.class);
                startActivity(intent);
            }
        });

    }

    @SubScribe(thread = ThreadModel.MAIN)
    public void getMessage(NEBean bean){
        Log.e("TestaActivity", "getMessage: "+bean.toString());
    }
}
