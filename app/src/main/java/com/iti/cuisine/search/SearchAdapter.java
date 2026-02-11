package com.iti.cuisine.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.cuisine.R;
import com.iti.cuisine.utils.glide.GlideManager;

import java.util.List;
import java.util.function.Consumer;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final AsyncListDiffer<SearchItem> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private Consumer<SearchItem> onTypeClicked;

    private Consumer<SearchItem> onMealClicked;

    private int spanSize = 2;

    private static final DiffUtil.ItemCallback<SearchItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchItem oldItem, @NonNull SearchItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchItem oldItem, @NonNull SearchItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public SearchAdapter() {}

    public void setSearchItems(List<SearchItem> searchItems) {
        differ.submitList(searchItems);
    }

    public void setOnTypeClicked(Consumer<SearchItem> onTypeClicked) {
        this.onTypeClicked = onTypeClicked;
    }

    public void setOnMealClicked(Consumer<SearchItem> onMealClicked) {
        this.onMealClicked = onMealClicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == SearchItem.ViewType.TYPE_SEARCH.getType()) {
            View view = inflater.inflate(R.layout.item_search_type, parent, false);
            return new TypeViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_search_meal, parent, false);
            return new MealViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CuisineViewHolder) holder).bind(differ.getCurrentList().get(position), onTypeClicked, onMealClicked);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return differ.getCurrentList().get(position).getType();
    }

    @Override
    public long getItemId(int position) {
        return differ.getCurrentList().get(position).hashCode();
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public static class TypeViewHolder extends RecyclerView.ViewHolder implements CuisineViewHolder {

        private final ImageView typeImage;
        private final TextView typeTitle;
        private final View itemView;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            typeImage = itemView.findViewById(R.id.type_image);
            typeTitle = itemView.findViewById(R.id.type_title);
        }

        @Override
        public void bind(SearchItem searchItem, Consumer<SearchItem> onTypeClicked, Consumer<SearchItem> onMealClicked) {
            typeTitle.setText(searchItem.getTitle());
            itemView.setOnClickListener(v -> onTypeClicked.accept(searchItem));
            GlideManager.loadInto(searchItem.getImageUrl(), typeImage);
        }
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder implements CuisineViewHolder {

        private final ImageView mealImage;
        private final TextView mealTitle;
        private final View itemView;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mealImage = itemView.findViewById(R.id.meal_image);
            mealTitle = itemView.findViewById(R.id.meal_title);
        }

        @Override
        public void bind(SearchItem searchItem, Consumer<SearchItem> onTypeClicked, Consumer<SearchItem> onMealClicked) {
            mealTitle.setText(searchItem.getTitle());
            itemView.setOnClickListener(v -> onMealClicked.accept(searchItem));
            GlideManager.loadInto(searchItem.getImageUrl(), mealImage);
        }
    }

    private interface CuisineViewHolder {
        void bind(SearchItem searchItem, Consumer<SearchItem> onTypeClicked, Consumer<SearchItem> onMealClicked);
    }

}
