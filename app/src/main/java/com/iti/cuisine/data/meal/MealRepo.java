package com.iti.cuisine.data.meal;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealWithIngredients;
import com.iti.cuisine.data.database_models.PlanMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepo {

    Single<String> fetchRandomMealId();
    Flowable<MealWithIngredients> getMealWithIngredientsById(String id);

    Single<String> fetchMealsByLetter();
    Flowable<List<MealEntity>> getMealsByLetter(String letter);

    Completable fetchMealById(String mealId);
    Flowable<List<FavoriteMealEntity>> getFavoriteMealById(String mealId);
    Completable insertFavoriteMeal(FavoriteMealEntity favoriteMealEntity);
    Completable deleteFavoriteMealById(String id);

    Single<List<PlanMealEntity>> getSinglePlanMealByDate(long date);
    Completable insertPlanMeal(PlanMealEntity planMealEntity);

    Flowable<List<PlanMealEntity>> getPlanMealByDate(long date);

    Completable deletePlanMealById(PlanMealEntity mealEntity);
}
