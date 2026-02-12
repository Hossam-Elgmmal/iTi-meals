package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealWithIngredients;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealEntity meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MealEntity> meals);

    @Transaction
    @Query("SELECT * FROM meals WHERE id = :mealId")
    Flowable<MealWithIngredients> getMealWithIngredients(String mealId);

    @Query("SELECT * FROM meals WHERE title LIKE :letter || '%'")
    Flowable<List<MealEntity>> getMealsByFirstLetter(String letter);

    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAllMeals();

    @Query("DELETE FROM meals WHERE id = :mealId")
    Completable deleteMealById(String mealId);

    @Query("DELETE FROM meals")
    Completable deleteAllMeals();
}