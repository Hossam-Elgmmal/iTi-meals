package com.iti.cuisine.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iti.cuisine.R;


public class SearchFragment extends Fragment {

    private String searchItemName;
    private int searchMode;

    private Button btnBack;
    private TextInputLayout searchInputLayout;
    private TextInputEditText searchEditText;

    private SwipeRefreshLayout refreshLayout;

    private ChipGroup toggleSearchType;

    private MaterialCardView selectedItemCard;
    private ImageView selectedItemImage;
    private TextView selectedItemTextView;
    private Button btnClearItem;

    private RecyclerView searchRecyclerView;

    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
        searchItemName = args.getSearchItem();
        searchMode = args.getSearchMode();
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
    }

    public void initializeView(View view) {

        btnBack = view.findViewById(R.id.btnBack);
        searchInputLayout = view.findViewById(R.id.search_input_layout);

        searchEditText = view.findViewById(R.id.search_edit_text);

        refreshLayout = view.findViewById(R.id.swipe_refresh);

        toggleSearchType = view.findViewById(R.id.toggleSearchType);

        selectedItemCard = view.findViewById(R.id.selectedItemCard);
        selectedItemImage = view.findViewById(R.id.selectedItemImage);
        selectedItemTextView = view.findViewById(R.id.selectedItemTextView);

        btnClearItem = view.findViewById(R.id.btnClearItem);

        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);

        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = searchAdapter.getItemViewType(position);

                if (type == SearchItem.ViewType.MEAL_SEARCH.getType()) {
                    return 2;
                }
                return 1;
            }
        });
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter();
        searchRecyclerView.setAdapter(searchAdapter);

    }

}