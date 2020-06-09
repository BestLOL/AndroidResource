package com.example.administrator.androidresources.OkHttp;

import java.io.InputStream;

public interface CallBackListener {
    void onSuccess(InputStream inputStream);
    void onFailed();
}
