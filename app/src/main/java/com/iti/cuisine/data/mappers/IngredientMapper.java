package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.IngredientEntity;
import com.iti.cuisine.data.network_models.IngredientDto;

public class IngredientMapper {

    public static IngredientEntity mapToEntity(IngredientDto dto) {
        return new IngredientEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getThumbnail()
        );
    }

}
