package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "ingredients")
public class IngredientEntity {

    @NonNull
    @PrimaryKey
    public String id;

    public String title;

    public String description;

    public String thumbnail;

    public IngredientEntity(@NonNull String id, String title, String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
