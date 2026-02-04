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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public String getDescription() {
        return description;
    }
}
