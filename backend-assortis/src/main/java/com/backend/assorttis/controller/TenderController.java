package com.backend.assorttis.controller;


import com.backend.assorttis.dto.tender.PaginatedResponseDTO;
import com.backend.assorttis.dto.tender.TenderKPIsDTO;
import com.backend.assorttis.dto.tender.TenderListDTO;
import com.backend.assorttis.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tenders")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @GetMapping("/active")
    public PaginatedResponseDTO<TenderListDTO> getActiveTenders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String country) {
        log.info("GET /api/tenders/active called with page={}, size={}, sector={}, country={}", page, size, sector, country);
        PaginatedResponseDTO<TenderListDTO> response = tenderService.getActiveTenders(page, size, sector, country);
        log.info("Response size: {}", response.getData().size());
        return response;
    }

    @GetMapping("/kpis")
    public TenderKPIsDTO getTenderKPIs(@RequestParam Long organizationId) {
        return tenderService.getTenderKPIs(organizationId);
    }

}

