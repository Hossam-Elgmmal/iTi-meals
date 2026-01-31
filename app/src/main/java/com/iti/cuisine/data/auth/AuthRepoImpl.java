package com.iti.cuisine.data.auth;

import com.google.firebase.auth.FirebaseAuth;

public class AuthRepoImpl implements AuthRepo {

    private final FirebaseAuth auth;

    public AuthRepoImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

}
