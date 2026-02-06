package com.iti.cuisine.utils.snackbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int margin = 0;
        if (data.getBottomPadding() != 0) {
            margin = (int) (data.getBottomPadding() * context.getResources().getDisplayMetrics().density);
        }
        params.setMargins(
                params.leftMargin,
                params.topMargin,
                params.rightMargin,
                margin + params.bottomMargin
        );
        view.setLayoutParams(params);

        currentSnackbar.show();
    }

    private void dismissCurrentSnackbar() {
        if (currentSnackbar != null) {
            currentSnackbar.dismiss();
            currentSnackbar = null;
        }
    }
}
