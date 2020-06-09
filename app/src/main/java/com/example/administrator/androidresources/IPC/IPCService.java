package com.example.administrator.androidresources.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.administrator.androidresources.IPC.AIDL.Book;
import com.example.administrator.androidresources.IPC.AIDL.IBookManager;

import java.util.List;

//在Service中用于通信
public class IPCService extends Service {
    private List<Book> bookList;

    //创建一个Binder类
    private IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (bookList){
                return bookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if(!bookList.contains(book)){
                bookList.add(book);
            }
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        //服务端调用的就是这个Binder对象
        return mBinder;
    }



}
