package com.iti.cuisine.splash;


import com.iti.cuisine.utils.presenter.Presenter;

public interface SplashPresenter extends Presenter {

    interface SplashView {

        void navigateToLoginScreen();

        void navigateToHomeScreen();

    }

    void navigateToNextScreen();

    void setView(SplashView view);

    void removeView();

}
