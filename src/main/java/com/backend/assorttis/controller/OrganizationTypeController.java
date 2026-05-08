package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationTypeDTO;
import com.backend.assorttis.service.OrganizationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organization-types")
@RequiredArgsConstructor
public class OrganizationTypeController {

    private final OrganizationTypeService organizationTypeService;

    @GetMapping
    public List<OrganizationTypeDTO> getAllOrganizationTypes() {
        return organizationTypeService.getAllOrganizationTypes();
    }
}
