// IBookManager.aidl
package com.example.administrator.androidresources.IPC.AIDL;
import com.example.administrator.androidresources.IPC.AIDL.Book;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);

}
