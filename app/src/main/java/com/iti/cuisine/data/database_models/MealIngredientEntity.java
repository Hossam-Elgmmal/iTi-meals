package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(tableName = "mealIngredients", primaryKeys = {"mealId", "title"})
public class MealIngredientEntity {


    @NonNull
    @ColumnInfo(name = "mealId")
    private String mealId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    private String measure;

    private String thumbnail;

    public MealIngredientEntity(@NonNull String mealId, @NonNull String title, String measure, String thumbnail) {
        this.mealId = mealId;
        this.title = title;
        this.measure = measure;
        this.thumbnail = thumbnail;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
