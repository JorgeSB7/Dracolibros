package com.dracolibros.interfaces;

public interface ListInterface {
    public interface View{
        void StartFormActivity();
        void StartSearchActivity();
        void StartAboutAPPActivity();
    }

    public interface Presenter{
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutAPPButton();
    }
}
