package com.iti.cuisine.data.user;


public class FireFavoriteMeal {

    private String id;

    private String title;

    private String thumbnail;

    private String country;

    private String countryFlagUrl;

    private long insertDate;

    public FireFavoriteMeal() {}


    public FireFavoriteMeal(String id, String title, String thumbnail, String country, String countryFlagUrl, long insertDate) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.country = country;
        this.countryFlagUrl = countryFlagUrl;
        this.insertDate = insertDate;
    }

    public String getId() {
        return id;
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

    public long getInsertDate() {
        return insertDate;
    }
}
