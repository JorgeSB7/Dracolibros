package com.dracolibros.presenters;

import android.util.Log;

import com.dracolibros.R;
import com.dracolibros.interfaces.FormInterface;
import com.dracolibros.views.MyApplication;

public class FormPresenter implements FormInterface.Presenter {
    private FormInterface.View view;
    String TAG = "Dracolibros/FormPresenter";
    public FormPresenter (FormInterface.View view) {this.view=view;}

    @Override
    public void onClickSaveButton() {
        view.CloseFormActivity();
    }

    @Override
    public void onClickDeleteButton() {
        Log.d(TAG,"onClickDeleteButton.....");
        view.alertDelete();
    }

    @Override
    public String getError(String error_code) {
        String error_msg = "";
        switch (error_code) {
            case "BookName":
                error_msg = MyApplication.getContext().getResources().getString(R.string.name_error);
                break;
            case "AuthorName":
                error_msg = MyApplication.getContext().getResources().getString(R.string.author_error);
                break;
            case "BookCode":
                error_msg = MyApplication.getContext().getResources().getString(R.string.code_error);
                break;
            case "BookISBN":
                error_msg = MyApplication.getContext().getResources().getString(R.string.isbn_error);
                break;
            case "BookDate":
                error_msg = MyApplication.getContext().getResources().getString(R.string.date_error);
                break;
            case "NewGenre":
                error_msg = MyApplication.getContext().getResources().getString(R.string.Genre_error);
                break;
        }
        return error_msg;
    }

}
