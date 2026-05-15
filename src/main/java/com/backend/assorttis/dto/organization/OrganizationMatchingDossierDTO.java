package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class OrganizationMatchingDossierDTO {
    private Long id;
    private Long organizationId;
    private Long createdByUserId;
    private String name;
    private Integer avgScore;
    private Integer totalOrganizations;
    private List<Map<String, Object>> results;
    private Map<String, Object> filters;
    private Instant createdAt;
    private Instant updatedAt;
}

