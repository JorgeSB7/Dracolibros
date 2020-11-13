package com.dracolibros.presenters;

import com.dracolibros.interfaces.ListInterface;

public class ListPresenter implements ListInterface.Presenter {
    private ListInterface.View view;
    public ListPresenter (ListInterface.View view) {this.view=view;}

    @Override
    public void onClickFloatingButton() {
        view.StartFormActivity();
    }
}
