package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "planMeals")
public class PlanMealEntity {

    @NonNull
    @PrimaryKey
    private String id;

    private String title;

    private String thumbnail;

    private long date;

    public PlanMealEntity(@NonNull String id, String title, String thumbnail, long date) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
