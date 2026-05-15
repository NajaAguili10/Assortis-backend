package com.backend.assorttis.controller;

import com.backend.assorttis.dto.partnership.PartnershipDTO;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.PartnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @GetMapping("/current")
    public List<PartnershipDTO> getCurrentOrganizationPartnerships(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return partnershipService.getCurrentOrganizationPartnerships(userDetails.getId());
    }
}