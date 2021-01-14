package com.dracolibros.interfaces;

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
    }
}
