package com.iti.cuisine.data.network;

import com.iti.cuisine.data.network_models.CategoriesResponseDto;
import com.iti.cuisine.data.network_models.CountryResponseDto;
import com.iti.cuisine.data.network_models.FilterResponseDto;
import com.iti.cuisine.data.network_models.IngredientsResponseDto;
import com.iti.cuisine.data.network_models.MealsResponseDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("random.php")
    Single<MealsResponseDto> getRandomMeal();

    @GET("lookup.php")
    Single<MealsResponseDto> getMealById(@Query("i") String id);

    @GET("search.php")
    Single<MealsResponseDto> getMealsByFirstLetter(@Query("f") String letter);

    @GET("search.php")
    Single<MealsResponseDto> searchMealByName(@Query("s") String mealName);


    @GET("categories.php")
    Single<CategoriesResponseDto> getCategories();

    @GET("filter.php")
    Single<FilterResponseDto> getMealsByCategory(@Query("c") String category);

    @GET("list.php?a=list")
    Single<CountryResponseDto> getCountries();

    @GET("filter.php")
    Single<FilterResponseDto> getMealsByCountry(@Query("a") String country);

    @GET("list.php?i=list")
    Single<IngredientsResponseDto> getIngredients();

    @GET("filter.php")
    Single<FilterResponseDto> getMealsByIngredient(@Query("i") String ingredient);

}
