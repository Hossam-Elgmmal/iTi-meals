package com.iti.cuisine.data.user;

import com.iti.cuisine.data.database_models.MealType;
import com.iti.cuisine.data.database_models.PlanMealEntity;

public class FirePlanMapper {

    public static FirePlanMeal toFirePlanMeal(PlanMealEntity entity) {
        return new FirePlanMeal(
                entity.getMealType().getTitle(),
                entity.getDate(),
                entity.getMealId(),
                entity.getTitle(),
                entity.getThumbnail(),
                entity.getCountry(),
                entity.getCountryFlagUrl()
        );
    }

    public static PlanMealEntity fromFirePlanMeal(FirePlanMeal meal) {

        return new PlanMealEntity(
                MealType.fromString(meal.getMealType()),
                meal.getDate(),
                meal.getMealId(),
                meal.getTitle(),
                meal.getThumbnail(),
                meal.getCountry(),
                meal.getCountryFlagUrl()
        );
    }
}
