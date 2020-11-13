package com.dracolibros.presenters;

import com.dracolibros.interfaces.FormInterface;

public class FormPresenter implements FormInterface.Presenter {
    private FormInterface.View view;
    public FormPresenter (FormInterface.View view) {this.view=view;}

    @Override
    public void onClickSaveButton() {
        view.CloseFormActivity();
    }

}
