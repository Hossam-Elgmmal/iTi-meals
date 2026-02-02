package com.iti.cuisine.sign_up;

import android.util.Patterns;

import com.iti.cuisine.Constants;
import com.iti.cuisine.R;
import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;
import com.iti.cuisine.data.auth.AuthResult;
import com.iti.cuisine.utils.presenter.Presenter;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SignUpPresenterImpl implements SignUpPresenter, Presenter {

    @Nullable
    private SignUpView view;
    private final AuthRepo authRepo;

    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.createDefault(false);
    private final BehaviorSubject<Boolean> isUserAuthenticated = BehaviorSubject.createDefault(false);
    private final CompositeDisposable disposables = new CompositeDisposable();
    private Disposable loadingDisposable;
    private Disposable userDisposable;

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
        listenToAuthenticatedUser();
    }

    @Override
    public void removeView() {
        stopListeningToEvents();
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
        disposables.add(loadingDisposable);
    }

    private void listenToAuthenticatedUser() {
        userDisposable = isUserAuthenticated.subscribe(isAuthenticated -> {
            if (isAuthenticated && view != null) {
                view.navigateToHomeScreen();
            }
        });
        disposables.add(userDisposable);
    }

    private void stopListeningToEvents() {
        disposables.remove(loadingDisposable);
        disposables.remove(userDisposable);
    }

    @Override
    public void onGoToLoginClick() {
        if (view != null) {
            view.navigateToLoginScreen();
        }
    }

    @Override
    public void onSignUpClick(String username, String email, String password, String confirmPassword) {

        boolean isValid = validate(username, email, password, confirmPassword);

        if (!isValid) return;

        Disposable signUpDisposable = authRepo.signUpWithEmailAndPassword(username, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> showLoading.onNext(true))
                .doFinally(() -> showLoading.onNext(false))
                .subscribe(
                        authResult -> {
                            if (authResult == AuthResult.SUCCESS) {
                                isUserAuthenticated.onNext(true);
                            }
                            if (view != null) {
                                view.showMessage(authResult.getMessageId());
                            }
                        }
                );
        disposables.add(signUpDisposable);
    }

    private boolean validate(String username, String email, String password, String confirmPassword) {
        boolean usernameError = false;
        boolean emailError = false;
        boolean passwordError = false;
        boolean confirmPasswordError = false;

        if (view == null) return false;
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
        if (view == null) return;

        Disposable googleDisposable = view.getGoogleCredentials()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> showLoading.onNext(true))
                .doFinally(() -> showLoading.onNext(false))
                .subscribe(
                        credential -> {
                            Disposable googleLoginDisposable = authRepo.signInWithGoogle(credential)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnSubscribe(ignored -> showLoading.onNext(true))
                                    .doFinally(() -> showLoading.onNext(false))
                                    .subscribe(authResult -> {
                                        if (authResult == AuthResult.SUCCESS) {
                                            isUserAuthenticated.onNext(true);
                                        }
                                        if (view != null) {
                                            view.showMessage(authResult.getMessageId());
                                        }
                                    });

                            disposables.add(googleLoginDisposable);
                        },
                        t -> {
                            AuthResult result = AuthResult.fromException(t);
                            if (view != null) {
                                view.showMessage(result.getMessageId());
                            }
                        }
                );
        disposables.add(googleDisposable);
    }

    @Override
    public void onGuestLoginClick() {
        Disposable guestLoginDisposable = authRepo.signInAnonymously()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> showLoading.onNext(true))
                .doFinally(() -> showLoading.onNext(false))
                .subscribe(authResult -> {
                    if (authResult == AuthResult.SUCCESS) {
                        isUserAuthenticated.onNext(true);
                    }
                    if (view != null) {
                        view.showMessage(authResult.getMessageId());
                    }
                });
        disposables.add(guestLoginDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
