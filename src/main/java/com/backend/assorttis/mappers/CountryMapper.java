package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.location.CountryDTO;
import com.backend.assorttis.entities.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public CountryDTO toDTO(Country country) {
        if (country == null) return null;
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .code(country.getCode())
                .regionWorld(country.getRegionWorld())
                .build();
    }
}
