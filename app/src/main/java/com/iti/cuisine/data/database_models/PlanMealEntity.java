package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(tableName = "planMeals", primaryKeys = {"mealType", "date"})
public class PlanMealEntity {

    @NonNull
    @ColumnInfo(name = "mealType")
    private MealType mealType;

    @ColumnInfo(name = "date")
    private long date;

    @NonNull
    private String mealId;

    @NonNull
    private String title;

    @NonNull
    private String thumbnail;

    public PlanMealEntity(@NonNull MealType mealType, long date, @NonNull String mealId, @NonNull String title, @NonNull String thumbnail) {
        this.mealType = mealType;
        this.date = date;
        this.mealId = mealId;
        this.title = title;
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

    @NonNull
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@NonNull String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @NonNull
    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(@NonNull MealType mealType) {
        this.mealType = mealType;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
