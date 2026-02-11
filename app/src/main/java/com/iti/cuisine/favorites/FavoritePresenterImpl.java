package com.iti.cuisine.favorites;

import static com.iti.cuisine.Constants.TAG;

import android.util.Log;

import androidx.annotation.Nullable;

import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import java.util.Comparator;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class FavoritePresenterImpl implements FavoritePresenter {

    private final BehaviorSubject<Long> selectedDate = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private Disposable favoriteMealsDisposable;
    private final MealRepo mealRepo;

    @Nullable
    private FavoriteView view;

    public static FavoritePresenterImpl createNewInstance() {
        return new FavoritePresenterImpl(MealRepoImpl.getInstance());
    }

    private FavoritePresenterImpl(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
    }

    @Override
    public void setView(FavoriteView view) {
        this.view = view;
        listenToFavoriteMeals();
    }

    private void listenToFavoriteMeals() {
        favoriteMealsDisposable = mealRepo.getAllFavoriteMeals()
                .map(list -> list.stream().sorted(
                                Comparator.comparingLong(FavoriteMealEntity::getInsertDate)
                        ).collect(Collectors.toList())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (view != null) {
                        view.setMeals(list);
                    }
                }, t -> Log.e(TAG, "listenToPlanMeals: ", t));
        disposables.add(favoriteMealsDisposable);
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
    public void deleteMeal(FavoriteMealEntity mealEntity) {
        Disposable disposable =
                mealRepo.deleteFavoriteMealById(mealEntity.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                view.showDeletedMeal(mealEntity);
                            }
                        }, t -> Log.e(TAG, "deleteFavoriteMeal: ", t));
        disposables.add(disposable);
    }

    @Override
    public void addFavoriteMeal(FavoriteMealEntity mealEntity) {
        Disposable disposable =
                mealRepo.insertFavoriteMeal(mealEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                view.showMessage(R.string.meal_added_to_plan);
                            }
                        }, t -> Log.e(TAG, "saveMealToFavorite: ", t));
        disposables.add(disposable);
    }

    private void stopListeningToData() {
        disposables.remove(favoriteMealsDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
