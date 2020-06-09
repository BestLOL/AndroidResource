package com.example.administrator.androidresources.OkHttp;

public class Response {
    private int resultCode;
    private String reason;

    @Override
    public String toString() {
        return "Response{" +
                "resultCode=" + resultCode +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
