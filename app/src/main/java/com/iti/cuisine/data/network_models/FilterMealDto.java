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
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }
}
