package com.iti.cuisine.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.search.SearchMode;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import java.util.List;


public class FavoritesFragment extends Fragment implements FavoritePresenter.FavoriteView {

    private final String PRESENTER_KEY = "favorite_presenter";

    private FavoritePresenter presenter;

    private PresenterHost presenterHost;

    private Button searchButton;
    private RecyclerView mealRecyclerView;
    private FavoriteMealAdapter adapter;
    private LottieAnimationView lottieAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.favorites_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        initializeParameters(view);
    }

    private void initializeParameters(@NonNull View view) {
        searchButton = view.findViewById(R.id.btnSearch);
        mealRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        lottieAnimation = view.findViewById(R.id.lottieAnimation);

        adapter = new FavoriteMealAdapter(List.of());
        mealRecyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(mealRecyclerView);


        searchButton.setOnClickListener(v -> navigateToSearchScreen("", SearchMode.NONE, ""));
        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, FavoritePresenterImpl::createNewInstance);

        adapter.setOnMealClick(this::navigateToMealDetailScreen);
        adapter.setOnCountryClick(this::navigateToSearchCountryScreen);
        adapter.setOnDeleteMealClick(this::deleteMeal);

        presenter.setView(this);
    }

    @NonNull
    private ItemTouchHelper getItemTouchHelper() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                FavoriteMealEntity mealEntity = adapter.getMeal(position);
                presenter.deleteMeal(mealEntity);
            }
        };
        return new ItemTouchHelper(swipeToDeleteCallback);
    }

    private void navigateToSearchScreen(String searchString, SearchMode searchMode, String imageUrl) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalSearchFragment(searchString, searchMode.getMode(), imageUrl);

        presenterHost.navigate(action);
    }

    public void navigateToSearchCountryScreen(String countryTitle, String imageUrl) {
        navigateToSearchScreen(countryTitle, SearchMode.COUNTRY, imageUrl);
    }
    public void navigateToMealDetailScreen(String mealId) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalMealDetailsFragment(mealId);

        presenterHost.navigate(action);
    }

    private void deleteMeal(FavoriteMealEntity mealEntity) {
        presenter.deleteMeal(mealEntity);
    }

    @Override
    public void setMeals(List<FavoriteMealEntity> meals) {
        adapter.setMeals(meals);
        if (meals.isEmpty()) {
            lottieAnimation.setVisibility(View.VISIBLE);
        } else {
            lottieAnimation.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showDeletedMeal(FavoriteMealEntity mealEntity) {
        String message = getString(R.string.meal_deleted_successfully);
        SnackbarBuilder snackbarBuilder = new SnackbarBuilder();
        SnackbarBuilder.SnackbarData data = snackbarBuilder
                .setMessage(message)
                .setBottomPadding(84)
                .setAction(getString(R.string.undo),
                        v -> presenter.addFavoriteMeal(mealEntity))
                .build();
        presenterHost.showSnackbar(data);
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