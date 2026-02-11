package com.iti.cuisine.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.utils.glide.GlideManager;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class SearchFragment extends Fragment implements SearchPresenter.SearchView {


    private final String PRESENTER_KEY = "search_presenter";

    private SearchPresenter presenter;

    private PresenterHost presenterHost;

    private String initialItemName;
    private String initialItemImageUrl;
    private int initialSearchMode;

    private Button btnBack;
    private TextInputEditText searchEditText;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ChipGroup toggleSearchType;

    private MaterialCardView selectedItemCard;
    private ImageView selectedItemImage;
    private TextView selectedItemTextView;
    private Button btnClearSelectedItem;

    private LottieAnimationView lottieAnimation;

    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
        initialItemName = args.getSearchItem();
        initialSearchMode = args.getSearchMode();
        initialItemImageUrl = args.getImageUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.search_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeView(view);
        setClickListeners();
        initializingSelectedItem();
    }

    public void initializeView(View view) {

        btnBack = view.findViewById(R.id.btnBack);

        searchEditText = view.findViewById(R.id.search_edit_text);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        toggleSearchType = view.findViewById(R.id.toggleSearchType);

        selectedItemCard = view.findViewById(R.id.selectedItemCard);
        selectedItemImage = view.findViewById(R.id.selectedItemImage);
        selectedItemTextView = view.findViewById(R.id.selectedItemTextView);

        btnClearSelectedItem = view.findViewById(R.id.btnClearItem);

        lottieAnimation = view.findViewById(R.id.lottieAnimation);

        RecyclerView searchRecyclerView = view.findViewById(R.id.searchRecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == RecyclerView.NO_POSITION) return 1;
                return searchAdapter.getSpanSize();
            }
        });
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter();
        searchAdapter.setHasStableIds(true);
        searchRecyclerView.setAdapter(searchAdapter);

        presenterHost = (PresenterHost) requireActivity();
        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, SearchPresenterImpl::createNewInstance);

        swipeRefreshLayout.setOnRefreshListener(presenter::refreshData);

        presenter.setView(this);
    }


    private void setClickListeners() {
        btnBack.setOnClickListener(v -> presenterHost.navigateBack());

        toggleSearchType.setOnCheckedStateChangeListener(
                (group, checkedIds) -> {
                    if (!checkedIds.isEmpty()) {
                        int checkedId = checkedIds.get(0);
                        if (checkedId == R.id.chipCuisines) {
                            presenter.showCountries();
                        } else if (checkedId == R.id.chipCategories) {
                            presenter.showCategories();
                        } else if (checkedId == R.id.chipIngredients) {
                            presenter.showIngredients();
                        }
                    }
                }
        );
        searchAdapter.setOnMealClicked(searchItem -> {
            navigateToMealDetailScreen(searchItem.getId());
        });
        searchAdapter.setOnTypeClicked(searchItem -> {
            presenter.setSelectedItem(searchItem);
            searchEditText.setText("");
        });
        btnClearSelectedItem.setOnClickListener(v -> {
            presenter.clearSelectedItem();
            searchEditText.setText("");
        });
    }

    private void initializingSelectedItem() {
        SearchMode mode = SearchMode.fromCode(initialSearchMode);
        if (mode == SearchMode.INGREDIENT) {
            toggleSearchType.check(R.id.chipIngredients);
            presenter.showIngredients();
        } else if (mode == SearchMode.CATEGORY) {
            toggleSearchType.check(R.id.chipCategories);
            presenter.showCategories();
        } else {
            toggleSearchType.check(R.id.chipCuisines);
            presenter.showCountries();
        }
        if (!initialItemName.isBlank()) {
            presenter.setSelectedItem(new SearchItem(
                    initialItemName.trim(),
                    initialItemName.trim(),
                    initialItemImageUrl,
                    SearchItem.ViewType.TYPE_SEARCH
            ));
        }
    }

    public void navigateToMealDetailScreen(String mealId) {
        NavDirections action = MainNavGraphDirections
                .actionGlobalMealDetailsFragment(mealId);

        presenterHost.navigate(action);
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
                .build();
        presenterHost.showSnackbar(data);
    }

    @Override
    public void setSelectedItem(SearchItem searchItem) {
        enableChip(false);
        selectedItemTextView.setText(searchItem.getTitle());
        GlideManager.loadInto(searchItem.getImageUrl(), selectedItemImage);
        selectedItemCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearSelectedItem() {
        enableChip(true);
        selectedItemCard.setVisibility(View.GONE);
        selectedItemTextView.setText("");
        selectedItemImage.setImageResource(R.drawable.logo);
    }

    void enableChip(boolean isEnabled) {

        for (int i = 0; i < toggleSearchType.getChildCount(); i++) {
            toggleSearchType.getChildAt(i).setEnabled(isEnabled);
        }
    }

    @Override
    public Observable<String> getSearchText() {
        return Observable.create(emitter -> {

            emitter.onNext(searchEditText.getText().toString());
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());
                }
            };

            searchEditText.addTextChangedListener(textWatcher);
            emitter.setCancellable(() -> searchEditText.removeTextChangedListener(textWatcher));
        });
    }

    @Override
    public void setSearchItems(List<SearchItem> searchItems) {
        if (searchItems.isEmpty()) {
            lottieAnimation.setVisibility(View.VISIBLE);
        } else {
            lottieAnimation.setVisibility(View.GONE);
            if (searchItems.get(0).getType() == SearchItem.ViewType.MEAL_SEARCH.getType()) {
                searchAdapter.setSpanSize(2);
            } else {
                searchAdapter.setSpanSize(1);
            }
        }
        searchAdapter.setSearchItems(searchItems);
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