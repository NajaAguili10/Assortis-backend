package com.backend.assorttis.dto.organization;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class OrganizationSavedSearchDTO {
    private Long id;
    private String label;
    private Map<String, Object> payload;
    private Instant createdAt;
}
