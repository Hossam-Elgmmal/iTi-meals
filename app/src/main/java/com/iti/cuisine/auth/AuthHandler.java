package com.iti.cuisine.auth;

import android.util.Log;

import com.iti.cuisine.Constants;
import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthResult;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;
import com.iti.cuisine.data.user.FireFavoriteMapper;
import com.iti.cuisine.data.user.FirePlanMapper;
import com.iti.cuisine.data.user.UserRepo;
import com.iti.cuisine.data.user.UserRepoImpl;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AuthHandler {

    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.createDefault(false);
    private final BehaviorSubject<Boolean> isUserAuthenticated = BehaviorSubject.createDefault(false);
    private final CompositeDisposable disposables = new CompositeDisposable();
    private Disposable loadingDisposable;
    private Disposable userDisposable;

    private final AuthRepo authRepo;

    private AuthView view;

    public AuthHandler(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    public void setView(AuthView view) {
        this.view = view;
        listenToLoading();
        listenToAuthenticatedUser();
    }

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


    public void executeAuthOperation(Single<AuthResult> authOperation) {
        Disposable disposable = authOperation
                .flatMap(authResult ->
                        restoreData().andThen(Single.just(authResult))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> showLoading.onNext(true))
                .doFinally(() -> showLoading.onNext(false))
                .subscribe(
                        this::handleAuthSuccess,
                        this::handleAuthError
                );
        disposables.add(disposable);
    }

    private void handleAuthSuccess(AuthResult authResult) {
        if (authResult == AuthResult.SUCCESS) {
            isUserAuthenticated.onNext(true);
        }
        if (view != null) {
            view.showMessage(authResult.getMessageId());
        }
    }

    private void handleAuthError(Throwable throwable) {
        AuthResult result = AuthResult.fromException(throwable);
        if (view != null) {
            view.showMessage(result.getMessageId());
        }
    }

    public void handleGoogleLogin() {
        if (view == null) return;
        Disposable disposable = view.getGoogleCredentials()
                .flatMap(authRepo::signInWithGoogle)
                .flatMap(authResult ->
                        restoreData().andThen(Single.just(authResult))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> showLoading.onNext(true))
                .doFinally(() -> showLoading.onNext(false))
                .subscribe(
                        this::handleAuthSuccess,
                        this::handleAuthError
                );
        disposables.add(disposable);
    }

    public void handleGuestLogin() {
        executeAuthOperation(authRepo.signInAnonymously());
    }

    public void destroy() {
        disposables.clear();
    }

    public Completable restoreData() {
        UserRepo userRepo = new UserRepoImpl();
        MealRepo mealRepo = MealRepoImpl.getInstance();

        return userRepo.getFireUser()
                .flatMapCompletable(user -> mealRepo.saveAllFavoriteMeals(user.getFavorites().stream().map(FireFavoriteMapper::fromFireFavoriteMeal).collect(Collectors.toList()))
                        .andThen(mealRepo.saveAllPlanMeals(user.getPlannedMeals().stream()
                                .map(FirePlanMapper::fromFirePlanMeal)
                                .collect(Collectors.toList()))))
                .doOnError(e -> Log.e(Constants.TAG, "uploadData: ", e))
                .onErrorComplete();
    }
}
