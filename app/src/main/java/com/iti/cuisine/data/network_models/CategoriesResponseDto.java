package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponseDto {
    @SerializedName("categories")
    private List<CategoryDto> categories;

    public List<CategoryDto> getCategories() {
        return categories == null ? List.of() : categories;
    }
}
