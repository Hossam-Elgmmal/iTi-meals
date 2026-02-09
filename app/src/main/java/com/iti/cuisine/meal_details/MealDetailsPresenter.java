package com.iti.cuisine.meal_details;

import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealType;
import com.iti.cuisine.data.database_models.MealWithIngredients;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.data.ui_models.MealStep;
import com.iti.cuisine.utils.presenter.Presenter;

import java.util.List;
import java.util.Map;

public interface MealDetailsPresenter extends Presenter {

    interface MealDetailsView {
        void showLoading();
        void hideLoading();

        void setMeal(MealWithIngredients meal, List<MealStep> mealSteps);
        void setIsFavorite(boolean isFavorite);

        void showMessage(int messageId);

        void showConfirmPlanDialog(MealEntity mealEntity, long longDate, String formattedDate, Map<MealType, PlanMealEntity> meals);
    }

    void toggleFavorite(MealEntity mealEntity);
    void getPlanMealsByDateAndShowConfirmDialog(MealEntity mealEntity, long date);
    void saveMealToPlan(MealEntity mealEntity, MealType mealType, long date);
    void setMealId(String mealId);
    void setView(MealDetailsView view);
    void removeView();
    void refreshData(String mealId);
}
