package com.iti.cuisine.data.auth;

import android.app.Application;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.exceptions.ClearCredentialException;

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.iti.cuisine.Constants;

import org.jspecify.annotations.NonNull;

import java.util.concurrent.Executors;

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


    public Single<AuthResult> signInWithGoogle(Credential credential) {
        return Single.create(emitter -> {
            if (credential instanceof CustomCredential
                    && credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {

                GoogleIdTokenCredential tokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
                AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenCredential.getIdToken(), null);

                auth.signInWithCredential(authCredential)
                        .addOnSuccessListener(result -> emitter.onSuccess(AuthResult.SUCCESS))
                        .addOnFailureListener(t -> {
                            AuthResult result = AuthResult.fromException(t);
                            emitter.onSuccess(result);
                        });

            } else {
                emitter.onSuccess(AuthResult.GOOGLE_SIGN_IN_FAILED);
            }
        });
    }

    @Override
    public void signOut(Application application) {
        auth.signOut();
        CredentialManager credentialManager = CredentialManager.create(application);
        credentialManager.clearCredentialStateAsync(
                new ClearCredentialStateRequest(),
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(@NonNull Void result) {
                    }

                    @Override
                    public void onError(@NonNull ClearCredentialException e) {
                        Log.e(Constants.TAG, "onError: Unable to clear credentials", e);
                    }
                });
    }
}
