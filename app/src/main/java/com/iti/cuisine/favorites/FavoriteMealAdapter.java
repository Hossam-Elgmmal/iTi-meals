package com.iti.cuisine.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.utils.glide.GlideManager;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FavoriteMealAdapter extends RecyclerView.Adapter<FavoriteMealAdapter.FavoriteViewHolder> {

    private List<FavoriteMealEntity> meals;

    private static Consumer<String> onMealClick;
    public static Consumer<FavoriteMealEntity> onDeleteMealClick;
    private static BiConsumer<String, String> onCountryClick;

    public FavoriteMealAdapter(List<FavoriteMealEntity> meals) {
        this.meals = meals;
    }

    public void setMeals(List<FavoriteMealEntity> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setOnMealClick(Consumer<String> onMealClick) {
        FavoriteMealAdapter.onMealClick = onMealClick;
    }

    public void setOnDeleteMealClick(Consumer<FavoriteMealEntity> onDeleteMealClick) {
        FavoriteMealAdapter.onDeleteMealClick = onDeleteMealClick;
    }

    public void setOnCountryClick(BiConsumer<String, String> onCountryClick) {
        FavoriteMealAdapter.onCountryClick = onCountryClick;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public FavoriteMealEntity getMeal(int position) {
        return meals.get(position);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final View itemView;
        private final ImageView image;
        private final TextView title;
        private final Chip countryChip;

        public FavoriteViewHolder(View view) {
            super(view);
            itemView = view;
            image = view.findViewById(R.id.meal_image);
            title = view.findViewById(R.id.meal_title);
            countryChip = view.findViewById(R.id.meal_country_chip);
        }

        public void bind(FavoriteMealEntity favoriteMealEntity) {

            itemView.setOnClickListener(v -> onMealClick.accept(favoriteMealEntity.getId()));
            countryChip.setOnClickListener(v -> onCountryClick.accept(favoriteMealEntity.getCountry(), favoriteMealEntity.getCountryFlagUrl()));

            title.setText(favoriteMealEntity.getTitle());
            countryChip.setText(favoriteMealEntity.getCountry());

            GlideManager.loadInto(favoriteMealEntity.getThumbnail(), image);
            GlideManager.loadImageIntoChip(favoriteMealEntity.getCountryFlagUrl(), countryChip);

        }
    }
}
