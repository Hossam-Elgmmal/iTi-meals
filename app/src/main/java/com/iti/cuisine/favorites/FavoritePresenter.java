package com.iti.cuisine.favorites;


import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.utils.presenter.Presenter;

import java.util.List;

public interface FavoritePresenter extends Presenter {


    interface FavoriteView {
        void setMeals(List<FavoriteMealEntity> meals);

        void showMessage(int messageId);
        void showDeletedMeal(FavoriteMealEntity mealEntity);
    }

    void setView(FavoriteView view);

    void removeView();

    void setDate(long date);

    void deleteMeal(FavoriteMealEntity mealEntity);

    void addFavoriteMeal(FavoriteMealEntity mealEntity);

}
