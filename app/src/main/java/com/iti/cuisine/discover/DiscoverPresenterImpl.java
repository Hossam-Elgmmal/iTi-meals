package com.iti.cuisine.discover;

import androidx.annotation.Nullable;

import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class DiscoverPresenterImpl implements DiscoverPresenter{

    private final BehaviorSubject<String> randomMealId = BehaviorSubject.createDefault("");
    private final BehaviorSubject<String> firstIngredientTitle = BehaviorSubject.createDefault("");
    private final BehaviorSubject<String> secondIngredientTitle = BehaviorSubject.createDefault("");
    private final BehaviorSubject<String> todayLetter = BehaviorSubject.createDefault("");

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
        getRandomMeal();
        getTodayMeals();
    }

    private void getRandomMeal() {
        //todo
    }

    private void getTodayMeals() {
        //todo
    }

    @Override
    public void setView(DiscoverView view) {
        this.view = view;
        listenToDiscoverData();
    }

    private void listenToDiscoverData() {
        //todo
    }

    @Override
    public void removeView() {
        stopListeningToDiscoverData();
        view = null;
    }

    private void stopListeningToDiscoverData() {
        //todo
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
