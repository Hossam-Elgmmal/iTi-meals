package com.iti.cuisine.sign_up;

public interface SignUpPresenter {

    interface SignUpView {
        void navigateToHomeScreen();
        void navigateToLoginScreen();
        void showLoading();
        void hideLoading();
        void showMessage(int messageId);
        void showUsernameError();
        void showEmailError();
        void showPasswordError();
        void showConfirmPasswordError();
        void removeUsernameError();
        void removeEmailError();
        void removePasswordError();
        void removeConfirmPasswordError();
    }

    void setView(SignUpView view);
    void removeView();
    void onGoToLoginClick();
    void onGoToHome();
    void onSignUpClick(String username, String email, String password, String confirmPassword);
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
