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

    private final String id;
    private final String title;
    private final String imageUrl;
    private final String lowerTitle;
    private final int type;

    public SearchItem(String id, String title, String imageUrl, ViewType viewType) {
        this.id = id;
        this.title = title;
        lowerTitle = title.toLowerCase();
        this.imageUrl = imageUrl;
        this.type = viewType.getType();
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getLowerTitle() {
        return lowerTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                imageUrl.equals(that.imageUrl) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
