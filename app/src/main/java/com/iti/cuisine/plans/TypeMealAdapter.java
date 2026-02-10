package com.iti.cuisine.plans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.iti.cuisine.R;
import com.iti.cuisine.data.database_models.PlanMealEntity;
import com.iti.cuisine.utils.glide.GlideManager;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TypeMealAdapter extends RecyclerView.Adapter<TypeMealAdapter.TypeViewHolder> {

    private List<PlanMealEntity> meals;

    private static Consumer<String> onMealClick;
    public static Consumer<PlanMealEntity> onDeleteMealClick;
    private static BiConsumer<String, String> onCountryClick;

    public TypeMealAdapter(List<PlanMealEntity> meals) {
        this.meals = meals;
    }

    public void setMeals(List<PlanMealEntity> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setOnMealClick(Consumer<String> onMealClick) {
        TypeMealAdapter.onMealClick = onMealClick;
    }

    public void setOnDeleteMealClick(Consumer<PlanMealEntity> onDeleteMealClick) {
        TypeMealAdapter.onDeleteMealClick = onDeleteMealClick;
    }

    public void setOnCountryClick(BiConsumer<String, String> onCountryClick) {
        TypeMealAdapter.onCountryClick = onCountryClick;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan_meal, parent, false);

        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class TypeViewHolder extends RecyclerView.ViewHolder {

        private final View itemView;
        private final TextView mealType;
        private final Button deleteButton;
        private final ImageView image;
        private final TextView title;
        private final Chip countryChip;

        public TypeViewHolder(View view) {
            super(view);
            itemView = view;
            mealType = view.findViewById(R.id.meal_type);
            deleteButton = view.findViewById(R.id.btnDelete);
            image = view.findViewById(R.id.meal_image);
            title = view.findViewById(R.id.meal_title);
            countryChip = view.findViewById(R.id.meal_country_chip);
        }

        public void bind(PlanMealEntity planMealEntity) {

            deleteButton.setOnClickListener(v -> onDeleteMealClick.accept(planMealEntity));
            itemView.setOnClickListener(v -> onMealClick.accept(planMealEntity.getMealId()));
            countryChip.setOnClickListener(v -> onCountryClick.accept(planMealEntity.getCountry(), planMealEntity.getCountryFlagUrl()));

            mealType.setText(planMealEntity.getMealType().getTitleId());
            title.setText(planMealEntity.getTitle());
            countryChip.setText(planMealEntity.getCountry());

            GlideManager.loadInto(planMealEntity.getThumbnail(), image);
            GlideManager.loadImageIntoChip(planMealEntity.getCountryFlagUrl(), countryChip);

        }
    }
}
