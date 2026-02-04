package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MealEntity meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<MealEntity> meals);

    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAllMeals();

    @Query("SELECT * FROM meals WHERE id = :mealId")
    Flowable<MealEntity> getMealById(String mealId);

    @Query("DELETE FROM meals WHERE id = :mealId")
    Completable deleteMealById(String mealId);

}