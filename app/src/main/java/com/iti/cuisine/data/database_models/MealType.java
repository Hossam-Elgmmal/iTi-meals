package com.iti.cuisine.data.database_models;

import com.iti.cuisine.R;

public enum MealType {
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER");

    private final String title;

    MealType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

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

    public static MealType fromString(String title) {
        if (title.equalsIgnoreCase(BREAKFAST.title)) {
            return BREAKFAST;
        } else if (title.equalsIgnoreCase(LUNCH.title)) {
            return LUNCH;
        } else {
            return DINNER;
        }
    }
}
