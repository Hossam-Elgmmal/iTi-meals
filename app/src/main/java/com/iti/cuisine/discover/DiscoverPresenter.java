package com.iti.cuisine.discover;

import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.utils.presenter.Presenter;

import java.util.List;

public interface DiscoverPresenter extends Presenter {

    interface DiscoverView {
        void showLoading();
        void hideLoading();

        void setTodayMeals(List<MealEntity> meals);

        void setRandomMeal(MealEntity meal);
        void setFirstIngredient(MealIngredientEntity ingredient);
        void setSecondIngredient(MealIngredientEntity ingredient);

        void showMessage(int messageId);
    }

    void setView(DiscoverView view);
    void refreshData();
    void removeView();
}
