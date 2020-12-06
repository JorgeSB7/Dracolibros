package com.dracolibros.presenters;

import com.dracolibros.interfaces.ListInterface;

public class ListPresenter implements ListInterface.Presenter {
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
