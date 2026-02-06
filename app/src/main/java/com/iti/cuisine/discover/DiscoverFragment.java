package com.iti.cuisine.discover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.utils.glide.GlideManager;
import com.iti.cuisine.utils.presenter.PresenterHost;

import java.util.List;


public class DiscoverFragment extends Fragment implements DiscoverPresenter.DiscoverView {

    private final String PRESENTER_KEY = "discover_presenter";

    private DiscoverPresenter presenter;

    private PresenterHost presenterHost;

    private MaterialCardView randomMealCardView;
    private ImageView randomMealImageView;
    private TextView randomMealTitleTextView;
    private Chip randomMealCountryChip;

    private MaterialCardView firstIngredientCardView;
    private ImageView firstIngredientImageView;
    private TextView firstIngredientTitleTextView;

    private MaterialCardView secondIngredientCardView;
    private ImageView secondIngredientImageView;
    private TextView secondIngredientTitleTextView;

    private TodayMealAdapter todayMealAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.discover_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        initializeParameters(view);
    }

    private void initializeParameters(@NonNull View view) {

        randomMealCardView = view.findViewById(R.id.random_meal_card);
        randomMealImageView = view.findViewById(R.id.random_meal_image);
        randomMealTitleTextView = view.findViewById(R.id.random_meal_title);
        randomMealCountryChip = view.findViewById(R.id.random_meal_country_chip);

        firstIngredientCardView = view.findViewById(R.id.first_ingredient_card);
        firstIngredientImageView = view.findViewById(R.id.first_ingredient_image);
        firstIngredientTitleTextView = view.findViewById(R.id.first_ingredient_title);

        secondIngredientCardView = view.findViewById(R.id.second_ingredient_card);
        secondIngredientImageView = view.findViewById(R.id.second_ingredient_image);
        secondIngredientTitleTextView = view.findViewById(R.id.second_ingredient_title);

        RecyclerView todayMealRecyclerView = view.findViewById(R.id.today_meal_recycler);
        todayMealAdapter = new TodayMealAdapter(List.of());

        todayMealAdapter.setOnItemClick(this::navigateToMealDetailScreen);
        todayMealAdapter.setOnCountryClick(this::navigateToCountryScreen);

        todayMealRecyclerView.setAdapter(todayMealAdapter);

        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, DiscoverPresenterImpl::createNewInstance);
        presenter.setView(this);
    }

    @Override
    public void showLoading() {
        //todo
    }

    @Override
    public void hideLoading() {
        //todo
    }

    @Override
    public void setRandomMeal(MealEntity meal) {

        randomMealTitleTextView.setText(meal.getTitle());
        randomMealCountryChip.setText(meal.getCountry());
        randomMealCardView
                .setOnClickListener(v -> navigateToMealDetailScreen(meal.getId()));

        randomMealCountryChip
                .setOnClickListener(v -> navigateToCountryScreen(meal.getCountry()));

        GlideManager.loadInto(meal.getThumbnail(), randomMealImageView);
        GlideManager.loadInto(meal.getCountryFlagUrl(), randomMealImageView);

    }

    @Override
    public void setFirstIngredient(MealIngredientEntity ingredient) {
        firstIngredientTitleTextView.setText(ingredient.getTitle());
        firstIngredientCardView.setOnClickListener(v -> {
            navigateToIngredientScreen(ingredient.getTitle());
        });
        GlideManager.loadInto(ingredient.getThumbnail(), firstIngredientImageView);
    }

    @Override
    public void setSecondIngredient(MealIngredientEntity ingredient) {
        secondIngredientTitleTextView.setText(ingredient.getTitle());
        secondIngredientCardView.setOnClickListener(v -> {
            navigateToIngredientScreen(ingredient.getTitle());
        });
        GlideManager.loadInto(ingredient.getThumbnail(), secondIngredientImageView);
    }

    @Override
    public void setTodayMeals(List<MealEntity> meals) {
        todayMealAdapter.setTodayMeals(meals);
    }

    public void navigateToMealDetailScreen(String mealId) {
        //todo
    }

    public void navigateToIngredientScreen(String ingredientTitle) {
        //todo
    }

    public void navigateToCountryScreen(String countryTitle) {
        //todo
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        if (isRemoving()) {
            presenterHost.removePresenter(PRESENTER_KEY);
        }
        super.onDestroyView();
    }
}