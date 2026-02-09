package com.iti.cuisine.plans;

import androidx.annotation.Nullable;

import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class PlanPresenterImpl implements PlanPresenter {

    private final BehaviorSubject<Long> selectedDate = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private Disposable planMealsDisposable;
    private final MealRepo mealRepo;

    @Nullable
    private PlanView view;

    public static PlanPresenterImpl createNewInstance() {
        return new PlanPresenterImpl(MealRepoImpl.getInstance());
    }

    private PlanPresenterImpl(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
    }

    @Override
    public void setView(PlanView view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        stopListeningToData();
        view = null;
    }

    private void stopListeningToData() {
        //disposables.remove(planMealsDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
