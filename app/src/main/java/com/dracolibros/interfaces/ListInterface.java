package com.dracolibros.interfaces;

import com.dracolibros.model.BookEntity;

import java.util.ArrayList;

public interface ListInterface {
    public interface View{
        void StartFormActivity();
        void StartFormActivity(String id);
        void StartSearchActivity();
        void StartAboutAPPActivity();
    }

    public interface Presenter{
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutAPPButton();
        void onClickRecyclerViewItem(String id);
        public String getError(int error_code);
        ArrayList<BookEntity> getAllSummarize();
        public BookEntity getbyid (String id);
    }
}
