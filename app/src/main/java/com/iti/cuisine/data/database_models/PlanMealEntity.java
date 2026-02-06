package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "planMeals")
public class PlanMealEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String thumbnail;

    private long date;

    public PlanMealEntity(@NonNull String id, @NonNull String title, @NonNull String thumbnail, long date) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
