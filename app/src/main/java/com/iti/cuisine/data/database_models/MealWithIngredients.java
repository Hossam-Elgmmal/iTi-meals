package com.iti.cuisine.data.database_models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class MealWithIngredients {
    @Embedded
    private MealEntity meal;

    @Relation(
            parentColumn = "id",
            entityColumn = "mealId"
    )
    private List<MealIngredientEntity> ingredients;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealWithIngredients that = (MealWithIngredients) o;
        return Objects.equals(meal.getId(), that.meal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(meal.getId());
    }

    public MealWithIngredients(MealEntity meal, List<MealIngredientEntity> ingredients) {
        this.meal = meal;
        this.ingredients = ingredients;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }

    public List<MealIngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MealIngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }
}