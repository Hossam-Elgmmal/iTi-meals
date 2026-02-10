package com.iti.cuisine.search;

public class SearchItem {

    public enum ViewType {
        TYPE_SEARCH(0),
        MEAL_SEARCH(1);

        private final int type;

        ViewType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    private final String title;
    private final String imageUrl;
    private final int type;

    public SearchItem(String title, String imageUrl, ViewType viewType) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.type = viewType.getType();
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
