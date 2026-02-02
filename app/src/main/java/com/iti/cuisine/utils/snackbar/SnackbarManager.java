package com.iti.cuisine.utils.snackbar;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarManager {

    private Snackbar currentSnackbar;

    public void showSnackbar(SnackbarBuilder.SnackbarData data, Context context, View hostView) {

        dismissCurrentSnackbar();

        currentSnackbar = Snackbar.make(
                context,
                hostView,
                data.getMessage(),
                Snackbar.LENGTH_SHORT
        );

        if (!data.getActionText().isBlank() && data.getAction() != null) {
            currentSnackbar.setAction(data.getActionText(), data.getAction());
        }
        View view = currentSnackbar.getView();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                data.getBottomPadding()
        );

        currentSnackbar.show();
    }

    private void dismissCurrentSnackbar() {
        if (currentSnackbar != null) {
            currentSnackbar.dismiss();
            currentSnackbar = null;
        }
    }
}
