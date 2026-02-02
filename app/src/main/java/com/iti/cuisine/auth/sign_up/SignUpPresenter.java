package com.iti.cuisine.auth.sign_up;

import com.iti.cuisine.auth.AuthView;
import com.iti.cuisine.utils.presenter.Presenter;

public interface SignUpPresenter extends Presenter {

    interface SignUpView extends AuthView {
        void navigateToLoginScreen();
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
    void onSignUpClick(String username, String email, String password, String confirmPassword);
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
