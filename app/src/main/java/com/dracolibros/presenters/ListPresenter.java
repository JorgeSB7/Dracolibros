package com.dracolibros.presenters;

import android.util.Log;

import com.dracolibros.R;
import com.dracolibros.interfaces.ListInterface;
import com.dracolibros.views.MyApplication;

public class ListPresenter implements ListInterface.Presenter {
    String TAG = "Dracolibros/ListPresenter";
    private ListInterface.View view;
    public ListPresenter (ListInterface.View view) {this.view=view;}

    @Override
    public void onClickFloatingButton() {
        view.StartFormActivity();
    }

    @Override
    public void onClickSearchButton() { view.StartSearchActivity(); }

    @Override
    public void onClickAboutAPPButton() { view.StartAboutAPPActivity(); }

    @Override
    public void onClickRecyclerViewItem(String id) {
        view.StartFormActivity(id);
    }

    @Override
    public String getError(int error_code) {
        Log.d(TAG, "Starting toolbar");
        String error_msg = "";
        switch (error_code) {
            case 1:
                error_msg = MyApplication.getContext().getResources().getString(R.string.DeleteD);
                break;
            case 2:
                error_msg = MyApplication.getContext().getResources().getString(R.string.ErrorDeleteD);
                break;
        }
        return error_msg;
    }

    /*
    @Override
    public void onSwipeRecyclerViewItem(String id) {
        // Decirle al modelo que borre id
        //.. luego en la Unidad 5

        // Decirle al RV que lo elimino
        view.removeRecyclerViewItem(id);
        // Decirle al view que muestre Toast
        view.showToast("error");
    }
     */

}
