package com.dracolibros.interfaces;

import com.dracolibros.model.BookEntity;

import java.util.ArrayList;

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
        BookEntity getbyid (String id);
        public ArrayList<String> getGenres();
        //__________PERMISOS
        void onClickImage();
        public void delete (BookEntity b);
    }

}
