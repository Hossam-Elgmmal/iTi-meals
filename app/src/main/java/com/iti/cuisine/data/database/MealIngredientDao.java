package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.MealIngredientEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MealIngredientEntity mealIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<MealIngredientEntity> mealIngredients);

    @Query("SELECT * FROM mealIngredients")
    Flowable<List<MealIngredientEntity>> getAllMealIngredients();

    @Query("SELECT * FROM mealIngredients WHERE mealId = :mealId")
    Flowable<List<MealIngredientEntity>> getMealIngredientsByMealId(String mealId);

    @Query("DELETE FROM mealIngredients WHERE mealId = :mealId ")
    Completable deleteMealIngredient(String mealId);

    @Query("DELETE FROM mealIngredients WHERE mealId = :mealId")
    Completable deleteMealIngredientsByMealId(String mealId);

}