package com.example.administrator.androidresources.GlidePlugin;

import android.content.Context;

//封装请求
public class Glide {

    public static BitMapRequest with(Context context){
        return new BitMapRequest(context);
    }
}
