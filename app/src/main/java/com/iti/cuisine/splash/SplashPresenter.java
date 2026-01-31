package com.iti.cuisine.splash;

public interface SplashPresenter {

    interface SplashView {

        void navigateToLoginScreen();

        void navigateToHomeScreen();

    }

    void navigateToNextScreen();

}
