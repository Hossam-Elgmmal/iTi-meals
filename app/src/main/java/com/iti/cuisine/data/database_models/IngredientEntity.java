package com.iti.cuisine.data.database_models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "ingredients")
public class IngredientEntity {

    @PrimaryKey
    public String id;

    public String title;

    public String description;

    public String thumbnail;
}
