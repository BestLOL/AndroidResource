package com.example.administrator.androidresources.EventBusPlugin;

public class NEBean {

    private String first,second;

    public NEBean(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "NEBean{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
