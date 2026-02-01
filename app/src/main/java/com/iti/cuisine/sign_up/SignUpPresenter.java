package com.iti.cuisine.sign_up;

public interface SignUpPresenter {

    interface SignUpView {
        void navigateToHomeScreen();
        void navigateToLoginScreen();
        void showLoading();
        void hideLoading();
        void showErrorMessage(String message);
    }

    void onGoToLoginClick();
    void onSignUpClick(String username, String email, String password, String confirmPassword);
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
