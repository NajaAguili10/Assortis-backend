package com.backend.assorttis.controller;

import com.backend.assorttis.dto.sector.SectorDTO;
import com.backend.assorttis.dto.sector.SubsectorDTO;
import com.backend.assorttis.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public List<SectorDTO> getAllSectors() {
        return sectorService.getAllSectors();
    }

    @GetMapping("/subsectors")
    public List<SubsectorDTO> getAllSubsectors() {
        return sectorService.getAllSubsectors();
    }

    @GetMapping("/{sectorId}/subsectors")
    public List<SubsectorDTO> getSubsectorsBySectorId(@PathVariable Long sectorId) {
        return sectorService.getSubsectorsBySectorId(sectorId);
    }
}