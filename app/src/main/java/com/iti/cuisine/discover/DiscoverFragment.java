package com.iti.cuisine.discover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.search.SearchMode;
import com.iti.cuisine.utils.glide.GlideManager;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

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

    private SwipeRefreshLayout swipeRefreshLayout;

    private MaterialCardView loadingOneCardView;
    private MaterialCardView loadingTwoCardView;

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

        loadingOneCardView = view.findViewById(R.id.loading_card_view_1);
        loadingTwoCardView = view.findViewById(R.id.loading_card_view_2);

        RecyclerView todayMealRecyclerView = view.findViewById(R.id.today_meal_recycler);
        todayMealAdapter = new TodayMealAdapter(List.of());

        todayMealAdapter.setOnItemClick(this::navigateToMealDetailScreen);
        todayMealAdapter.setOnCountryClick(this::navigateToSearchCountryScreen);

        todayMealRecyclerView.setAdapter(todayMealAdapter);

        Button searchButton = view.findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(v -> navigateToSearchScreen("", SearchMode.NONE));

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, DiscoverPresenterImpl::createNewInstance);

        swipeRefreshLayout.setOnRefreshListener(presenter::refreshData);

        presenter.setView(this);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(int messageId) {
        String message = getString(messageId);
        SnackbarBuilder snackbarBuilder = new SnackbarBuilder();
        SnackbarBuilder.SnackbarData data = snackbarBuilder
                .setMessage(message)
                .setBottomPadding(84)
                .build();
        presenterHost.showSnackbar(data);
    }

    @Override
    public void setRandomMeal(MealEntity meal) {

        randomMealTitleTextView.setText(meal.getTitle());
        randomMealCountryChip.setText(meal.getCountry());
        randomMealCardView
                .setOnClickListener(v -> navigateToMealDetailScreen(meal.getId()));

        randomMealCountryChip.setClickable(true);
        randomMealCountryChip
                .setOnClickListener(v -> navigateToSearchCountryScreen(meal.getCountry()));

        GlideManager.loadInto(meal.getThumbnail(), randomMealImageView);
        GlideManager.loadImageIntoChip(meal.getCountryFlagUrl(), randomMealCountryChip);

    }

    @Override
    public void setFirstIngredient(MealIngredientEntity ingredient) {
        firstIngredientTitleTextView.setText(ingredient.getTitle());
        firstIngredientCardView
                .setOnClickListener(v -> navigateToSearchIngredientScreen(ingredient.getTitle()));
        GlideManager.loadInto(ingredient.getThumbnail(), firstIngredientImageView);
    }

    @Override
    public void setSecondIngredient(MealIngredientEntity ingredient) {
        secondIngredientTitleTextView.setText(ingredient.getTitle());
        secondIngredientCardView
                .setOnClickListener(v -> navigateToSearchIngredientScreen(ingredient.getTitle()));
        GlideManager.loadInto(ingredient.getThumbnail(), secondIngredientImageView);
    }

    @Override
    public void setTodayMeals(List<MealEntity> meals) {
        if (meals.isEmpty()) {
            loadingOneCardView.setVisibility(View.VISIBLE);
            loadingTwoCardView.setVisibility(View.VISIBLE);
            return;
        } else {
            loadingOneCardView.setVisibility(View.GONE);
            loadingTwoCardView.setVisibility(View.GONE);
        }
        todayMealAdapter.setTodayMeals(meals);
    }

    public void navigateToMealDetailScreen(String mealId) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalMealDetailsFragment(mealId);

        presenterHost.navigate(action);
    }

    public void navigateToSearchIngredientScreen(String ingredientTitle) {
        navigateToSearchScreen(ingredientTitle, SearchMode.INGREDIENT);
    }

    public void navigateToSearchCountryScreen(String countryTitle) {
        navigateToSearchScreen(countryTitle, SearchMode.COUNTRY);
    }

    private void navigateToSearchScreen(String searchString, SearchMode country) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalSearchFragment(searchString, country.getMode());

        presenterHost.navigate(action);
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (isRemoving() && presenterHost != null) {
            presenterHost.removePresenter(PRESENTER_KEY);
        }
        super.onDetach();
    }
}