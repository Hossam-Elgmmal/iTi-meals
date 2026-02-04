package com.iti.cuisine.data.database_models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey
    private String id;

    private String title;

    private String thumbnail;

    private String description;
}
