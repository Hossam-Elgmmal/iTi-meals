package com.iti.cuisine.data.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.rxjava3.core.Single;

public class AuthRepoImpl implements AuthRepo {

    private final FirebaseAuth auth;

    public AuthRepoImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public Single<AuthResult> signUpWithEmailAndPassword(String username, String email, String password) {
        return Single.create(emitter -> auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    FirebaseUser user = result.getUser();
                    if (user != null) {
                        user.updateProfile(
                                new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .build()
                        );
                    }
                    emitter.onSuccess(AuthResult.SUCCESS);
                })
                .addOnFailureListener(t -> {
                    AuthResult result = AuthResult.fromException(t);
                    emitter.onSuccess(result);
                }));
    }

    @Override
    public Single<AuthResult> signInAnonymously() {
        return Single.create(emitter -> auth.signInAnonymously()
                .addOnSuccessListener(result -> emitter.onSuccess(AuthResult.SUCCESS))
                .addOnFailureListener(t -> {
                    AuthResult result = AuthResult.fromException(t);
                    emitter.onSuccess(result);
                }));
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
