package com.iti.cuisine.login;

public interface LoginPresenter {

    interface LoginView {
        void navigateToHomeScreen();
        void navigateToSignUpScreen();
        void navigateToForgotPasswordScreen();
        void showLoading();
        void hideLoading();
        void showErrorMessage(String message);
    }

    void onLoginClick(String email, String password);
    void onSignUpClick();
    void onForgotPasswordClick();
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
