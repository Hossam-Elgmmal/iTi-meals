package com.iti.cuisine.meal_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.cuisine.R;
import com.iti.cuisine.data.ui_models.MealStep;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MealStepViewHolder> {

    private List<MealStep> mealSteps;

    public StepsAdapter(List<MealStep> mealSteps) {
        this.mealSteps = mealSteps;
    }

    public void setMealSteps(List<MealStep> mealSteps) {
        this.mealSteps = mealSteps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_step, parent, false);
        return new MealStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealStepViewHolder holder, int position) {
        holder.bind(mealSteps.get(position));
    }

    @Override
    public int getItemCount() {
        return mealSteps.size();
    }

    public static class MealStepViewHolder extends RecyclerView.ViewHolder {

        private final TextView mealStepTitle;
        private final TextView mealStepDetails;

        public MealStepViewHolder(@NonNull View itemView) {
            super(itemView);
            mealStepTitle = itemView.findViewById(R.id.meal_step_title);
            mealStepDetails = itemView.findViewById(R.id.meal_step_details);
        }

        public void bind(MealStep mealStep) {
            mealStepTitle.setText(mealStep.getTitle());
            mealStepDetails.setText(mealStep.getContent());
        }
    }
}
