package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationMatchingDossierDTO;
import com.backend.assorttis.dto.organization.OrganizationMatchingDossierRequest;
import com.backend.assorttis.dto.organization.OrganizationMatchingStatsDTO;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.OrganizationMatchingDossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/organization-matching-dossiers")
@RequiredArgsConstructor
public class OrganizationMatchingDossierController {
    private final OrganizationMatchingDossierService dossierService;

    @GetMapping
    public List<OrganizationMatchingDossierDTO> getDossiers(Authentication authentication) {
        return dossierService.getCurrentOrganizationDossiers(requireUser(authentication).getId());
    }

    @GetMapping("/stats")
    public OrganizationMatchingStatsDTO getStats(Authentication authentication) {
        return dossierService.getCurrentOrganizationStats(requireUser(authentication).getId());
    }

    @GetMapping("/{id}")
    public OrganizationMatchingDossierDTO getDossier(@PathVariable Long id, Authentication authentication) {
        return dossierService.getCurrentOrganizationDossier(requireUser(authentication).getId(), id);
    }

    @PostMapping
    public OrganizationMatchingDossierDTO createDossier(
            @RequestBody OrganizationMatchingDossierRequest request,
            Authentication authentication
    ) {
        return dossierService.createCurrentOrganizationDossier(requireUser(authentication).getId(), request);
    }

    @PutMapping("/{id}")
    public OrganizationMatchingDossierDTO updateDossier(
            @PathVariable Long id,
            @RequestBody OrganizationMatchingDossierRequest request,
            Authentication authentication
    ) {
        return dossierService.updateCurrentOrganizationDossier(requireUser(authentication).getId(), id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDossier(@PathVariable Long id, Authentication authentication) {
        dossierService.deleteCurrentOrganizationDossier(requireUser(authentication).getId(), id);
    }

    private UserDetailsImpl requireUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userDetails;
    }
}
