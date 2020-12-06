package com.dracolibros.presenters;

import com.dracolibros.R;
import com.dracolibros.interfaces.SearchInterface;
import com.dracolibros.views.MyApplication;

public class SearchPresenter implements SearchInterface.Presenter {
    private SearchInterface.View view;
    public SearchPresenter (SearchInterface.View view) {this.view=view;}

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
}
