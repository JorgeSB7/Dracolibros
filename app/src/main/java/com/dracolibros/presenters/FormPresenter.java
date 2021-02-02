package com.dracolibros.presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.dracolibros.R;
import com.dracolibros.interfaces.FormInterface;
import com.dracolibros.model.BookEntity;
import com.dracolibros.model.BookModel;
import com.dracolibros.views.MyApplication;

import java.util.ArrayList;

public class FormPresenter implements FormInterface.Presenter {
    private FormInterface.View view;
    String TAG = "Dracolibros/FormPresenter";

    private BookModel BModel;
    public FormPresenter (FormInterface.View view) {
        this.view=view;
        this.BModel = new BookModel();
    }

    @Override
    public void onClickSaveButton(BookEntity book) {
        if (BModel.insert(book)){
            view.CloseFormActivity();
        } else {

        }
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
            case "SaveError":
                error_msg = MyApplication.getContext().getResources().getString(R.string.SaveError);
                break;
        }
        return error_msg;
    }

    @Override
    public void clicAcceptDelete() {
        Log.d(TAG,"clicAcceptDelete.....");
        view.CloseFormActivity();
    }

    @Override
    public void PermissionGranted() {
        Log.d(TAG,"PermissionGranted.....");
        view.selectPicture();
    }

    @Override
    public void PermissionDenied() {
        Log.d(TAG,"PermissionDenied.....");
        view.showError();
    }

    @Override
    public BookEntity getbyid(String id) {
        return BModel.getbyid(id);
    }

    @Override
    public ArrayList<String> getGenres() {
        return BModel.getGenres();
    }

    @Override
    public void onClickImage() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d(TAG, "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                view.IntentChooser();
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                view.showError();
            }
        } else {
            // Permiso aceptado
                view.selectPicture();
        }
    }

    @Override
    public void delete(BookEntity b) {
        BModel.delete(b);
    }

}
