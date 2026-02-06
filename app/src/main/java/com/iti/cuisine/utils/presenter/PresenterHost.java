package com.iti.cuisine.utils.presenter;

import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import java.util.function.Supplier;

public interface PresenterHost {

    <T extends Presenter> T getPresenter(String key, Supplier<T> factory);

    void removePresenter(String key);

    void showLoadingDialog();

    void hideLoadingDialog();

    void showSnackbar(SnackbarBuilder.SnackbarData data);
}
