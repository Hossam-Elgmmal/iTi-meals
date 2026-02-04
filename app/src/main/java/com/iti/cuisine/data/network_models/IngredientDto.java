package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {
    @SerializedName("idIngredient")
    public String id;
    @SerializedName("strIngredient")
    public String title;
    @SerializedName("strDescription")
    public String description;
    @SerializedName("strThumb")
    public String thumbnail;
}
