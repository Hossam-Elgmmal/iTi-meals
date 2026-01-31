package com.iti.cuisine.splash;

import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;

public class SplashPresenterImpl implements SplashPresenter{

    private final SplashView view;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
    }

    @Override
    public void navigateToNextScreen() {
        if (isUserLoggedIn()) {
            view.navigateToHomeScreen();
        } else {
            view.navigateToLoginScreen();
        }
    }

    private boolean isUserLoggedIn() {
        AuthRepo authRepo = new AuthRepoImpl();

        return authRepo.isUserLoggedIn();
    }

}
