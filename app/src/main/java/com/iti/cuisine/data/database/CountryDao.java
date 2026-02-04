package com.iti.cuisine.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.cuisine.data.database_models.CountryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(CountryEntity country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<CountryEntity> countries);

    @Query("SELECT * FROM countries")
    Flowable<List<CountryEntity>> getAllCountries();

    @Query("SELECT * FROM countries WHERE title = :title")
    Flowable<CountryEntity> getCountryByTitle(String title);

    @Query("DELETE FROM countries WHERE title = :title")
    Completable deleteCountryByTitle(String title);

}