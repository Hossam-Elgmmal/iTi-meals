package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "countries")
public class CountryEntity {

    @NonNull
    @PrimaryKey
    private String title;

    @NonNull
    private String flagUrl;

    public CountryEntity(@NonNull String title, @NonNull String flagUrl) {
        this.title = title;
        this.flagUrl = flagUrl;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(@NonNull String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
