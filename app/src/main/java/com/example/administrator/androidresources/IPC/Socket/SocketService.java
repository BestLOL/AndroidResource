package com.example.administrator.androidresources.IPC.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        //服务端
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket client = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //客户端
        try {
            Socket socket = new Socket("localhost",8080);
            socket.getInputStream();
            socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
