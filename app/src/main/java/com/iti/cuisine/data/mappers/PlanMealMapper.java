package com.iti.cuisine.data.mappers;


import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.PlanMealEntity;

public class PlanMealMapper {

    public static PlanMealEntity mapToEntity(MealEntity meal) {
        return new PlanMealEntity(
                meal.getId(),
                meal.getTitle(),
                meal.getThumbnail(),
                System.currentTimeMillis()
        );
    }

}
