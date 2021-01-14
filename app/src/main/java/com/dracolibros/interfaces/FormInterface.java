package com.dracolibros.interfaces;

import com.dracolibros.model.BookEntity;

public interface FormInterface {
    public interface View{
        void CloseFormActivity();
        void alertDelete();
        void IntentChooser();
        void showError();
        void selectPicture();
        void deleteIMG();
    }
    public interface Presenter{
        void onClickSaveButton(BookEntity book);
        void onClickDeleteButton();
        String getError(String error_code);
        void clicAcceptDelete();
        void PermissionGranted();
        void PermissionDenied();

        //__________PERMISOS
        void onClickImage();
    }

}
