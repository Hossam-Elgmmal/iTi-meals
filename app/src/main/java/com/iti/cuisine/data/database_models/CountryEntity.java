package com.iti.cuisine.data.database_models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "countries")
public class CountryEntity {

    @PrimaryKey
    public String title;

    public String emoji;

}
