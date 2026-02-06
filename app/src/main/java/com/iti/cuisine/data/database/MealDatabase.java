package com.iti.cuisine.data.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iti.cuisine.data.database_models.CategoryEntity;
import com.iti.cuisine.data.database_models.CountryEntity;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.FilterMealEntity;
import com.iti.cuisine.data.database_models.IngredientEntity;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.data.database_models.PlanMealEntity;

@Database(entities = {CategoryEntity.class, CountryEntity.class,
        FavoriteMealEntity.class, IngredientEntity.class, MealEntity.class,
        MealIngredientEntity.class, PlanMealEntity.class, FilterMealEntity.class
}, version = 1)
public abstract class MealDatabase extends RoomDatabase {


    private static final String DATABASE_NAME = "meals.db";
    private static volatile MealDatabase instance;

    public static MealDatabase getInstance(Application application) {
        if (instance == null) {
            synchronized (MealDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application, MealDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract CategoryDao getCategoryDao();

    public abstract CountryDao getCountryDao();

    public abstract FavoriteDao getFavoriteDao();

    public abstract IngredientDao getIngredientDao();

    public abstract MealDao getMealDao();

    public abstract MealIngredientDao getMealIngredientDao();

    public abstract PlanMealDao getPlanMealDao();

    public abstract FilterMealDao getFilterMealDao();

}
