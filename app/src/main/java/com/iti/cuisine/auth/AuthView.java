package com.iti.cuisine.auth;

import androidx.credentials.Credential;

import io.reactivex.rxjava3.core.Single;

public interface AuthView {
        void navigateToHomeScreen();
        void showLoading();
        void hideLoading();
        void showMessage(int messageId);
        Single<Credential> getGoogleCredentials();
}

