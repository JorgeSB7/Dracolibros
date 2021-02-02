package com.dracolibros.interfaces;

import java.util.ArrayList;

public interface SearchInterface {
    public interface View{
        void CloseSearchActivity();
    }
    public interface Presenter{
        void onClickSearchButton();
        String getError(String error_code);
        public ArrayList<String> getGenres();
    }
}
