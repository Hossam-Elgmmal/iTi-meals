package com.iti.cuisine.data.meal;

import android.app.Application;
import android.icu.util.Calendar;

import com.iti.cuisine.data.database.CategoryDao;
import com.iti.cuisine.data.database.CountryDao;
import com.iti.cuisine.data.database.FavoriteDao;
import com.iti.cuisine.data.database.FilterMealDao;
import com.iti.cuisine.data.database.IngredientDao;
import com.iti.cuisine.data.database.MealDao;
import com.iti.cuisine.data.database.MealDatabase;
import com.iti.cuisine.data.database.MealIngredientDao;
import com.iti.cuisine.data.database.PlanMealDao;
import com.iti.cuisine.data.database_models.CategoryEntity;
import com.iti.cuisine.data.database_models.CountryEntity;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.IngredientEntity;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.data.database_models.MealWithIngredients;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.data.mappers.CategoryMapper;
import com.iti.cuisine.data.mappers.CountryMapper;
import com.iti.cuisine.data.mappers.IngredientMapper;
import com.iti.cuisine.data.mappers.MealIngredientMapper;
import com.iti.cuisine.data.mappers.MealMapper;
import com.iti.cuisine.data.mappers.SearchMealMapper;
import com.iti.cuisine.data.network.MealsService;
import com.iti.cuisine.data.network.RetrofitManager;
import com.iti.cuisine.data.network_models.CategoryDto;
import com.iti.cuisine.data.network_models.CountryDto;
import com.iti.cuisine.data.network_models.IngredientDto;
import com.iti.cuisine.data.network_models.MealDto;
import com.iti.cuisine.search.SearchItem;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepoImpl implements MealRepo {

    private final CategoryDao categoryDao;
    private final CountryDao countryDao;
    private final FavoriteDao favoriteDao;
    private final IngredientDao ingredientDao;
    private final MealDao mealDao;
    private final MealIngredientDao mealIngredientDao;
    private final PlanMealDao planMealDao;
    private final FilterMealDao filterMealDao;

    private final MealsService mealsService;

    private MealRepoImpl(MealDatabase mealDatabase, MealsService mealsService) {
        categoryDao = mealDatabase.getCategoryDao();
        countryDao = mealDatabase.getCountryDao();
        favoriteDao = mealDatabase.getFavoriteDao();
        ingredientDao = mealDatabase.getIngredientDao();
        mealDao = mealDatabase.getMealDao();
        mealIngredientDao = mealDatabase.getMealIngredientDao();
        planMealDao = mealDatabase.getPlanMealDao();
        filterMealDao = mealDatabase.getFilterMealDao();

        this.mealsService = mealsService;
    }

    private static volatile MealRepoImpl instance;

    public static Completable initialize(Application application) {
        return Completable.create(
                emitter -> {
                    if (instance == null) {
                        synchronized (MealRepoImpl.class) {
                            if (instance == null) {
                                MealDatabase mealDatabase = MealDatabase.getInstance(application);
                                MealsService mealsService = new RetrofitManager().getMealsService(application);
                                instance = new MealRepoImpl(mealDatabase, mealsService);
                                emitter.onComplete();
                            }
                        }
                    }
                    emitter.onComplete();
                }
        );
    }

    public static MealRepo getInstance() {
        return instance;
    }

    @Override
    public Single<String> fetchRandomMealId() {
        return mealsService.getRandomMeal()
                .flatMap(response -> {
                    if (response.getMeals().isEmpty()) {
                        return Single.just("");
                    }
                    MealDto mealDto = response.getMeals().get(0);

                    return saveAllMealsToDatabase(response.getMeals())
                            .andThen(Single.just(mealDto.getId()));
                })
                .subscribeOn(Schedulers.io());
    }

    private Completable saveAllMealsToDatabase(List<MealDto> meals) {
        return Completable
                .fromAction(() -> {
                    List<MealEntity> mealEntities =
                            meals.stream()
                                    .map(MealMapper::mapToEntity)
                                    .collect(Collectors.toList());

                    mealDao.insertAll(mealEntities);

                    List<MealIngredientEntity> ingredientEntities =
                            meals.stream().flatMap(mealDto -> mealDto.getIngredientsWithMeasures()
                                            .stream()
                                            .map(MealIngredientMapper::mapToEntity)
                                    )
                                    .collect(Collectors.toList());

                    mealIngredientDao.insertAll(ingredientEntities);
                });
    }

    @Override
    public Flowable<MealWithIngredients> getMealWithIngredientsById(String id) {
        return mealDao.getMealWithIngredients(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<String> fetchMealsByLetter() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int letterIndex = (year * 1000 + month * 100 + day) % 26;

        String letter = String.valueOf((char) ('A' + letterIndex));

        return mealsService.getMealsByFirstLetter(letter)
                .flatMap(response -> {
                    if (response.getMeals().isEmpty()) {
                        return Single.just("");
                    }
                    return saveAllMealsToDatabase(response.getMeals())
                            .andThen(Single.just(letter));
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<MealEntity>> getMealsByLetter(String letter) {

        return mealDao.getMealsByFirstLetter(letter)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable fetchMealById(String mealId) {
        return mealsService.getMealById(mealId)
                .flatMapCompletable(response -> saveAllMealsToDatabase(response.getMeals()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<FavoriteMealEntity>> getFavoriteMealById(String mealId) {
        return favoriteDao.getFavoriteMealById(mealId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertFavoriteMeal(FavoriteMealEntity favoriteMealEntity) {
        return favoriteDao.insert(favoriteMealEntity)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFavoriteMealById(String id) {
        return favoriteDao.deleteFavoriteMealById(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<PlanMealEntity>> getSinglePlanMealByDate(long date) {
        return planMealDao.getSinglePlanMealByDate(date)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<PlanMealEntity>> getPlanMealByDate(long date) {
        return planMealDao.getPlanMealByDate(date)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePlanMealById(PlanMealEntity mealEntity) {
        return planMealDao.deletePlanMealById(mealEntity.getMealType(), mealEntity.getDate())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<IngredientEntity>> getAllIngredients() {
        return ingredientDao.getAllIngredients()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<CategoryEntity>> getAllCategories() {
        return categoryDao.getAllCategories()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<CountryEntity>> getAllCountries() {
        return countryDao.getAllCountries()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable fetchCategories() {
        return mealsService.getCategories().flatMapCompletable(
                response ->
                        saveAllCategoriesToDatabase(response.getCategories())
        ).subscribeOn(Schedulers.io());
    }

    private Completable saveAllCategoriesToDatabase(List<CategoryDto> categories) {
        return Completable
                .fromAction(() -> {
                    List<CategoryEntity> categoryEntities =
                            categories.stream()
                                    .map(CategoryMapper::mapToEntity)
                                    .collect(Collectors.toList());

                    categoryDao.insertAll(categoryEntities);
                });
    }

    @Override
    public Completable fetchCountries() {
        return mealsService.getCountries().flatMapCompletable(
                response ->
                        saveAllCountriesToDatabase(response.getCountries())
        ).subscribeOn(Schedulers.io());
    }

    private Completable saveAllCountriesToDatabase(List<CountryDto> countries) {
        return Completable
                .fromAction(() -> {
                    List<CountryEntity> countryEntities =
                            countries.stream()
                                    .map(CountryMapper::mapToEntity)
                                    .collect(Collectors.toList());

                    countryDao.insertAll(countryEntities);
                });
    }

    @Override
    public Completable fetchIngredients() {
        return mealsService.getIngredients().flatMapCompletable(
                response ->
                        saveAllIngredientsToDatabase(response.getIngredients())
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<SearchItem>> fetchMealsByCategory(String title) {

        return mealsService.getMealsByCategory(title.replaceAll("\\s", "_"))
                .map(response -> response.getFilterMeals().stream()
                        .map(SearchMealMapper::mapToSearchItem)
                        .collect(Collectors.toList()))
                .subscribeOn(Schedulers.io());

    }
    @Override
    public Single<List<SearchItem>> fetchMealsByCountry(String title) {

        return mealsService.getMealsByCountry(title.replaceAll("\\s", "_"))
                .map(response -> response.getFilterMeals().stream()
                        .map(SearchMealMapper::mapToSearchItem)
                        .collect(Collectors.toList()))
                .subscribeOn(Schedulers.io());

    }
    @Override
    public Single<List<SearchItem>> fetchMealsByIngredient(String title) {

        return mealsService.getMealsByIngredient(title.replaceAll("\\s", "_"))
                .map(response -> response.getFilterMeals().stream()
                        .map(SearchMealMapper::mapToSearchItem)
                        .collect(Collectors.toList()))
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Flowable<List<FavoriteMealEntity>> getAllFavoriteMeals() {
        return favoriteDao.getAllFavoriteMeals()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteAllMeals() {
        return mealDao.deleteAllMeals()
                .andThen(favoriteDao.deleteAllFavoriteMeals())
                .andThen(planMealDao.deleteAllPlanMeals())
                .andThen(filterMealDao.deleteAllFilterMeals())
                .andThen(categoryDao.deleteAllCategories())
                .andThen(countryDao.deleteAllCountries())
                .andThen(ingredientDao.deleteAllIngredients())
                .andThen(mealIngredientDao.deleteAllMealIngredients())
                .onErrorComplete()
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Flowable<Integer> getPlanMealCount() {
        return planMealDao.getPlanMealCount()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<Integer> getFavoriteCount() {
        return favoriteDao.getFavoriteCount()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<FavoriteMealEntity>> getSingleAllFavoriteMeal() {
        return favoriteDao.getSingleAllFavoriteMeal()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<PlanMealEntity>> getSingleAllPlanMeal() {
        return planMealDao.getSingleAllPlanMeal()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable saveAllFavoriteMeals(List<FavoriteMealEntity> list) {
        return favoriteDao.insertAll(list);
    }

    @Override
    public Completable saveAllPlanMeals(List<PlanMealEntity> list) {
        return  planMealDao.insertAll(list);
    }

    private Completable saveAllIngredientsToDatabase(List<IngredientDto> ingredients) {
        return Completable
                .fromAction(() -> {
                    List<IngredientEntity> ingredientEntities =
                            ingredients.stream()
                                    .map(IngredientMapper::mapToEntity)
                                    .collect(Collectors.toList());

                    ingredientDao.insertAll(ingredientEntities);
                });
    }


    @Override
    public Completable insertPlanMeal(PlanMealEntity planMealEntity) {
        return planMealDao.insert(planMealEntity)
                .subscribeOn(Schedulers.io());
    }
}
