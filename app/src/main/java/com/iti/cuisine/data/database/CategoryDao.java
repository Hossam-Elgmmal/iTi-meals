package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryEntity category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryEntity> categories);

    @Query("SELECT * FROM categories")
    Flowable<List<CategoryEntity>> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    Flowable<CategoryEntity> getCategoryById(String categoryId);

    @Query("DELETE FROM categories WHERE id = :categoryId")
    Completable deleteCategoryById(String categoryId);

    @Query("DELETE FROM categories")
    Completable deleteAllCategories();
}
