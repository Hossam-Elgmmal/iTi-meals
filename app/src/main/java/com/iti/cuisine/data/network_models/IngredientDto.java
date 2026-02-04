package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {
    @SerializedName("idIngredient")
    public String id;
    @SerializedName("strIngredient")
    public String title;
    @SerializedName("strDescription")
    public String strDescription;
    @SerializedName("strThumb")
    public String strThumb;
    @SerializedName("strType")
    public Object strType;
}
