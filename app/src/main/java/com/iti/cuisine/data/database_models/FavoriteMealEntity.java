package com.iti.cuisine.data.database_models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "favoriteMeals")
public class FavoriteMealEntity {

    @PrimaryKey
    public String id;

    public String title;

    private String thumbnail;

}
