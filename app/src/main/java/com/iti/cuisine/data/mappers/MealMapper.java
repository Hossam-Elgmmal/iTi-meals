package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.network_models.CountryDto;
import com.iti.cuisine.data.network_models.MealDto;

public class MealMapper {

    public static MealEntity mapToEntity(MealDto mealDto) {
        return new MealEntity(
                mealDto.getId(),
                mealDto.getTitle(),
                mealDto.getCategory(),
                mealDto.getCountry(),
                CountryDto.getFlagUrl(mealDto.getCountry()),
                mealDto.getInstructions(),
                mealDto.getThumbnail(),
                mealDto.getYoutubeUrl()
        );
    }

}
