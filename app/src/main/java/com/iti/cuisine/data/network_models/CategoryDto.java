package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {
    @SerializedName("idCategory")
    private String id;

    @SerializedName("strCategory")
    private String title;

    @SerializedName("strCategoryThumb")
    private String thumbnail;

    @SerializedName("strCategoryDescription")
    private String description;

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
