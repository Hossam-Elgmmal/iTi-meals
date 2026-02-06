package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class FilterMealDto {
    @SerializedName("strMeal")
    private String title;
    @SerializedName("strMealThumb")
    private String thumbnail;
    @SerializedName("idMeal")
    private String id;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getThumbnail() {
        return thumbnail == null ? "" : thumbnail;
    }

    public String getId() {
        return id == null ? "" : id;
    }
}
