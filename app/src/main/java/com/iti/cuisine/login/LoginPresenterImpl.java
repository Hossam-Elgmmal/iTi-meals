package com.iti.cuisine.login;

import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView view;
    private AuthRepo authRepo;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        authRepo = new AuthRepoImpl();
    }

    @Override
    public void onLoginClick(String email, String password) {
        //todo
    }

    @Override
    public void onGoToSignUpClick() {
        view.navigateToSignUpScreen();
    }

    @Override
    public void onForgotPasswordClick() {
        view.navigateToForgotPasswordScreen();
    }

    @Override
    public void onGoogleLoginClick() {
        //todo
    }

    @Override
    public void onGuestLoginClick() {
        //todo
    }
}
