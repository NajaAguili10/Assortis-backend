package com.backend.assorttis.controller;

import com.backend.assorttis.dto.partnership.PartnershipDTO;
import com.backend.assorttis.service.PartnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/partnerships")
@RequiredArgsConstructor
public class PartnershipController {

    private final PartnershipService partnershipService;

    @GetMapping
    public List<PartnershipDTO> getAllPartnerships() {
        return partnershipService.getAllPartnerships();
    }
}
