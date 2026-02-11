package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "favoriteMeals")
public class FavoriteMealEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String thumbnail;

    @NonNull
    private String country;

    @NonNull
    private String countryFlagUrl;

    private long insertDate;

    public FavoriteMealEntity(@NonNull String id, @NonNull String title, @NonNull String thumbnail, @NonNull String country, @NonNull String countryFlagUrl, long insertDate) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.country = country;
        this.countryFlagUrl = countryFlagUrl;
        this.insertDate = insertDate;
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

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(@NonNull String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public void setInsertDate(long insertDate) {
        this.insertDate = insertDate;
    }

    public long getInsertDate() {
        return insertDate;
    }
}
