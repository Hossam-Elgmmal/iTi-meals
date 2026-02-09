package com.iti.cuisine.plans;

import static com.iti.cuisine.Constants.TAG;

import android.util.Log;

import androidx.annotation.Nullable;

import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import java.util.Comparator;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
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
        listenToPlanMeals();
    }

    private void listenToPlanMeals() {
        planMealsDisposable = selectedDate
                .distinctUntilChanged()
                .toFlowable(BackpressureStrategy.LATEST)
                .switchMap(mealRepo::getPlanMealByDate)
                .map(list -> list.stream().sorted(
                                Comparator.comparingInt(o -> o.getMealType().ordinal())
                        ).collect(Collectors.toList())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (view != null) {
                        view.setMeals(list);
                    }
                }, t -> Log.e(TAG, "listenToPlanMeals: ", t));
        disposables.add(planMealsDisposable);
    }

    @Override
    public void setDate(long date) {
        selectedDate.onNext(date);
    }

    @Override
    public void removeView() {
        stopListeningToData();
        view = null;
    }

    @Override
    public void deleteMeal(PlanMealEntity mealEntity) {
        Disposable disposable =
                mealRepo.deletePlanMealById(mealEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                view.showDeletedMeal(mealEntity);
                            }
                        }, t -> Log.e(TAG, "deleteMeal: ", t));
        disposables.add(disposable);
    }

    @Override
    public void addPlanMeal(PlanMealEntity mealEntity) {
        Disposable disposable =
                mealRepo.insertPlanMeal(mealEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                view.showMessage(R.string.meal_added_to_plan);
                            }
                        }, t -> Log.e(TAG, "saveMealToPlan: ", t));
        disposables.add(disposable);
    }

    private void stopListeningToData() {
        disposables.remove(planMealsDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
