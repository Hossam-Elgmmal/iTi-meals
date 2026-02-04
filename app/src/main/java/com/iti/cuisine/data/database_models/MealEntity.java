package com.iti.cuisine.data.database_models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "meals")
public class MealEntity {

    @PrimaryKey
    private String id;

    private String title;

    private String category;

    private String country;

    private String instructions;

    private String thumbnail;

    private String youtubeUrl;

}
