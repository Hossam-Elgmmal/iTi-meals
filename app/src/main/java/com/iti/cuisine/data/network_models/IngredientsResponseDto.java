package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponseDto {
    @SerializedName("meals")
    private List<IngredientDto> ingredients;

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }
}
