package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrganizationMatchingDossierRequest {
    private String name;
    private Integer avgScore;
    private Integer totalOrganizations;
    private List<Map<String, Object>> results;
    private Map<String, Object> filters;
}

