package com.iti.cuisine.plans;


import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.utils.presenter.Presenter;

import java.util.List;

public interface PlanPresenter extends Presenter {


    interface PlanView {
        void setMeals(List<PlanMealEntity> meals);

        void showMessage(int messageId);
        void showDeletedMeal(PlanMealEntity mealEntity);
    }

    void setView(PlanView view);

    void removeView();

    void setDate(long date);

    void deleteMeal(PlanMealEntity mealEntity);

    void addPlanMeal(PlanMealEntity mealEntity);

}
