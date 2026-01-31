package com.iti.cuisine.login;

import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;
    private AuthRepo authRepo;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        authRepo = new AuthRepoImpl();
    }

    @Override
    public void onLoginClick(String email, String password) {
        //todo
    }

    @Override
    public void onSignUpClick() {
        //todo
    }

    @Override
    public void onForgotPasswordClick() {
        //todo
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
