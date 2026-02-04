package com.iti.cuisine.data.meal;

import android.app.Application;

import com.iti.cuisine.data.database.CategoryDao;
import com.iti.cuisine.data.database.CountryDao;
import com.iti.cuisine.data.database.FavoriteDao;
import com.iti.cuisine.data.database.IngredientDao;
import com.iti.cuisine.data.database.MealDao;
import com.iti.cuisine.data.database.MealDatabase;
import com.iti.cuisine.data.database.MealIngredientDao;
import com.iti.cuisine.data.database.PlanMealDao;
import com.iti.cuisine.data.network.MealsService;
import com.iti.cuisine.data.network.RetrofitManager;

public class MealRepoImpl implements MealRepo {

    private final CategoryDao categoryDao;
    private final CountryDao countryDao;
    private final FavoriteDao favoriteDao;
    private final IngredientDao ingredientDao;
    private final MealDao mealDao;
    private final MealIngredientDao mealIngredientDao;
    private final PlanMealDao planMealDao;

    private final MealsService mealsService;

    private MealRepoImpl(MealDatabase mealDatabase, MealsService mealsService) {
        categoryDao = mealDatabase.getCategoryDao();
        countryDao = mealDatabase.getCountryDao();
        favoriteDao = mealDatabase.getFavoriteDao();
        ingredientDao = mealDatabase.getIngredientDao();
        mealDao = mealDatabase.getMealDao();
        mealIngredientDao = mealDatabase.getMealIngredientDao();
        planMealDao = mealDatabase.getPlanMealDao();

        this.mealsService = mealsService;
    }

    private static volatile MealRepoImpl instance;

    public static MealRepo getInstance(Application application) {
        if (instance == null) {
            synchronized (MealRepoImpl.class) {
                if (instance == null) {
                    MealDatabase mealDatabase = MealDatabase.getInstance(application);
                    MealsService mealsService = new RetrofitManager().getMealsService(application);
                    instance = new MealRepoImpl(mealDatabase, mealsService);
                }
            }
        }
        return instance;
    }

}
