package com.iti.cuisine.data.auth;


import io.reactivex.rxjava3.core.Single;

public interface AuthRepo {

    boolean isUserLoggedIn();
    Single<AuthResult> signUpWithEmailAndPassword(String username, String email, String password);
    void signOut();
}
