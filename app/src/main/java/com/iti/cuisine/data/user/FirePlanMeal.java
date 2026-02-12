package com.iti.cuisine.data.user;


public class FirePlanMeal {

    private String mealType;

    private long date;

    private String mealId;

    private String title;

    private String thumbnail;

    private String country;

    private String countryFlagUrl;

    public FirePlanMeal() {

    }

    public FirePlanMeal(String mealType, long date, String mealId, String title, String thumbnail, String country, String countryFlagUrl) {
        this.mealType = mealType;
        this.date = date;
        this.mealId = mealId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.country = country;
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getMealType() {
        return mealType;
    }

    public long getDate() {
        return date;
    }

    public String getMealId() {
        return mealId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }
}
