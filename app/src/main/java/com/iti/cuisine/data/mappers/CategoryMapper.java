package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.CategoryEntity;
import com.iti.cuisine.data.network_models.CategoryDto;

public class CategoryMapper {

    public static CategoryEntity mapToEntity(CategoryDto dto) {
        return new CategoryEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getThumbnail(),
                dto.getDescription()
        );
    }

}
