package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {
    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strIngredient")
    private String title;
    @SerializedName("strDescription")
    private String description;
    @SerializedName("strThumb")
    private String thumbnail;

    public String getId() {
        return id == null ? "" : id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public String getThumbnail() {
        return thumbnail == null ? "" : thumbnail;
    }
}
