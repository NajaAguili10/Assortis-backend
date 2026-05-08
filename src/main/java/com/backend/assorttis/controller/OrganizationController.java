package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.CurrentOrganizationUpdateRequest;
import com.backend.assorttis.dto.location.CountryDTO;
import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.dto.organization.OrganizationSavedSearchDTO;
import com.backend.assorttis.dto.sector.SectorDTO;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.ExpertService;
import com.backend.assorttis.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final ExpertService expertService;

    @GetMapping
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }
    @GetMapping
    public List<OrganizationDTO> getAllOrganizationsByOrganization() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/kpis")
    public OrganizationKPIsDTO getKPIs() {
        return organizationService.getKPIs();
    }

    @GetMapping("/me")
    public OrganizationDTO getCurrentOrganization(Authentication authentication) {
        return organizationService.getCurrentOrganization(authentication != null ? authentication.getName() : null);
    }

    @PutMapping("/me")
    public OrganizationDTO updateCurrentOrganization(
            Authentication authentication,
            @RequestBody CurrentOrganizationUpdateRequest request
    ) {
        return organizationService.updateCurrentOrganization(
                authentication != null ? authentication.getName() : null,
                request
        );
    }
    @GetMapping("/{orgId}/subscription-sectors")
    public List<String> getSubscriptionSectors(@PathVariable Long orgId) {
        return organizationService.getSubscriptionSectors(orgId);
    }

    /* @GetMapping("/filters")
       public OrganizationFiltersDataDTO getFiltersData() {
           return organizationService.getFiltersData();

   */
    @GetMapping("/my-subscription-sectors")
    @PreAuthorize("isAuthenticated()")
    public List<SectorDTO> getMySubscriptionSectors() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        // Try organization first
        Long orgId = organizationService.getOrganizationIdByUserId(userId);
        if (orgId != null) {
            return organizationService.getSubscriptionSectorDTOs(orgId);
        }



        // Return empty list instead of throwing exception
        return new ArrayList<>();
    }

    @GetMapping("/my-subscription-countries")
    @PreAuthorize("isAuthenticated()")
    public List<CountryDTO> getMySubscriptionCountries() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        Long orgId = organizationService.getOrganizationIdByUserId(userId);
        if (orgId != null) {
            return organizationService.getSubscriptionCountryDTOs(orgId);
        }
        return new ArrayList<>();
    }
/*
    @GetMapping("/saved-searches/{userId}")
    public List<OrganizationSavedSearchDTO> getSavedSearches(@PathVariable Long userId) {
        return organizationService.getSavedSearches(userId);
    }

    @PostMapping("/saved-searches/{userId}")
    public OrganizationSavedSearchDTO saveSearch(@PathVariable Long userId, @RequestParam String name, @RequestBody Map<String, Object> payload) {
        return organizationService.saveSearch(userId, name, payload);
    }

    @DeleteMapping("/saved-searches/{id}")
    public void deleteSavedSearch(@PathVariable Long id) {
        organizationService.deleteSavedSearch(id);
    }
 */

}
