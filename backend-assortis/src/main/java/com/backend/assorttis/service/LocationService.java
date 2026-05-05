package com.backend.assorttis.service;

import com.backend.assorttis.dto.location.CountryDTO;
import com.backend.assorttis.dto.location.RegionDTO;
import com.backend.assorttis.repository.CountryRepository;
import com.backend.assorttis.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream().map(c -> CountryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .code(c.getCode())
                .regionWorld(c.getRegionWorld())
                .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream().map(r -> RegionDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .code(r.getCode())
                .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CountryDTO> getCountriesByRegionWorld(String regionWorld) {
        // Assuming we might want to filter by regionWorld
        return countryRepository.findAll().stream()
                .filter(c -> regionWorld.equals(c.getRegionWorld()))
                .map(c -> CountryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .code(c.getCode())
                .regionWorld(c.getRegionWorld())
                .build()).collect(Collectors.toList());
    }
}
