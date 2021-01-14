package com.dracolibros.model;

import android.util.Log;

import java.util.UUID;

import io.realm.Realm;

public class BookModel {
    String TAG = "Dracolibros/BookModel";

    public boolean insert (BookEntity book){
        boolean result = true;
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            book.setId(UUID.randomUUID().toString());
            realm.copyToRealm(book);
        });
        Log.d("DemoAndroidRealm", "Path: " + realm.getPath());
        realm.close();
        return result;
    }
}
