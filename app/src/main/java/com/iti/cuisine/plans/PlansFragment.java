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
import com.iti.cuisine.search.SearchMode;
import com.iti.cuisine.utils.presenter.PresenterHost;

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

        searchButton.setOnClickListener(v -> navigateToSearchScreen());
        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, PlanPresenterImpl::createNewInstance);


        presenter.setView(this);
    }

    private void navigateToSearchScreen() {
        NavDirections action = MainNavGraphDirections
                .actionGlobalSearchFragment("", SearchMode.NONE.getMode());

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