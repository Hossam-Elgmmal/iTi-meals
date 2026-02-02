package com.iti.cuisine.utils.snackbar;

import android.view.View;

public class SnackbarBuilder {

    private String message = "";
    private String actionText = "";
    private View.OnClickListener action = null;
    private int bottomPadding = 0;

    public SnackbarBuilder() {}

    public SnackbarData build() {
        return new SnackbarData(message, actionText, action, bottomPadding);
    }

    public SnackbarBuilder setAction(String actionText, View.OnClickListener action) {
        this.actionText = actionText;
        this.action = action;
        return this;
    }

    public SnackbarBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public SnackbarBuilder setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
        return this;
    }

    public static class SnackbarData {
        private final String message;
        private final String actionText;
        private final View.OnClickListener action;
        private final int bottomPadding;

        private SnackbarData(String message, String actionText, View.OnClickListener action, int bottomPadding) {
            this.message = message;
            this.actionText = actionText;
            this.action = action;
            this.bottomPadding = bottomPadding;
        }

        public String getMessage() {
            return message;
        }

        public View.OnClickListener getAction() {
            return action;
        }

        public String getActionText() {
            return actionText;
        }

        public int getBottomPadding() {
            return bottomPadding;
        }
    }
}
