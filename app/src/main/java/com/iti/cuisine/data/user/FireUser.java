package com.iti.cuisine.data.user;

import java.util.List;

public class FireUser {

    private List<FireFavoriteMeal> favorites;
    private List<FirePlanMeal> plannedMeals;

    public FireUser() {}

    public FireUser(List<FireFavoriteMeal> favorites, List<FirePlanMeal> plannedMeals) {
        this.favorites = favorites;
        this.plannedMeals = plannedMeals;
    }

    public List<FireFavoriteMeal> getFavorites() {
        return favorites;
    }

    public List<FirePlanMeal> getPlannedMeals() {
        return plannedMeals;
    }
}
