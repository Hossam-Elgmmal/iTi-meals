package com.iti.cuisine.data.network_models;

import com.google.gson.annotations.SerializedName;

public class CountryDto {
    @SerializedName("strArea")
    private String title;

    public String getTitle() {
        return title;
    }

    public String getFlagEmoji() {
        switch (title) {

            case "Algerian":
                return "🇩🇿";

            case "American":
                return "🇺🇸";

            case "Argentinian":
                return "🇦🇷";

            case "Australian":
                return "🇦🇺";

            case "British":
                return "🇬🇧";

            case "Canadian":
                return "🇨🇦";

            case "Chinese":
                return "🇨🇳";

            case "Croatian":
                return "🇭🇷";

            case "Dutch":
                return "🇳🇱";

            case "Egyptian":
                return "🇪🇬";

            case "Filipino":
                return "🇵🇭";

            case "French":
                return "🇫🇷";

            case "Greek":
                return "🇬🇷";

            case "Indian":
                return "🇮🇳";

            case "Irish":
                return "🇮🇪";

            case "Italian":
                return "🇮🇹";

            case "Jamaican":
                return "🇯🇲";

            case "Japanese":
                return "🇯🇵";

            case "Kenyan":
                return "🇰🇪";

            case "Malaysian":
                return "🇲🇾";

            case "Mexican":
                return "🇲🇽";

            case "Moroccan":
                return "🇲🇦";

            case "Norwegian":
                return "🇳🇴";

            case "Polish":
                return "🇵🇱";

            case "Portuguese":
                return "🇵🇹";

            case "Russian":
                return "🇷🇺";

            case "Saudi Arabian":
                return "🇸🇦";

            case "Slovakian":
                return "🇸🇰";

            case "Spanish":
                return "🇪🇸";

            case "Syrian":
                return "🇸🇾";

            case "Thai":
                return "🇹🇭";

            case "Tunisian":
                return "🇹🇳";

            case "Turkish":
                return "🇹🇷";

            case "Ukrainian":
                return "🇺🇦";

            case "Uruguayan":
                return "🇺🇾";

            case "Venezulan":
                return "🇻🇪";   // API typo

            case "Vietnamese":
                return "🇻🇳";

            default:
                return "🌍";
        }
    }
}
