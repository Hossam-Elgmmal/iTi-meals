package com.iti.cuisine.data.auth;

import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.NoCredentialException;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.iti.cuisine.R;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

public enum AuthResult {
    SUCCESS(R.string.welcome_aboard),
    EMAIL_ALREADY_IN_USE(R.string.this_email_is_already_registered),
    INVALID_CREDENTIALS(R.string.invalid_email_or_password),
    WEAK_PASSWORD(R.string.password_is_too_weak),
    INVALID_EMAIL(R.string.invalid_email_address),
    INVALID_USER(R.string.user_account_not_found),
    NETWORK_ERROR(R.string.no_internet_connection),
    TIMEOUT_ERROR(R.string.request_timed_out_please_try_again),
    TOO_MANY_REQUESTS(R.string.too_many_attempts_please_try_again_later),
    GOOGLE_SIGN_IN_FAILED(R.string.google_sign_in_failed),
    GOOGLE_SIGN_IN_CANCELLED(R.string.google_sign_in_was_cancelled),
    GOOGLE_PLAY_SERVICES_UNAVAILABLE(R.string.google_play_services_is_not_available),
    UNKNOWN_ERROR(R.string.registration_failed_please_try_again),
    USER_DISABLED(R.string.this_account_has_been_disabled),
    GOOGLE_ACCOUNT_COLLISION(R.string.this_google_account_is_already_linked_to_another_user),
    GOOGLE_CREDENTIAL_ERROR(R.string.google_credential_error),
    GOOGLE_NO_ACCOUNTS(R.string.no_google_accounts_found),;


    private final int messageId;

    AuthResult(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public static AuthResult fromException(Throwable error) {
        if (error instanceof FirebaseAuthUserCollisionException) {
            return EMAIL_ALREADY_IN_USE;
        } else if (error instanceof FirebaseAuthWeakPasswordException) {
            return WEAK_PASSWORD;
        } else if (error instanceof FirebaseAuthInvalidCredentialsException) {
            return INVALID_CREDENTIALS;
        } else if (error instanceof FirebaseAuthInvalidUserException) {
            return INVALID_USER;
        } else if (error instanceof FirebaseNetworkException) {
            return NETWORK_ERROR;
        } else if (error instanceof TimeoutException || error instanceof SocketTimeoutException) {
            return TIMEOUT_ERROR;
        } else if (error instanceof FirebaseTooManyRequestsException) {
            return TOO_MANY_REQUESTS;
        } else if (error instanceof GetCredentialCancellationException) {
                return GOOGLE_SIGN_IN_CANCELLED;
        } else if (error instanceof NoCredentialException) {
            return GOOGLE_NO_ACCOUNTS;
        } else if (error instanceof GetCredentialException) {
            return GOOGLE_CREDENTIAL_ERROR;
        }

        if (error instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) error).getErrorCode();

            switch (errorCode) {
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    return EMAIL_ALREADY_IN_USE;
                case "ERROR_WEAK_PASSWORD":
                    return WEAK_PASSWORD;
                case "ERROR_INVALID_EMAIL":
                    return INVALID_EMAIL;
                case "ERROR_WRONG_PASSWORD":
                case "ERROR_USER_NOT_FOUND":
                    return INVALID_CREDENTIALS;
                case "ERROR_USER_DISABLED":
                    return USER_DISABLED;
                case "ERROR_TOO_MANY_REQUESTS":
                    return TOO_MANY_REQUESTS;
                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                    return GOOGLE_ACCOUNT_COLLISION;
                default:
                    return UNKNOWN_ERROR;
            }
        }

        if (error instanceof ApiException) {
            ApiException apiException = (ApiException) error;
            int statusCode = apiException.getStatusCode();

            switch (statusCode) {
                case CommonStatusCodes.CANCELED:
                    return GOOGLE_SIGN_IN_CANCELLED;
                case CommonStatusCodes.NETWORK_ERROR:
                    return NETWORK_ERROR;
                case CommonStatusCodes.TIMEOUT:
                    return TIMEOUT_ERROR;
                case CommonStatusCodes.API_NOT_CONNECTED:
                    return GOOGLE_PLAY_SERVICES_UNAVAILABLE;
                default:
                    return GOOGLE_SIGN_IN_FAILED;
            }
        }

        return UNKNOWN_ERROR;
    }
}