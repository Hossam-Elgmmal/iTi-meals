package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Objects;

@Entity(tableName = "mealIngredients", primaryKeys = {"mealId", "title"})
public class MealIngredientEntity {


    @NonNull
    @ColumnInfo(name = "mealId")
    private String mealId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    private String measure;

    @NonNull
    private String thumbnail;

    public MealIngredientEntity(@NonNull String mealId, @NonNull String title, @NonNull String measure, @NonNull String thumbnail) {
        this.mealId = mealId;
        this.title = title;
        this.measure = measure;
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealIngredientEntity that = (MealIngredientEntity) o;
        return Objects.equals(mealId, that.mealId) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, title);
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

    @NonNull
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(@NonNull String measure) {
        this.measure = measure;
    }

    @NonNull
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@NonNull String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
