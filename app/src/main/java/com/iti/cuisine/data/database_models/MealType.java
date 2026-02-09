package com.iti.cuisine.data.database_models;

import com.iti.cuisine.R;

public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER;

    public int getTitleId() {
        switch (this) {
            case BREAKFAST:
                return R.string.breakfast;
            case LUNCH:
                return R.string.lunch;
            case DINNER:
                return R.string.dinner;
            default:
                return R.string.dinner;
        }
    }
}
