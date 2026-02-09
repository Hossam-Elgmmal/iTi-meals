package com.iti.cuisine.meal_details;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.MealEntity;
import com.iti.cuisine.data.database_models.MealType;
import com.iti.cuisine.data.database_models.MealWithIngredients;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.data.ui_models.MealStep;
import com.iti.cuisine.search.SearchMode;
import com.iti.cuisine.utils.glide.GlideManager;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import java.util.List;
import java.util.Map;


public class MealDetailsFragment extends Fragment implements MealDetailsPresenter.MealDetailsView {

    private final String PRESENTER_KEY = "meal_details_presenter";
    private final String DATE_PICKER_TAG = "DATE_PICKER";

    private Button btnBack;
    private ImageView mealImage;

    private TextView mealTitle;
    private MaterialButton btnFavorite;
    private Chip mealCountryChip;

    private RecyclerView ingredientsRecyclerView;
    private IngredientsAdapter ingredientsAdapter;

    private RecyclerView stepsRecyclerView;
    private StepsAdapter stepsAdapter;

    private Button btnAddToPlan;
    private Button btnWatchNow;

    private MealDetailsPresenter presenter;

    private PresenterHost presenterHost;

    private String mealId;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        mealId = args.getMealId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.details_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        initializeParameters(view);
    }

    private void initializeParameters(@NonNull View view) {

        mealImage = view.findViewById(R.id.meal_image);
        mealTitle = view.findViewById(R.id.meal_title);

        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        ingredientsAdapter = new IngredientsAdapter(List.of());
        ingredientsAdapter.setOnIngredientClick(this::navigateToSearchIngredientScreen);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        stepsRecyclerView = view.findViewById(R.id.steps_recycler_view);
        stepsAdapter = new StepsAdapter(List.of());
        stepsRecyclerView.setAdapter(stepsAdapter);

        btnAddToPlan = view.findViewById(R.id.btnAddToPlan);
        btnWatchNow = view.findViewById(R.id.btnWatchNow);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        btnFavorite = view.findViewById(R.id.btnFavorite);
        mealCountryChip = view.findViewById(R.id.meal_country_chip);

        presenterHost = (PresenterHost) requireActivity();

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> presenterHost.navigateBack());

        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, MealDetailsPresenterImpl::createNewInstance);

        presenter.setMealId(mealId);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshData(mealId));

        presenter.setView(this);
        presenter.refreshData(mealId);
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
    public void setMeal(MealWithIngredients meal, List<MealStep> mealSteps) {
        MealEntity mealEntity = meal.getMeal();
        mealTitle.setText(mealEntity.getTitle());
        mealCountryChip.setText(mealEntity.getCountry());
        mealCountryChip
                .setOnClickListener(v -> navigateToSearchCountryScreen(mealEntity.getCountry()));

        ingredientsAdapter.setIngredients(meal.getIngredients());
        stepsAdapter.setMealSteps(mealSteps);
        btnWatchNow.setOnClickListener(v -> navigateToVideoScreen(mealEntity.getYoutubeUrl()));

        btnFavorite.setOnClickListener(v -> toggleFavorites(mealEntity));

        btnAddToPlan.setOnClickListener(v -> addToPlan(mealEntity));

        GlideManager.loadImageIntoChip(mealEntity.getCountryFlagUrl(), mealCountryChip);
        GlideManager.loadInto(mealEntity.getThumbnail(), mealImage);

    }

    @Override
    public void setIsFavorite(boolean isFavorite) {
        btnFavorite.setIconResource(isFavorite ? R.drawable.ic_favorites_filled : R.drawable.ic_favorites);
    }

    public void navigateToSearchIngredientScreen(String ingredientTitle) {
        navigateToSearchScreen(ingredientTitle, SearchMode.INGREDIENT);
    }

    public void navigateToSearchCountryScreen(String countryTitle) {
        navigateToSearchScreen(countryTitle, SearchMode.COUNTRY);
    }

    private void navigateToSearchScreen(String countryTitle, SearchMode country) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalSearchFragment(countryTitle, country.getMode());

        presenterHost.navigate(action);
    }

    public void navigateToVideoScreen(String videoUrl) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalVideoFragment(videoUrl);

        presenterHost.navigate(action);
    }

    private void toggleFavorites(MealEntity mealEntity) {
        presenter.toggleFavorite(mealEntity);
    }

    private void addToPlan(MealEntity mealEntity) {
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.from(today))
                .build();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .setSelection(today)
                .setCalendarConstraints(constraints)
                .build();

        datePicker.show(requireActivity().getSupportFragmentManager(), DATE_PICKER_TAG);

        datePicker.addOnPositiveButtonClickListener(date ->
                presenter.getPlanMealsByDateAndShowConfirmDialog(mealEntity, date));
    }

    @Override
    public void showConfirmPlanDialog(MealEntity mealEntity, long longDate, String formattedDate, Map<MealType, PlanMealEntity> meals) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_confirm_add_plan);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        ImageView newImageView = dialog.findViewById(R.id.newMealImageView);
        TextView newTitleTextView = dialog.findViewById(R.id.newMealTitle);

        ImageView oldImageView = dialog.findViewById(R.id.oldMealImageView);
        TextView oldTitleTextView = dialog.findViewById(R.id.oldMealTitle);

        TextView dateTextView = dialog.findViewById(R.id.dateTextView);
        TextView emptyTextView = dialog.findViewById(R.id.emptyTextView);
        TextView mealTypeTextView = dialog.findViewById(R.id.mealTypeTextView);

        MaterialButtonToggleGroup toggleGroup = dialog.findViewById(R.id.toggleMealType);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        GlideManager.loadInto(mealEntity.getThumbnail(), newImageView);
        newTitleTextView.setText(mealEntity.getTitle());

        dateTextView.setText(formattedDate);

        PlanMealEntity typeMeal = meals.get(MealType.BREAKFAST);
        btnConfirm.setOnClickListener(v -> {
            presenter.saveMealToPlan(mealEntity, MealType.BREAKFAST, longDate);
            dialog.dismiss();
        });
        if (typeMeal == null){
            btnConfirm.setText(R.string.save);
            emptyTextView.setVisibility(View.VISIBLE);
            dateTextView.setVisibility(View.VISIBLE);
            oldImageView.setVisibility(View.INVISIBLE);
            oldTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            btnConfirm.setText(R.string.update);
            GlideManager.loadInto(typeMeal.getThumbnail(), oldImageView);
            oldTitleTextView.setText(typeMeal.getTitle());
            emptyTextView.setVisibility(View.INVISIBLE);
            dateTextView.setVisibility(View.INVISIBLE);
            oldImageView.setVisibility(View.VISIBLE);
            oldTitleTextView.setVisibility(View.VISIBLE);
        }

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                PlanMealEntity oldMeal;
                if (checkedId == R.id.btnBreakfast) {
                    mealTypeTextView.setText(R.string.breakfast);
                    oldMeal = meals.get(MealType.BREAKFAST);
                    btnConfirm.setOnClickListener(v -> {
                        presenter.saveMealToPlan(mealEntity, MealType.BREAKFAST, longDate);
                        dialog.dismiss();
                    });
                } else if (checkedId == R.id.btnLunch) {
                    mealTypeTextView.setText(R.string.lunch);
                    oldMeal = meals.get(MealType.LUNCH);
                    btnConfirm.setOnClickListener(v -> {
                        presenter.saveMealToPlan(mealEntity, MealType.LUNCH, longDate);
                        dialog.dismiss();
                    });
                } else {
                    mealTypeTextView.setText(R.string.dinner);
                    oldMeal = meals.get(MealType.DINNER);
                    btnConfirm.setOnClickListener(v -> {
                        presenter.saveMealToPlan(mealEntity, MealType.DINNER, longDate);
                        dialog.dismiss();
                    });
                }
                if (oldMeal == null){
                    btnConfirm.setText(R.string.save);
                    emptyTextView.setVisibility(View.VISIBLE);
                    dateTextView.setVisibility(View.VISIBLE);
                    oldImageView.setVisibility(View.INVISIBLE);
                    oldTitleTextView.setVisibility(View.INVISIBLE);
                } else {
                    btnConfirm.setText(R.string.update);
                    GlideManager.loadInto(oldMeal.getThumbnail(), oldImageView);
                    oldTitleTextView.setText(oldMeal.getTitle());
                    emptyTextView.setVisibility(View.INVISIBLE);
                    dateTextView.setVisibility(View.INVISIBLE);
                    oldImageView.setVisibility(View.VISIBLE);
                    oldTitleTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (isRemoving()) {
            presenterHost.removePresenter(PRESENTER_KEY);
        }
        super.onDetach();
    }
}