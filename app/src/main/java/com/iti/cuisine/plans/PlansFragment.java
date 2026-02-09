package com.iti.cuisine.plans;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.search.SearchMode;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;


public class PlansFragment extends Fragment implements PlanPresenter.PlanView {

    private final String PRESENTER_KEY = "plans_presenter";

    private PlanPresenter presenter;

    private PresenterHost presenterHost;

    private Button searchButton;
    private CalendarView calendarView;
    private RecyclerView planMealRecyclerView;
    private TypeMealAdapter typeMealAdapter;
    private MaterialCardView emptyListCard;
    private TextView emptyListTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.plans_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        initializeParameters(view);
    }

    private void initializeParameters(@NonNull View view) {
        searchButton = view.findViewById(R.id.btnSearch);
        calendarView = view.findViewById(R.id.calendarView);
        planMealRecyclerView = view.findViewById(R.id.plan_meal_recycler);
        typeMealAdapter = new TypeMealAdapter(List.of());
        planMealRecyclerView.setAdapter(typeMealAdapter);
        emptyListCard = view.findViewById(R.id.quote_card_view);
        emptyListTextView = view.findViewById(R.id.emptyListTextView);

        searchButton.setOnClickListener(v -> navigateToSearchScreen("", SearchMode.NONE));
        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, PlanPresenterImpl::createNewInstance);

        typeMealAdapter.setOnMealClick(this::navigateToMealDetailScreen);
        typeMealAdapter.setOnCountryClick(this::navigateToSearchCountryScreen);
        typeMealAdapter.setOnDeleteMealClick(this::deleteMeal);

        presenter.setView(this);
        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
            LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
            setCurrentSelectedDate(date);
        });
        LocalDate date = LocalDate.now();
        setCurrentSelectedDate(date);
    }

    private void setCurrentSelectedDate(LocalDate date) {
        long utcMillis = date
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        presenter.setDate(utcMillis);
    }

    private void navigateToSearchScreen(String searchString, SearchMode searchMode) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalSearchFragment(searchString, searchMode.getMode());

        presenterHost.navigate(action);
    }

    public void navigateToSearchCountryScreen(String countryTitle) {
        navigateToSearchScreen(countryTitle, SearchMode.COUNTRY);
    }
    public void navigateToMealDetailScreen(String mealId) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalMealDetailsFragment(mealId);

        presenterHost.navigate(action);
    }

    private void deleteMeal(PlanMealEntity mealEntity) {
        presenter.deleteMeal(mealEntity);
    }

    @Override
    public void setMeals(List<PlanMealEntity> meals) {
        typeMealAdapter.setMeals(meals);
        if (meals.isEmpty()) {
            emptyListCard.setVisibility(View.VISIBLE);
            emptyListTextView.setVisibility(View.VISIBLE);
        } else {
            emptyListCard.setVisibility(View.INVISIBLE);
            emptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showDeletedMeal(PlanMealEntity mealEntity) {
        String message = getString(R.string.meal_deleted_successfully);
        SnackbarBuilder snackbarBuilder = new SnackbarBuilder();
        SnackbarBuilder.SnackbarData data = snackbarBuilder
                .setMessage(message)
                .setBottomPadding(84)
                .setAction(getString(R.string.undo),
                        v -> presenter.addPlanMeal(mealEntity))
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