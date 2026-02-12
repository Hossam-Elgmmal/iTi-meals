package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoriteMealEntity favoriteMeal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<FavoriteMealEntity> favoriteMeals);

    @Query("SELECT * FROM favoriteMeals")
    Flowable<List<FavoriteMealEntity>> getAllFavoriteMeals();

    @Query("SELECT * FROM favoriteMeals WHERE id = :mealId")
    Flowable<List<FavoriteMealEntity>> getFavoriteMealById(String mealId);

    @Query("DELETE FROM favoriteMeals WHERE id = :mealId")
    Completable deleteFavoriteMealById(String mealId);

    @Query("DELETE FROM favoriteMeals")
    Completable deleteAllFavoriteMeals();

    @Query("SELECT COUNT(*) FROM favoriteMeals")
    Flowable<Integer> getFavoriteCount();

    @Query("SELECT * FROM favoriteMeals")
    Single<List<FavoriteMealEntity>> getSingleAllFavoriteMeal();
}