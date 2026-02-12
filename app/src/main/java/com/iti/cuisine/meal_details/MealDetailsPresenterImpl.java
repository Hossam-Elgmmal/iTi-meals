package com.iti.cuisine.meal_details;

import static com.iti.cuisine.Constants.TAG;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealType;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.data.mappers.FavoriteMealMapper;
import com.iti.cuisine.data.mappers.PlanMealMapper;
import com.iti.cuisine.data.meal.MealErrorResult;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;
import com.iti.cuisine.data.ui_models.MealStep;
import com.iti.cuisine.data.user.UserData;
import com.iti.cuisine.data.user.UserRepo;
import com.iti.cuisine.data.user.UserRepoImpl;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {

    private final BehaviorSubject<String> currentMealId = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> isInFavorite = BehaviorSubject.createDefault(false);

    private Disposable mealDisposable;
    private Disposable loadingDisposable;
    private Disposable isInFavoriteDisposable;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MealRepo mealRepo;
    private final UserRepo userRepo;

    @Nullable
    private MealDetailsView view;

    public static MealDetailsPresenterImpl createNewInstance() {
        return new MealDetailsPresenterImpl(MealRepoImpl.getInstance(), new UserRepoImpl());
    }

    private MealDetailsPresenterImpl(MealRepo mealRepo,UserRepo userRepo) {
        this.mealRepo = mealRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void setMealId(String mealId) {
        currentMealId.onNext(mealId);
    }

    @Override
    public void setView(MealDetailsView view) {
        this.view = view;
        listenToMealId();
        listenToLoading();
    }

    @Override
    public void refreshData(String mealId) {
        currentMealId.onNext(mealId);
        getMealById(mealId);
        listenToFavorite(mealId);
    }

    private void getMealById(String mealId) {
        Disposable disposable = mealRepo.fetchMealById(mealId)
                .doOnSubscribe(d -> showLoading.onNext(true))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> showLoading.onNext(false))
                .onErrorComplete(
                        t -> {
                            if (view != null) {
                                view.showMessage(
                                        MealErrorResult.fromThrowable(t).getMessageId()
                                );
                            }
                            return true;
                        }
                )
                .subscribe();
        disposables.add(disposable);
    }

    private void listenToMealId() {
        mealDisposable = currentMealId
                .distinctUntilChanged()
                .toFlowable(BackpressureStrategy.LATEST)
                .switchMap(id -> mealRepo.getMealWithIngredientsById(id).take(1))
                .distinctUntilChanged()
                .map(meal -> new Pair<>(meal, MealStep.makeSteps(meal.getMeal().getInstructions())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fullMeal -> {
                    if (view != null) {
                        view.setMeal(fullMeal.first, fullMeal.second);
                    }
                }, t -> Log.e(TAG, "listenToMealId: ", t));
        disposables.add(mealDisposable);
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

    private void listenToFavorite(String mealId) {
        isInFavoriteDisposable = mealRepo.getFavoriteMealById(mealId)
                .map(meals -> !meals.isEmpty())
                .onErrorReturnItem(false)
                .doOnNext(isInFavorite::onNext)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    if (view != null) {
                        view.setIsFavorite(isFavorite);
                    }
                }, t -> Log.e(TAG, "getIsInFavorite: ", t));
        disposables.add(isInFavoriteDisposable);
    }

    @Override
    public boolean isUserGuest() {
        UserData userData = userRepo.getUserData();
        return userData.isGuest();
    }

    @Override
    public void toggleFavorite(MealEntity mealEntity) {

        UserData userData = userRepo.getUserData();
        if (userData.isGuest()) {
            if (view != null) {
                view.showMessage(R.string.you_re_in_guest_mode_sign_in_to_save_your_favorites_and_meal_plans);
            }
            return;
        }

        Disposable disposable;
        if (isInFavorite.getValue()) {
            disposable = mealRepo.deleteFavoriteMealById(mealEntity.getId())
                    .doOnError(t -> Log.e(TAG, "listenToFavorite: ", t))
                    .onErrorComplete()
                    .subscribe();
        } else {
            disposable = mealRepo.insertFavoriteMeal(FavoriteMealMapper.mapToEntity(mealEntity))
                    .doOnError(t -> Log.e(TAG, "listenToFavorite: ", t))
                    .onErrorComplete()
                    .subscribe();
        }
        disposables.add(disposable);
    }

    @Override
    public void saveMealToPlan(MealEntity mealEntity, MealType mealType, long date) {
        Disposable disposable =
                mealRepo.insertPlanMeal(PlanMealMapper.mapToEntity(mealEntity, mealType, date))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                view.showMessage(R.string.meal_added_to_plan);
                            }
                        }, t -> Log.e(TAG, "saveMealToPlan: ", t));
        disposables.add(disposable);
    }

    @Override
    public void getPlanMealsByDateAndShowConfirmDialog(MealEntity meal, long longDate) {
        Disposable disposable = mealRepo.getSinglePlanMealByDate(longDate)
                .map(list -> list.stream()
                        .collect(
                                Collectors.toMap(PlanMealEntity::getMealType, planMeal -> planMeal)
                        )
                )
                .onErrorReturnItem(Map.of())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    Date datex = new Date(longDate);

                    SimpleDateFormat formatter =
                            new SimpleDateFormat("MMM dd,\nyyyy", Locale.ENGLISH);

                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

                    String formatted = formatter.format(datex);

                    if (view != null) {
                        view.showConfirmPlanDialog(meal, longDate, formatted, meals);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void removeView() {
        stopListeningToData();
        view = null;
    }

    private void stopListeningToData() {
        disposables.remove(mealDisposable);
        disposables.remove(loadingDisposable);
        disposables.remove(isInFavoriteDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
