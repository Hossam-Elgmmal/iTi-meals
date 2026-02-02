package com.iti.cuisine.utils.google_credentials;

import android.app.Activity;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.iti.cuisine.R;

import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Single;

public class GoogleSignInManager {

    public Single<Credential> getGoogleCredentials(Activity activity, CredentialManager credentialManager) {
        return Single.create(emitter -> {
            GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .build();

            GetCredentialRequest request = new GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build();

            credentialManager.getCredentialAsync(activity, request, new CancellationSignal(),
                    Executors.newSingleThreadExecutor(),
                    new CredentialManagerCallback<>() {
                        @Override
                        public void onResult(GetCredentialResponse getCredentialResponse) {
                            emitter.onSuccess(getCredentialResponse.getCredential());
                        }

                        @Override
                        public void onError(@NonNull GetCredentialException e) {
                            emitter.onError(e);
                        }
                    });
        });
    }
}
