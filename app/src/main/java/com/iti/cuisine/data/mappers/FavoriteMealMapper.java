package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.MealEntity;

public class FavoriteMealMapper {

    public static FavoriteMealEntity mapToEntity(MealEntity meal) {
        return new FavoriteMealEntity(
                meal.getId(),
                meal.getTitle(),
                meal.getThumbnail(),
                meal.getCountry(),
                meal.getCountryFlagUrl(),
                System.currentTimeMillis()
        );
    }

}
