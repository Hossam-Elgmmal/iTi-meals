package com.iti.cuisine.data.database_models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(tableName = "mealIngredients", primaryKeys = {"mealId", "title"})
public class MealIngredientEntity {


    @ColumnInfo(name = "mealId")
    private String mealId;

    @ColumnInfo(name = "title")
    private String title;

    private String measure;

    private String thumbnail;

}
