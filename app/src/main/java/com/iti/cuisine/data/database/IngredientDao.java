package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.IngredientEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientEntity ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IngredientEntity> ingredients);

    @Query("SELECT * FROM ingredients")
    Flowable<List<IngredientEntity>> getAllIngredients();

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    Flowable<IngredientEntity> getIngredientById(String ingredientId);

    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
    Completable deleteIngredientById(String ingredientId);

    @Query("DELETE FROM ingredients")
    Completable deleteAllIngredients();
}