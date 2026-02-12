package com.iti.cuisine.data.user;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;

public class FireFavoriteMapper {

    public static FireFavoriteMeal toFireFavoriteMeal(FavoriteMealEntity entity) {
        return new FireFavoriteMeal(
                entity.getId(),
                entity.getTitle(),
                entity.getThumbnail(),
                entity.getCountry(),
                entity.getCountryFlagUrl(),
                entity.getInsertDate()
        );
    }

    public static FavoriteMealEntity fromFireFavoriteMeal(FireFavoriteMeal meal) {
        return new FavoriteMealEntity(
                meal.getId(),
                meal.getTitle(),
                meal.getThumbnail(),
                meal.getCountry(),
                meal.getCountryFlagUrl(),
                meal.getInsertDate()
        );
    }
}
