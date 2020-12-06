package com.dracolibros.interfaces;

public interface FormInterface {
    public interface View{
        void CloseFormActivity();
        void alertDelete();
    }
    public interface Presenter{
        void onClickSaveButton();
        void onClickDeleteButton();
        String getError(String error_code);
    }

}
