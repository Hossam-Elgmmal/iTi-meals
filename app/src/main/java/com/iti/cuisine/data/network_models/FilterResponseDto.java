package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterResponseDto {
    @SerializedName("meals")
    public List<FilterMealDto> filterMeals;

    public List<FilterMealDto> getFilterMeals() {
        return filterMeals;
    }
}
