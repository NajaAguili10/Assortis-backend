package com.backend.assorttis.controller;

import com.backend.assorttis.dto.location.CountryDTO;
import com.backend.assorttis.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final LocationService locationService;

    @GetMapping
    public List<CountryDTO> getAllCountries(@RequestParam(required = false) String regionWorld) {
        if (regionWorld != null) {
            return locationService.getCountriesByRegionWorld(regionWorld);
        }
        return locationService.getAllCountries();
    }
}
