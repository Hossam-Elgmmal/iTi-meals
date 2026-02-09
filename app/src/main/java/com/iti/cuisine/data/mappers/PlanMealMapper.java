package com.iti.cuisine.data.mappers;


import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealType;
import com.iti.cuisine.data.database_models.PlanMealEntity;

public class PlanMealMapper {

    public static PlanMealEntity mapToEntity(MealEntity meal, MealType mealType, long date) {
        return new PlanMealEntity(
                mealType,
                date,
                meal.getId(),
                meal.getTitle(),
                meal.getThumbnail(),
                meal.getCountry(),
                meal.getCountryFlagUrl()
        );
    }

}
