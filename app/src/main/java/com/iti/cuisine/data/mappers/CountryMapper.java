package com.iti.cuisine.data.mappers;

import com.iti.cuisine.data.database_models.CountryEntity;
import com.iti.cuisine.data.network_models.CountryDto;

public class CountryMapper {

    public static CountryEntity mapToEntity(CountryDto dto) {
        return new CountryEntity(
                dto.getTitle(),
                CountryDto.getFlagUrl(dto.getTitle())
        );
    }

}
