package com.example.administrator.androidresources.GlidePlugin;

import android.graphics.Bitmap;

public interface RequestListener {

    boolean onSuccess(Bitmap bitmap);

    boolean onFailed();
}
