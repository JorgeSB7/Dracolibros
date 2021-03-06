package com.dracolibros.presenters;

import android.util.Log;

import com.dracolibros.R;
import com.dracolibros.interfaces.SearchInterface;
import com.dracolibros.model.BookModel;
import com.dracolibros.views.MyApplication;

import java.util.ArrayList;

public class SearchPresenter implements SearchInterface.Presenter {
    String TAG = "Dracolibros/SearchPresenter";
    private SearchInterface.View view;
    private BookModel BModel;

    public SearchPresenter (SearchInterface.View view) {this.view=view;
        BModel = new BookModel();
    }

    @Override
    public void onClickSearchButton() {
        view.CloseSearchActivity();
    }

    @Override
    public String getError(String error_code) {
        String error_msg = "";
        switch (error_code) {
            case "BookInformation":
                error_msg = MyApplication.getContext().getResources().getString(R.string.information_error);
                break;
            case "BookDate":
                error_msg = MyApplication.getContext().getResources().getString(R.string.date_error);
                break;
        }
        return error_msg;
    }

    @Override
    public ArrayList<String> getGenres() {
        return BModel.getGenres();
    }

    @Override
    public void onClickMenuHelp() {
        Log.d(TAG,"onClickMenuHelp.....");
        view.startHelpActivity();
    }
}
