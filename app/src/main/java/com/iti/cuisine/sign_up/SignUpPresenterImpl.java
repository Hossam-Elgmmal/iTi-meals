package com.iti.cuisine.sign_up;

import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView view;
    private AuthRepo authRepo;

    public SignUpPresenterImpl(SignUpView view) {
        this.view = view;
        authRepo = new AuthRepoImpl();
    }

    @Override
    public void onGoToLoginClick() {
        view.navigateToLoginScreen();
    }

    @Override
    public void onSignUpClick(String username, String email, String password, String confirmPassword) {
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
