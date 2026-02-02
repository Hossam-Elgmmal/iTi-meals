package com.iti.cuisine.data.auth;


import android.app.Application;

import androidx.credentials.Credential;

import io.reactivex.rxjava3.core.Single;

public interface AuthRepo {

    boolean isUserLoggedIn();
    Single<AuthResult> signUpWithEmailAndPassword(String username, String email, String password);
    Single<AuthResult> signInWithGoogle(Credential credential);
    Single<AuthResult> signInAnonymously();
    void signOut(Application application);
}
