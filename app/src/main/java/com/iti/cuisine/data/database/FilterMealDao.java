package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.FilterMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FilterMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FilterMealEntity filterMeal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<FilterMealEntity> filterMeals);

    @Query("SELECT * FROM filter_meals")
    Flowable<List<FilterMealEntity>> getAllFilterMeals();

    @Query("SELECT * FROM filter_meals WHERE id = :mealId")
    Flowable<FilterMealEntity> getFilterById(String mealId);

    @Query("DELETE FROM filter_meals WHERE id = :mealId")
    Completable deleteFilterById(String mealId);

}
