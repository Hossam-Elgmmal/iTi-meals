package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "countries")
public class CountryEntity {

    @NonNull
    @PrimaryKey
    public String title;

    public String flagUrl;

    public CountryEntity(@NonNull String title, String flagUrl) {
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

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
