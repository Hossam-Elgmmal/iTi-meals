package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "categories")
public class CategoryEntity {

    @NonNull
    @PrimaryKey
    private String id;

    private String title;

    private String thumbnail;

    private String description;

    public CategoryEntity(@NonNull String id, String title, String thumbnail, String description) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
