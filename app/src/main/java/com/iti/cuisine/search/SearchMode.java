package com.iti.cuisine.search;

public enum SearchMode {
    NONE(0),
    INGREDIENT(1),
    COUNTRY(2),
    CATEGORY(3);

    private final int mode;

    SearchMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public static SearchMode fromCode(int code) {
        for (SearchMode mode : values()) {
            if (mode.getMode() == code) {
                return mode;
            }
        }
        return COUNTRY;
    }
}
