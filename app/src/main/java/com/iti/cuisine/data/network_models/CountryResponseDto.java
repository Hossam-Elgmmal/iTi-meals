package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponseDto {
    @SerializedName("meals")
    private List<CountryDto> countries;

    public List<CountryDto> getCountries() {
        return countries == null ? List.of() : countries;
    }
}
