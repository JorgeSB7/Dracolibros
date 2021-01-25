package com.dracolibros.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class BookModel {
    String TAG = "Dracolibros/BookModel";

    public ArrayList<BookEntity> getAllSummarize() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<BookEntity> result = realm.where(BookEntity.class).findAll();

        ArrayList<BookEntity> bookList = new ArrayList<>();
        bookList.addAll(realm.copyFromRealm(result));

        realm.close();

        ArrayList<BookEntity> bookListSummirize = new ArrayList<>();

        for(BookEntity book: bookList) {
            bookListSummirize.add(new BookEntity(book.getId(),book.getName(),book.getAuthor(),book.getImage()));
        }
        return bookListSummirize;
    }


    public boolean insert (BookEntity book){
        boolean result = false;
        Realm realm = Realm.getDefaultInstance();

        if(book.getId().equals("")){
            realm.executeTransaction(r -> {
                book.setId(UUID.randomUUID().toString());
                realm.copyToRealm(book);
            });
            Log.d("DemoAndroidRealm", "Path: " + realm.getPath());
            result=true;
            realm.close();
        }
        else{
            realm.executeTransaction(r -> {
                realm.copyToRealmOrUpdate(book);
            });
            Log.d("DemoAndroidRealm", "Path: " + realm.getPath());
            result = true;
            realm.close();
        }
        return result;
    }

    public BookEntity getbyid (String id){
        Realm realm = Realm.getDefaultInstance();
        BookEntity bookEntity = realm.where(BookEntity.class).equalTo("id",id).findFirst();
        BookEntity book = new BookEntity();
        book = realm.copyFromRealm(bookEntity);
        realm.close();
        return book;
    }
}
