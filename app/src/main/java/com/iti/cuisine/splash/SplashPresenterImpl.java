package com.iti.cuisine.splash;

import android.util.Log;

import androidx.annotation.NonNull;

import com.iti.cuisine.Constants;
import com.iti.cuisine.data.auth.AuthRepo;
import com.iti.cuisine.data.auth.AuthRepoImpl;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView view;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final BehaviorSubject<Boolean> shouldNavigate = BehaviorSubject.createDefault(false);

    private final Observable<Boolean> timerFinished = shouldNavigate.hide();

    private Disposable navigationDisposable;

    public SplashPresenterImpl() {
        disposable.add(
                Completable.timer(3500, TimeUnit.MILLISECONDS)
                        .subscribe(
                                () -> shouldNavigate.onNext(true)
                        )
        );
    }

    @Override
    public void setView(SplashView view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        view = null;
    }

    @Override
    public void navigateToNextScreen() {
        if (navigationDisposable != null) {
            disposable.remove(navigationDisposable);
        }
        navigationDisposable = navigateAfterTimer();
        disposable.add(navigationDisposable);
    }

    @NonNull
    private Disposable navigateAfterTimer() {
        return timerFinished.observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished -> {
                    if (finished && view != null) {
                        if (isUserLoggedIn()) {
                            view.navigateToHomeScreen();
                        } else {
                            view.navigateToLoginScreen();
                        }
                    }
                }, e -> Log.e(Constants.TAG, "navigateAfterTimer: ", e));
    }

    private boolean isUserLoggedIn() {
        AuthRepo authRepo = new AuthRepoImpl();

        return authRepo.isUserLoggedIn();
    }

    @Override
    public void destroy() {
        disposable.clear();
    }
}
