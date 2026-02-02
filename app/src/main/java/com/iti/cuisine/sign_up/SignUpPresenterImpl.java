package com.iti.cuisine.sign_up;

import android.util.Patterns;

import com.iti.cuisine.Constants;
import com.iti.cuisine.R;
import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;
import com.iti.cuisine.utils.presenter.Presenter;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SignUpPresenterImpl implements SignUpPresenter, Presenter {

    @Nullable
    private SignUpView view;
    private AuthRepo authRepo;

    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.createDefault(false);
    private final CompositeDisposable disposable = new CompositeDisposable();
    private Disposable loadingDisposable;

    public static SignUpPresenterImpl createNewInstance() {
        return new SignUpPresenterImpl(new AuthRepoImpl());
    }

    public SignUpPresenterImpl(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public void setView(@NonNull SignUpView view) {
        this.view = view;
        listenToLoading();
    }

    @Override
    public void removeView() {
        stopListeningToLoading();
        view = null;
    }

    private void listenToLoading() {
        loadingDisposable = showLoading.subscribe(show -> {
            if (view != null) {
                if (show) {
                    view.showLoading();
                } else {
                    view.hideLoading();
                }
            }
        });
        disposable.add(loadingDisposable);
    }

    private void stopListeningToLoading() {
        disposable.remove(loadingDisposable);
    }

    @Override
    public void onGoToLoginClick() {
        if (view != null) {
            view.navigateToLoginScreen();
        }
    }

    @Override
    public void onGoToHome() {
        if (view != null) {
            view.navigateToHomeScreen();
        }
    }

    @Override
    public void onSignUpClick(String username, String email, String password, String confirmPassword) {

        boolean isValid = validate(username, email, password, confirmPassword);

        //todo
    }

    private boolean validate(String username, String email, String password, String confirmPassword) {
        boolean usernameError = false;
        boolean emailError = false;
        boolean passwordError = false;
        boolean confirmPasswordError = false;

        assert view != null;
        int messageId = -1;

        if (password.equals(confirmPassword)) {
            view.removeConfirmPasswordError();
        } else {
            confirmPasswordError = true;
            view.showConfirmPasswordError();
            messageId = R.string.passwords_do_not_match;
        }

        if (password.length() >= 8 && password.matches(Constants.PASSWORD_PATTERN)) {
            view.removePasswordError();
        } else {
            passwordError = true;
            view.showPasswordError();
            messageId = R.string.invalid_password;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.removeEmailError();
        } else {
            emailError = true;
            view.showEmailError();
            messageId = R.string.invalid_email;
        }

        if (username.length() >= 3) {
            view.removeUsernameError();
        } else {
            usernameError = true;
            view.showUsernameError();
            messageId = R.string.username_is_too_short;
        }

        boolean isError = usernameError || emailError || passwordError || confirmPasswordError;

        if (isError) {
            view.showMessage(messageId);
        }

        return !isError;
    }

    @Override
    public void onGoogleLoginClick() {
        //todo
    }

    @Override
    public void onGuestLoginClick() {
        //todo
    }

    @Override
    public void destroy() {
        disposable.clear();
    }
}
