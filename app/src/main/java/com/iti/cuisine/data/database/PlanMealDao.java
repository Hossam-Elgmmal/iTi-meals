package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.PlanMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlanMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlanMealEntity planMeal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<PlanMealEntity> planMeals);

    @Query("SELECT * FROM planMeals")
    Flowable<List<PlanMealEntity>> getAllPlanMeals();

    @Query("SELECT * FROM planMeals WHERE id = :planMealId")
    Flowable<PlanMealEntity> getPlanMealById(String planMealId);

    @Query("SELECT * FROM planMeals WHERE date BETWEEN :startDate AND :endDate")
    Flowable<List<PlanMealEntity>> getPlanMealsByDateRange(long startDate, long endDate);

    @Query("DELETE FROM planMeals WHERE id = :planMealId")
    Completable deletePlanMealById(String planMealId);

}