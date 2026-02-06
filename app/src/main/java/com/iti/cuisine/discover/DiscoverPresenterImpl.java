package com.iti.cuisine.discover;

import android.util.Log;

import androidx.annotation.Nullable;

import com.iti.cuisine.Constants;
import com.iti.cuisine.data.meal.MealErrorResult;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class DiscoverPresenterImpl implements DiscoverPresenter {

    private final BehaviorSubject<String> randomMealId = BehaviorSubject.create();
    private final BehaviorSubject<String> todayLetter = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();

    private Disposable randomMealDisposable;
    private Disposable todayLetterDisposable;
    private Disposable loadingDisposable;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MealRepo mealRepo;

    @Nullable
    private DiscoverView view;

    public static DiscoverPresenterImpl createNewInstance() {
        return new DiscoverPresenterImpl(MealRepoImpl.getInstance());
    }

    private DiscoverPresenterImpl(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
        refreshData();
    }

    @Override
    public void refreshData() {
        getRandomMealIdThenTodayLetter();
    }

    private void getRandomMealIdThenTodayLetter() {
        Disposable disposable = mealRepo.fetchRandomMealId()
                .doOnSubscribe(d -> showLoading.onNext(true))
                .flatMap(id -> {
                    randomMealId.onNext(id);
                    return mealRepo.fetchMealsByLetter();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(letter -> {
                    showLoading.onNext(false);
                    todayLetter.onNext(letter);
                }, t -> {
                    showLoading.onNext(false);
                    if (view != null) {
                        view.showMessage(
                                MealErrorResult.fromThrowable(t).getMessageId()
                        );
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void setView(DiscoverView view) {
        this.view = view;
        listenToRandomMealId();
        listenToTodayMeals();
        listenToLoading();
    }

    private void listenToRandomMealId() {
        randomMealDisposable = randomMealId
                .toFlowable(BackpressureStrategy.LATEST)
                .switchMap(id ->
                        mealRepo.getMealWithIngredientsById(id).take(1)
                )
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealWithIngredients -> {
                    if (view != null) {
                        view.setRandomMeal(mealWithIngredients.getMeal());
                        view.setFirstIngredient(mealWithIngredients.getIngredients().get(0));
                        view.setSecondIngredient(mealWithIngredients.getIngredients().get(1));
                    }
                }, t -> Log.e(Constants.TAG, "listenToRandomMealId: ", t));
        disposables.add(randomMealDisposable);
    }

    private void listenToTodayMeals() {
        todayLetterDisposable = todayLetter
                .toFlowable(BackpressureStrategy.LATEST)
                .switchMap(letter ->
                        mealRepo.getMealsByLetter(letter).take(1)
                )
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                  if (view != null) {
                        view.setTodayMeals(meals);
                    }
                }, t -> Log.e(Constants.TAG, "listenToTodayMeals: ", t));
        disposables.add(todayLetterDisposable);
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

    @Override
    public void removeView() {
        stopListeningToDiscoverData();
        view = null;
    }

    private void stopListeningToDiscoverData() {
        disposables.remove(randomMealDisposable);
        disposables.remove(todayLetterDisposable);
        disposables.remove(loadingDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
