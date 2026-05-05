package com.backend.assorttis.controller;

import com.backend.assorttis.dto.location.RegionDTO;
import com.backend.assorttis.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final LocationService locationService;

    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return locationService.getAllRegions();
    }
}
