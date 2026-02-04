package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponseDto {

    @SerializedName("meals")
    public List<MealDto> meals;

    public List<MealDto> getMeals() {
        return meals;
    }
}
