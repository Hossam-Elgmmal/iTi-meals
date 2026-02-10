package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.network_models.FilterMealDto;
import com.iti.cuisine.search.SearchItem;

public class SearchMealMapper {
    public static SearchItem mapToSearchItem(FilterMealDto dto) {
        return new SearchItem(
                dto.getId(),
                dto.getTitle(),
                dto.getThumbnail() + "/large",
                SearchItem.ViewType.MEAL_SEARCH
        );
    }
}
