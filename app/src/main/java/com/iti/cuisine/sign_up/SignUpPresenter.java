package com.iti.cuisine.sign_up;

import androidx.credentials.Credential;

import io.reactivex.rxjava3.core.Single;

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
        Single<Credential> getGoogleCredentials();
    }

    void setView(SignUpView view);
    void removeView();
    void onGoToLoginClick();
    void onSignUpClick(String username, String email, String password, String confirmPassword);
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
