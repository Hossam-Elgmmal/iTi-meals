package com.iti.cuisine.data.database_models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "meals")
public class MealEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String category;

    @NonNull
    private String country;

    @NonNull
    private String countryFlagUrl;

    @NonNull
    private String instructions;

    @NonNull
    private String thumbnail;

    @NonNull
    private String youtubeUrl;

    public MealEntity(@NonNull String id,
                      @NonNull String title,
                      @NonNull String category,
                      @NonNull String country,
                      @NonNull String countryFlagUrl,
                      @NonNull String instructions,
                      @NonNull String thumbnail,
                      @NonNull String youtubeUrl
    ) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.country = country;
        this.countryFlagUrl = countryFlagUrl;
        this.instructions = instructions;
        this.thumbnail = thumbnail;
        this.youtubeUrl = youtubeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealEntity that = (MealEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
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

    @NonNull
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(@NonNull String instructions) {
        this.instructions = instructions;
    }

    @NonNull
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@NonNull String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @NonNull
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(@NonNull String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}
