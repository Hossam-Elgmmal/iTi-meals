package com.iti.cuisine.data.meal;

import com.iti.cuisine.data.database_models.CategoryEntity;
import com.iti.cuisine.data.database_models.CountryEntity;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.IngredientEntity;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealWithIngredients;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.search.SearchItem;

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

    Flowable<List<IngredientEntity>> getAllIngredients();

    Flowable<List<CategoryEntity>> getAllCategories();

    Flowable<List<CountryEntity>> getAllCountries();

    Completable fetchCategories();

    Completable fetchCountries();

    Completable fetchIngredients();

    Single<List<SearchItem>> fetchMealsByCategory(String title);

    Single<List<SearchItem>> fetchMealsByCountry(String title);

    Single<List<SearchItem>> fetchMealsByIngredient(String title);
}
