package com.dracolibros.interfaces;

public interface FormInterface {
    public interface View{
        void CloseFormActivity();
    }
    public interface Presenter{
        void onClickSaveButton();
    }
}
