package com.iti.cuisine.data.meal;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealWithIngredients;

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
}
