package com.iti.cuisine.meal_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.MealIngredientEntity;
import com.iti.cuisine.utils.glide.GlideManager;

import java.util.List;
import java.util.function.BiConsumer;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<MealIngredientEntity> ingredients;
    private static BiConsumer<String, String> onIngredientClick;

    public IngredientsAdapter(List<MealIngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(List<MealIngredientEntity> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void setOnIngredientClick(BiConsumer<String, String> onIngredientClick) {
        IngredientsAdapter.onIngredientClick = onIngredientClick;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ingredientImage;
        private final TextView ingredientTitle;
        private final TextView ingredientMeasure;
        private final View ingredientCard;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientCard = itemView;
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
            ingredientTitle = itemView.findViewById(R.id.ingredient_title);
            ingredientMeasure = itemView.findViewById(R.id.ingredient_measure);
        }

        public void bind(MealIngredientEntity mealIngredientEntity) {
            ingredientTitle.setText(mealIngredientEntity.getTitle());
            ingredientMeasure.setText(mealIngredientEntity.getMeasure());
            ingredientCard.setOnClickListener(v -> onIngredientClick.accept(mealIngredientEntity.getTitle(), mealIngredientEntity.getThumbnail()));
            GlideManager.loadInto(mealIngredientEntity.getThumbnail(), ingredientImage);
        }
    }
}
