package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationFiltersDataDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.dto.organization.OrganizationSavedSearchDTO;
import com.backend.assorttis.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/kpis")
    public OrganizationKPIsDTO getKPIs() {
        return organizationService.getKPIs();
    }

   /* @GetMapping("/filters")
    public OrganizationFiltersDataDTO getFiltersData() {
        return organizationService.getFiltersData();
    }

*/

}
