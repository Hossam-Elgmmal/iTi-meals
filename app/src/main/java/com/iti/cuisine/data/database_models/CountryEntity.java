package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "countries")
public class CountryEntity {

    @NonNull
    @PrimaryKey
    public String title;

    public String emoji;

    public CountryEntity(@NonNull String title, String emoji) {
        this.title = title;
        this.emoji = emoji;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
