package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.data.network_models.MealDto;

public class MealIngredientMapper {

    public static MealIngredientEntity mapToEntity(MealDto.MealIngredientDto dto) {
        return new MealIngredientEntity(
                dto.getMealId(),
                dto.getTitle(),
                dto.getMeasure(),
                dto.getThumbnail()
        );
    }

}
