package com.iti.cuisine.auth.login;

import com.iti.cuisine.auth.AuthView;
import com.iti.cuisine.utils.presenter.Presenter;

public interface LoginPresenter extends Presenter {

    interface LoginView extends AuthView {
        void navigateToSignUpScreen();
        void showEmailError();
        void showPasswordError();
        void removeEmailError();
        void removePasswordError();
    }

    void setView(LoginView view);
    void removeView();
    void onLoginClick(String email, String password);
    void onGoToSignUpClick();
    void onGoogleLoginClick();
    void onGuestLoginClick();
}
