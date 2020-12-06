package com.dracolibros.interfaces;

public interface SearchInterface {
    public interface View{
        void CloseSearchActivity();
    }
    public interface Presenter{
        void onClickSearchButton();
        String getError(String error_code);
    }
}
